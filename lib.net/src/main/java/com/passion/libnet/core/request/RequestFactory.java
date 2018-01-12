package com.passion.libnet.core.request;

import android.net.Uri;

import com.passion.libnet.core.NetConfig;
import com.passion.libnet.core.NetWrapper;
import com.passion.libnet.core.RequestModel;
import com.passion.libnet.core.imp.RequestInterceptor;
import com.passion.libnet.core.utils.SafeCheckUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by chaos
 * on 2017/11/16. 16:39
 * 文件描述：
 */

public class RequestFactory {
    private static NetConfig netConfig = NetWrapper.getConfig();

    private RequestFactory() {
    }

    public static Request create(RequestModel requestModel) {
        final RequestModel finalModel = intercept(requestModel, Arrays.asList(netConfig.getRequestInterceptors()));
        return parseRequestModel(finalModel);
    }


    private static RequestModel intercept(RequestModel requestModel, List<RequestInterceptor> requestInterceptors) {
        if (!SafeCheckUtil.isNull(requestInterceptors) && !requestInterceptors.isEmpty()) {
            for (RequestInterceptor requestInterceptor : requestInterceptors) {
                requestModel = requestInterceptor.intercept(requestModel);
                if (requestModel == null) {
                    throw new NullPointerException("Receives null request from request interceptor: " + requestInterceptor.getClass().getName());
                }
            }
        }
        return requestModel;
    }

    public static Request parseRequestModel(RequestModel requestModel) {
        if (RequestVar.REQUEST_GET.equalsIgnoreCase(requestModel.method())) {
            Uri.Builder uriBuilder = Uri.parse(requestModel.url()).buildUpon();
            for (String key : requestModel.getUrlParameters().keySet()) {
                uriBuilder.appendQueryParameter(key, requestModel.getUrlParameters().get(key));
            }

            return new Request.Builder()
                    .headers(Headers.of(requestModel.headers()))
                    .url(uriBuilder.build().toString())
                    .get()
                    .build();
        } else if (RequestVar.REQUEST_POST.equalsIgnoreCase(requestModel.method())) {

            Map<String, String> queryBodyParams = requestModel.getParameters();
            FormBody.Builder formBuilder = new FormBody.Builder();
            for (String key : queryBodyParams.keySet()) {
                formBuilder.add(key, queryBodyParams.get(key));
            }
            RequestBody requestBody = formBuilder.build();

            return new Request.Builder()
                    .url(requestModel.url())
                    .headers(Headers.of(requestModel.headers()))
                    .post(requestBody)
                    .build();

        } else {
            throw new IllegalArgumentException("Network only receives GET or POST method yet.");
        }
    }

}

