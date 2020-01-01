package com.compassitesinc.chat.operator.constructs;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class ChannelUserMap {

    private static ConcurrentHashMap<String , SocketChannel> channelUserMap = new ConcurrentHashMap<>();


    public static ConcurrentHashMap<String, SocketChannel> getChannelUserMap() {
        return channelUserMap;
    }

    public static void setChannelUserMap(ConcurrentHashMap<String, SocketChannel> channelUserMap) {
        ChannelUserMap.channelUserMap = channelUserMap;
    }
}
