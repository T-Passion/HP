package com.passion.libnet.core.factory;

import android.net.Network;

/**
 * Created by chaos
 * on 2017/11/16. 13:56
 * 文件描述：
 */

public interface NetFactory {

    Network getNetwork(String work);
}
