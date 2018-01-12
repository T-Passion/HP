package com.passion.libnet.core.request;

import java.util.regex.Pattern;

/**
 * Created by chaos
 * on 2018/1/11. 14:17
 * 文件描述：
 */

public final class RequestVar {
    public static final String VERSION_V1 = "v1";
    public static final String VERSION_V2 = "v2";
    public static final String VERSION_V3 = "v3";
    public static final String MEDIA_TYPE_PLAIN = "text/plain";
    public static final String MEDIA_TYPE_JSON = "application/json";
    public static final String REQUEST_POST = "post";
    public static final String REQUEST_GET = "get";
    public static final Pattern PATTERN = Pattern.compile("//");
    public static final String VERSION_CONST = "{version}";
    public static final String URL_FORMAT = "%s%s";

}
