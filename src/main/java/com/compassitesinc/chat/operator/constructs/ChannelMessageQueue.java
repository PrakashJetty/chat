package com.compassitesinc.chat.operator.constructs;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class ChannelMessageQueue {

    private static ConcurrentHashMap<SocketChannel, Queue<ChannelMessage>> channelMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<SocketChannel, Queue<ChannelMessage>> getChannelMap() {
        return channelMap;
    }

    public static void setChannelMap(ConcurrentHashMap<SocketChannel, Queue<ChannelMessage>> channelMap) {
        ChannelMessageQueue.channelMap = channelMap;
    }
}
