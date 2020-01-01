package com.compassitesinc.chat.operator.constructs;

//import com.compassitesinc.chat.socket.server.ChatServerNIOSocket;

import java.util.Properties;

public abstract class ChatCommConsumer {

    protected String topic;
    protected static Properties props = new Properties();
    //    protected Queue<ChatServerNIOSocket> q;
    protected ChatServerProtocolHandler protocolHandler;

    public ChatCommConsumer(String topic) {
        super();
        this.topic = topic;
    }

//    public ChatCommConsumer(String topic, Queue<ChatServerNIOSocket> q) {
//        super();
//        this.topic = topic;
//        this.q = q;
//    }


    public ChatCommConsumer(String topic, ChatServerProtocolHandler protocolHandler) {
        super();
        this.topic = topic;
        this.protocolHandler = protocolHandler;
    }

    public abstract void consume(byte[] commBytes, String topic);
}
