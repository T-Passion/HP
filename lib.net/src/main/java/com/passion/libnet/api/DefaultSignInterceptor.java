package com.passion.libnet.api;

import com.passion.libnet.core.RequestModel;
import com.passion.libnet.core.request.RequestInterceptor;
import com.passion.libnet.core.utils.SignGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by chaos
 * on 2017/11/15. 22:52
 * 文件描述：
 */

public class DefaultSignInterceptor implements RequestInterceptor {
    private Set<String> signRootSet;
    private Set<String> requiredSignUrlSet;
    private Set<String> forbiddenSignUrlSet;
    private String appSecret;

    public DefaultSignInterceptor(String appSecret) {
        this(appSecret, null, null, null);
    }

    public DefaultSignInterceptor(String appSecret, Set<String> signRoots, Set<String> requiredSignUrls, Set<String> forbiddenSignUrls) {
        this.appSecret = appSecret;
        if(signRoots != null && signRoots.size() > 0) {
            this.signRootSet = new HashSet<>();
            this.signRootSet.addAll(signRoots);
        }

        if(requiredSignUrls != null && requiredSignUrls.size() > 0) {
            this.requiredSignUrlSet = new HashSet<>();
            this.requiredSignUrlSet.addAll(requiredSignUrls);
        }

        if(forbiddenSignUrls != null && forbiddenSignUrls.size() > 0) {
            this.forbiddenSignUrlSet = new HashSet<>();
            this.forbiddenSignUrlSet.addAll(forbiddenSignUrls);
        }

    }
    @Override
    public RequestModel intercept(RequestModel request) {
        if(!request.isSignable()) {
            return request;
        } else {
            RequestModel.Builder builder = null;
            String url = request.url();
            boolean signable = false;
            if(this.forbiddenSignUrlSet == null || !this.forbiddenSignUrlSet.contains(url)) {
                if(this.requiredSignUrlSet != null && this.requiredSignUrlSet.contains(url)) {
                    signable = true;
                } else if(this.signRootSet != null && this.signRootSet.size() > 0) {
                    Iterator var5 = this.signRootSet.iterator();

                    while(var5.hasNext()) {
                        String signRoot = (String)var5.next();
                        if(url.startsWith(signRoot)) {
                            signable = true;
                        }
                    }
                } else {
                    signable = true;
                }
            }

            if(signable) {
                builder = this.signatureBuilder(request);
            }

            return builder == null?request:builder.build();
        }
    }


    private RequestModel.Builder signatureBuilder(RequestModel request) {
        Map<String, String> urlParameters = request.getUrlParameters();
        Map<String, String> parameters = request.getParameters();
        Map<String, String> signParamsMap = new HashMap();
        if(urlParameters != null) {
            signParamsMap.putAll(urlParameters);
        }

        if(parameters != null) {
            signParamsMap.putAll(parameters);
        }

        String sign = SignGenerator.client(this.appSecret, signParamsMap);
        RequestModel.Builder builder = request.newBuilder();
        builder.addParameter("sign", sign);
        return builder;
    }
}
