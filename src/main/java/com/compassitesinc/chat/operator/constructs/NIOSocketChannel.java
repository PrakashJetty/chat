package com.compassitesinc.chat.operator.constructs;

import java.nio.channels.ServerSocketChannel;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class NIOSocketChannel {

    private ServerSocketChannel serverSocket = null;

    private String userId;

    public NIOSocketChannel(ServerSocketChannel serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocketChannel getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocketChannel serverSocket) {
        this.serverSocket = serverSocket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
