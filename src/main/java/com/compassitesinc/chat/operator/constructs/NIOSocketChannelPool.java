package com.compassitesinc.chat.operator.constructs;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class NIOSocketChannelPool {

    private Queue<NIOSocketChannel> channelPool;

    public NIOSocketChannelPool(Queue<NIOSocketChannel> channelPool) {
        this.channelPool = channelPool;
    }

    public Queue<NIOSocketChannel> getChannelPool() {
        return channelPool;
    }

    public void setChannelPool(Queue<NIOSocketChannel> channelPool) {
        this.channelPool = channelPool;
    }
}
