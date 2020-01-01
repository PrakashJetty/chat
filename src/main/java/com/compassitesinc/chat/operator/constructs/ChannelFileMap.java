package com.compassitesinc.chat.operator.constructs;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by prakashjetty on 12/4/18.
 */
public class ChannelFileMap {
    private static ConcurrentHashMap<SocketChannel, String> channelFileMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<SocketChannel, String> getChannelFileMap() {
        return channelFileMap;
    }

    public static void setChannelFileMap(ConcurrentHashMap<SocketChannel, String> channelFileMap) {
        ChannelFileMap.channelFileMap = channelFileMap;
    }
}
