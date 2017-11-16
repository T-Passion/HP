package com.passion.libnet.core.factory;


import com.passion.libnet.core.Network;

/**
 * Created by chaos
 * on 2017/11/16. 13:56
 * 文件描述：
 */

public interface NetFactory {

    Network getNetwork(String work);
}
