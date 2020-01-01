package com.compassitesinc.chat.operator.constructs;

import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class NIOSelectorFactory {

    public static NIOChannelSelector getSelector(int index, RedisTemplate<String, ChannelMessage> redisTemplate) {
        try {
            Selector selector = Selector.open();

            NIOChannelSelector nioChannelSelector = new NIOChannelSelector(selector, "Selector" + index, "Selector" + index + "topic", redisTemplate);
            return nioChannelSelector;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
