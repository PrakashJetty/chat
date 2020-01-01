package com.compassitesinc.chat.operator.constructs;


public class ChatCommProducerFactory {

    private String typeMessaging = "kafka";
    private static ChatCommProducerFactory instance;
    private String topic = "socket-in-topic";

    private ChatCommProducerFactory() {
        super();

    }


    public ChatCommProducer getInstance(String type, String topic) {
        ChatCommProducer scp = null;
        if (type.equalsIgnoreCase("kafka")) {
            scp = new KafkaChatCommProducer(topic);
        }
        return scp;
    }

    public static ChatCommProducerFactory getInstance() {
        if (instance == null) {
            synchronized (ChatCommProducerFactory.class) {
                if (instance == null) {
                    instance = new ChatCommProducerFactory();
                }
            }
        }
        return instance;
    }

}
