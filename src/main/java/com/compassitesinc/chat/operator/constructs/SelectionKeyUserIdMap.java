package com.compassitesinc.chat.operator.constructs;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by prakashjetty on 11/27/18.
 */
public class SelectionKeyUserIdMap {

    private static ConcurrentHashMap<SocketChannel, SelectionKey> selectionKeyConcurrentHashMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<SocketChannel, SelectionKey> getSelectionKeyConcurrentHashMap() {
        return selectionKeyConcurrentHashMap;
    }

    public static void setSelectionKeyConcurrentHashMap(ConcurrentHashMap<SocketChannel, SelectionKey> selectionKeyConcurrentHashMap) {
        SelectionKeyUserIdMap.selectionKeyConcurrentHashMap = selectionKeyConcurrentHashMap;
    }
}
