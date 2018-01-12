package com.passion.libnet.core.utils;

import android.text.TextUtils;

import com.passion.libnet.core.exception.SignException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaos
 * on 2017/11/15. 23:03
 * 文件描述：
 */


public class SignGenerator {
    private static String[] SYSTEM_PARAMS = new String[]{"format", "appKey", "s_os", "s_osv", "s_apv", "s_net", "s_sc", "s_br", "s_did"};

    public SignGenerator() {
    }

    public static String client(String secrect, Map<String, String> params) throws SignException {
        String[] arr$ = SYSTEM_PARAMS;
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String systemParam = arr$[i$];
            if(!params.containsKey(systemParam)) {
                throw new SignException("没有" + systemParam);
            }
        }

        if(params.containsKey("secrect")) {
            params.remove("secrect");
        }

        return server(secrect, params);
    }

    public static String server(String secrect, Map<String, String> params) {
        String[] keys = (String[])params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder query = new StringBuilder();
        String[] arr$ = keys;
        int len$ = keys.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String key = arr$[i$];
            if(!key.equals("sign") && !key.equals("method") && !key.equals("appKey") && !key.equals("v") && !key.equals("format") && !key.equals("timestamp")) {
                String value = (String)params.get(key);
                if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    query.append(key).append(value);
                }
            }
        }

        return MD5Util.encode(query.toString() + secrect);
    }

    public static void main(String[] args) {
        Map<String, String> pp = new HashMap();
        pp.put("format", "format");
        pp.put("appKey", "appKey");
        pp.put("s_os", "s_os");
        pp.put("s_osv", "s_osv");
        pp.put("s_apv", "s_apv");
        pp.put("s_net", "s_net");
        pp.put("s_sc", "s_sc");
        pp.put("s_br", "s_br");
        pp.put("s_eid", "s_eid");
        pp.put("s_did", "s_did");
        pp.put("entityId", "entityId");
        System.out.println(client("2222", pp));
    }
}

