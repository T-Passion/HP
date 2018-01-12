package com.passion.libbase.net;

import com.passion.libbase.constants.BizApiConstant;
import com.passion.libnet.api.BizApiService;

/**
 * Created by chaos
 * on 2018/1/11. 15:28
 * 文件描述：业务
 */

public class HPBizApiService extends BizApiService {

    static {
        apiMap.put(BizApiConstant.GET_COVER_AD_KEY,BizApiConstant.GET_COVER_AD_VALUE);
    }



}
