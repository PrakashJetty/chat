package com.compassitesinc.chat.operator.constructs;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.util.List;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class ChannelsToSelectorMultiplexer {

    public void multiplex(List<NIOSocketChannel> channels, NIOChannelSelector channelSelector) {
        channels.stream().forEach(c -> {
            try {
                int ops = c.getServerSocket().validOps();
                c.getServerSocket().register(channelSelector.getSelector(), ops, null);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        });
    }
}
