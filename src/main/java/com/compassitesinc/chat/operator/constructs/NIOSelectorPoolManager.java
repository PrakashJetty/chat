package com.compassitesinc.chat.operator.constructs;

//import com.compassitesinc.chat.socket.server.ChatNIOServerChannelProcessor;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class NIOSelectorPoolManager {

    private static int POOL_SIZE = 2;

    private NIOChannelSelectorPool nioChannelSelectorPool;

    public void intitialzePool(RedisTemplate<String, ChannelMessage> redisTemplate) {
        Queue<NIOChannelSelector> pool = new LinkedBlockingQueue<>();
        for (int i = 0; i < POOL_SIZE; ++i) {
            pool.add(NIOSelectorFactory.getSelector(i, redisTemplate));
        }
        nioChannelSelectorPool = new NIOChannelSelectorPool(pool);

    }

    public NIOChannelSelectorPool getNioChannelSelectorPool() {
        return nioChannelSelectorPool;
    }

    public void setNioChannelSelectorPool(NIOChannelSelectorPool nioChannelSelectorPool) {
        this.nioChannelSelectorPool = nioChannelSelectorPool;
    }
}
