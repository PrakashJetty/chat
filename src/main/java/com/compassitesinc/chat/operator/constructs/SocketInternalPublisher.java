package com.compassitesinc.chat.operator.constructs;

public class SocketInternalPublisher {

    private String topic;

    public SocketInternalPublisher() {
    }

    public SocketInternalPublisher(String topic) {
        this.topic = topic;
    }

    public void publishSocket(byte[] bytes) {
        ChatCommProducerFactory.getInstance().getInstance("kafka", this.getTopic()).send(bytes);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
