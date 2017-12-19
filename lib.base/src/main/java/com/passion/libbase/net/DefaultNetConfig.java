package com.passion.libbase.net;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.passion.libbase.AppEnv;
import com.passion.libbase.constants.BaseConstant;
import com.passion.libbase.constants.HostConstant;
import com.passion.libnet.core.NetConfig;
import com.passion.libnet.core.RequestModel;
import com.passion.libnet.core.convert.GsonConverter;
import com.passion.libnet.core.request.RequestInterceptor;
import com.passion.libnet.core.utils.MD5Util;
import com.passion.libutils.DeviceUtil;
import com.passion.libutils.NetworkUtil;
import com.passion.libutils.SignUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Created by chaos
 * on 2017/11/20. 13:57
 * 文件描述：
 */

public class DefaultNetConfig {


    /**
     * 签名
     */
    private static RequestInterceptor signInterceptor = new RequestInterceptor() {
        @Override
        public RequestModel intercept(RequestModel requestModel) {
            RequestModel.Builder builder = signatureBuilder(requestModel);
            return builder.build();
        }
    };

    public static void initNetWork(Context context, boolean debugMode, int versionCode) {
        NetConfig netConfig = NetConfig.builder() //
                .setRequestInterceptors(getApiParamsInterceptor(context.getApplicationContext(), versionCode),
                        signInterceptor) // 拦截器
                .setSignAutomatic(false) // 不使用自动签名
                .setDebugMode(debugMode) // debug 模式
//                .setAppSecret(AppEnv.getApiSecret()) // app_secret
                .setJsonConverter(new GsonConverter()) // 设置Gson的converter
                .build();
        NetWorker.init(netConfig);//含拦截器的初始化
    }

    private static RequestInterceptor getApiParamsInterceptor(final Context context, final int versionCode) {
        return new RequestInterceptor() {
            @Override
            public RequestModel intercept(RequestModel requestModel) {
                String callDeviceId = DeviceUtil.getDeviceId(context);
                if (callDeviceId.length() > 16) {
                    callDeviceId = callDeviceId.substring(16, callDeviceId.length());
                }
                Map<String, String> apiParamMap = new HashMap<>();
                apiParamMap.put("s_os", BaseConstant.Net.ANDROID);                                // 系统.    android / ios /
                apiParamMap.put("s_osv", String.valueOf(Build.VERSION.SDK_INT));             // Android系统版本4.3.1
                apiParamMap.put("s_apv", String.valueOf(versionCode));                          // 应用版本
                apiParamMap.put("s_net", getNetType(context));                                      // 网络(有线:1 wifi:2 3G:3 4G:4 5G:5)
                apiParamMap.put("s_sc", DeviceUtil.getScreenWidAndHeight(context));          //屏幕尺寸(800x600);
                apiParamMap.put("s_br", String.format("%s-%s-%s", Build.MANUFACTURER, Build.BRAND, Build.MODEL));                                        // 手机品牌:  huawei
                apiParamMap.put("s_did", MD5Util.encode(TextUtils.isEmpty(callDeviceId)
                        ? BaseConstant.Net.UNKNOWN : callDeviceId));                      // 设备ID（获取设备uuid再进行md5）
                apiParamMap.put("format", BaseConstant.Net.JSON);                                 // 返回格式,目前只支持json
//                apiParamMap.put("app_key", AppEnv.getApiKey());                             // api分配给每个应用的key
                apiParamMap.put("v", "1.0");                                                 // API协议版本，可选值：1.0
                apiParamMap.put("timestamp", String.valueOf(System.currentTimeMillis()));    // 时间戳
                apiParamMap.put("sign_method", BaseConstant.Net.MD5);                             // 签名算法

                // 定位
//                String latitude = LocationUtil.getLgetatitude();
//                if (!StringUtils.isEmpty(latitude)) {
//                    apiParamMap.put("s_lat", latitude); // 定位纬度
//                }
//                String longitude = LocationHelper.getLongitude();
//                if (!StringUtils.isEmpty(longitude)) {
//                    apiParamMap.put("s_lng", longitude); // 定位经度
//                }

                RequestModel.Builder builder = requestModel.newBuilder().addUrlParameters(apiParamMap) // 公共参数
                        .addHeader("content-type", "application/x-www-form-urlencoded") // content_type
                        .addHeader("lang", String.valueOf(Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry())); // 服务端语言（语言_国家）
                String token = "";//UserHelper.getToken();
                if (!TextUtils.isEmpty(token)) {
                    builder.addHeader(BaseConstant.Net.PARA_TOKEN, token);
                }
                return builder.build();
            }
        };
    }

    private static RequestModel.Builder signatureBuilder(RequestModel request) {
        Map<String, String> urlParameters = request.getUrlParameters();
        Map<String, String> parameters = request.getParameters();
        Map<String, Object> signParamsMap = new HashMap<>();
        if (urlParameters != null) {
            signParamsMap.putAll(urlParameters);
        }
        if (parameters != null) {
            signParamsMap.putAll(parameters);
        }
        paramFilter(signParamsMap);
        String sign = SignUtil.sign(signParamsMap, AppEnv.getApiSecret());
        RequestModel.Builder builder = request.newBuilder();
        builder.addParameter(BaseConstant.Net.PARA_SIGN, sign);
        return builder;
    }

    private static String getNetType(Context context) {
        switch (NetworkUtil.getNetworkType(context.getApplicationContext())) {
            case NETWORK_WIFI:
                return "2";
            case NETWORK_4G:
                return "4";
            case NETWORK_3G:
            case NETWORK_2G:
                return "3";
            case NETWORK_UNKNOWN:
                return "4"; //先给个默认值，不然无法通过签名校验
        }
        return "4";
    }

    /**
     * 过滤param里空value的入参
     *
     * @param paramsMap
     */
    private static void paramFilter(Map<String, Object> paramsMap) {
        Iterator iterator = paramsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (null == entry.getValue() || "null".equals(entry.getValue())) {
                Logger.i("paramFilterRemove--" + entry.getKey());
                iterator.remove();
            }
        }
    }
}
