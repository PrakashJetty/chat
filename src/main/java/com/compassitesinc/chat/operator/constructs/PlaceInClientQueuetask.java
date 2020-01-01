package com.compassitesinc.chat.operator.constructs;

import org.springframework.data.redis.core.RedisTemplate;

import java.nio.channels.SocketChannel;

/**
 * Created by prakashjetty on 11/15/18.
 */
public class PlaceInClientQueuetask extends ProtocolProcessTask {

    private RedisTemplate<String, ChannelMessage> redisTemplate;

    public PlaceInClientQueuetask(RedisTemplate<String, ChannelMessage> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void processStep(ChannelMessageContext channelMessageContext) {
        SocketChannel serverSocket = null;
        try {
            redisTemplate.opsForList().leftPush(channelMessageContext.getChannelMessage().getToUserId(), channelMessageContext.getChannelMessage());
//            if (channelMessageContext.getChannelMessage() != null &&
//                    channelMessageContext.getChannelMessage().getToUserId() != null) {
//                serverSocket = ChannelUserMap.getChannelUserMap()
//                        .get(channelMessageContext.getChannelMessage().getToUserId());
//
//             } else {
//                serverSocket = channelMessageContext.getClient();
//            }
//            if (serverSocket != null) {
//                Queue<ChannelMessage> queue = ChannelMessageQueue.getChannelMap().get(serverSocket);
//                if (queue != null) {
//                    queue.add(channelMessageContext.getChannelMessage());
//                }
//            }
            channelMessageContext.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            channelMessageContext.setSuccess(false);
        }
    }
}
