package com.compassitesinc.chat.operator.constructs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class NIOChannelFactory {

    public static NIOSocketChannel getChannel(int port) {
        ServerSocketChannel serverSocket = null;
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", port));
            serverSocket.configureBlocking(false);
//            serverSocket.setOption(StandardSocketOptions.SO_KEEPALIVE, true);


        } catch (IOException e) {
            e.printStackTrace();
        }
        NIOSocketChannel nioSocketChannel = new NIOSocketChannel(serverSocket);
        return nioSocketChannel;
    }

}
