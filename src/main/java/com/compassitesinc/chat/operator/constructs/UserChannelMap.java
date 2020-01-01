package com.compassitesinc.chat.operator.constructs;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by prakashjetty on 12/10/18.
 */
public class UserChannelMap {

    private static ConcurrentHashMap<SocketChannel, String> channelUserMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<SocketChannel, String> getChannelUserMap() {
        return channelUserMap;
    }

    public static void setChannelUserMap(ConcurrentHashMap<SocketChannel, String> channelUserMap) {
        UserChannelMap.channelUserMap = channelUserMap;
    }
}
