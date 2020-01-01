package com.compassitesinc.chat.operator.constructs;

import java.util.Properties;

public abstract class ChatCommProducer {

    protected String topic;
    protected static Properties props = new Properties();

    public ChatCommProducer(String topic) {
        super();
        this.topic = topic;
    }

    public abstract void send(byte[] comm);
}
