package com.compassitesinc.chat.operator.constructs;

import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by prakashjetty on 12/6/18.
 */
public class NIOChannelWriter implements Runnable {

    private SocketChannel client;
    private SelectionKey key;
    private Selector selector;
    private RedisTemplate<String, ChannelMessage> redisTemplate;

    public NIOChannelWriter() {
    }

    public NIOChannelWriter(SocketChannel client, SelectionKey key, Selector selector) {
        this.client = client;
        this.key = key;
        this.selector = selector;
    }

    public NIOChannelWriter(SocketChannel client, SelectionKey key, Selector selector, RedisTemplate redisTemplate) {
        this.client = client;
        this.key = key;
        this.selector = selector;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run() {
        try {


            ChannelMessage channelMessage = redisTemplate.opsForList().rightPop(UserChannelMap.getChannelUserMap().get(client));
            if (channelMessage != null) {
                byte[] bytes = ChatCommMessageEncoder.serialize(channelMessage);
                ByteBuffer byteBuffer = ByteBuffer.allocate((bytes.length + 8));
                byteBuffer.putLong(bytes.length);
                byteBuffer.put(bytes);
                byteBuffer.flip();
                int write = 0;
                System.out.println(" Before Written to client:" + write + " bytes remaining:" + byteBuffer.remaining());
                int count = 0;

//                    write = client.write(byteBuffer);
//                    System.out.println(" First Written to client:" + write + " bytes remaining:" + byteBuffer.remaining());
                while (byteBuffer.position() != byteBuffer.capacity()) {
                    count += write;
                    write = client.write(byteBuffer);
                    System.out.println(" Written to client:" + write + " bytes remaining:" + byteBuffer.remaining());

                }
                System.out.println("bytes:" + bytes.length);
                System.out.println("writecount:" + count);
                System.out.println(write);
                byteBuffer.clear();
            }
            client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
