package com.compassitesinc.chat.operator.constructs;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class NIOChannelPoolManager {

    private static int POOL_SIZE = 2;

    private NIOSocketChannelPool nioSocketChannelPool;

    public void intitialzePool() {
        Queue<NIOSocketChannel> pool = new LinkedBlockingQueue<>();
        for (int i = 0; i < POOL_SIZE; ++i) {
            pool.add(NIOChannelFactory.getChannel(5454 + i));
        }
        nioSocketChannelPool = new NIOSocketChannelPool(pool);
    }

    public NIOSocketChannelPool getNioSocketChannelPool() {
        return nioSocketChannelPool;
    }

    public void setNioSocketChannelPool(NIOSocketChannelPool nioSocketChannelPool) {
        this.nioSocketChannelPool = nioSocketChannelPool;
    }
}
