package com.compassitesinc.chat.operator.constructs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by prakashjetty on 11/12/18.
 */
@Service
public class NIOChatServer implements ApplicationRunner {

    @Value("${document.store.path}")
    private String storePath;

    private Path rootLocation;

    @Autowired
    private RedisTemplate<String, ChannelMessage> redisTemplate;

    @Override
    public void run(ApplicationArguments arg0) {
        this.rootLocation = Paths.get(storePath);
//        IChatProtocolHelicProcessor helicProcessor = new ChatCustomProtocolHelicProcessor(null);
        ChatServerProtocolHandler protocolHandler = new ChatServerProtocolHandler();
        System.out.print("Starting.....");
        NIOChannelPoolManager nioChannelPoolManager = new NIOChannelPoolManager();
        nioChannelPoolManager.intitialzePool();
        NIOSelectorPoolManager nioSelectorPoolManager = new NIOSelectorPoolManager();
        nioSelectorPoolManager.intitialzePool(redisTemplate);
        ChannelsToSelectorMultiplexer channelsToSelectorMultiplexer = new ChannelsToSelectorMultiplexer();
        Iterator<NIOChannelSelector> iterator = nioSelectorPoolManager.getNioChannelSelectorPool()
                .getSelectorQueue().iterator();
        int index = 1;
        Iterator<NIOSocketChannel> channelIterator = nioChannelPoolManager.getNioSocketChannelPool().getChannelPool().iterator();

        while (iterator.hasNext()) {
            NIOChannelSelector channelSelector = iterator.next();
            ExecutorService executorinc = Executors.newFixedThreadPool(1);
            executorinc.submit((KafkaChatCommInConsumer) ChatCommConsumerFactory.getInstance().getInstance("kafka", channelSelector.getTopicName(), protocolHandler));
            List<NIOSocketChannel> channels = new ArrayList<>();
            while (channelIterator.hasNext()) {
                channels.add(channelIterator.next());

                if (index % 1 == 0) {
                    ++index;
                    break;
                } else {
                    ++index;
                }
            }
            channelsToSelectorMultiplexer.multiplex(channels, channelSelector);
            Thread executor = new Thread(channelSelector);
            executor.start();

        }

//        ExecutorService executorinc = Executors.newFixedThreadPool(1);
//        executorinc.submit((KafkaChatCommInConsumer) ChatCommConsumerFactory.getInstance().getInstance("kafka", "socket-in-topic", protocolHandler));
//        ExecutorService executoroutc = Executors.newFixedThreadPool(1);
//        executoroutc.submit((KafkaChatCommOutConsumer) ChatCommConsumerFactory.getInstance().getInstance("kafka", "socket-out-topic"));

        ProtocolProcessFlowConfiguration.configure(rootLocation, redisTemplate);


    }
}
