package com.compassitesinc.chat.operator.constructs;

public class ChatCommConsumerFactory {

    private String typeMessaging = "kafka";
    private static ChatCommConsumerFactory instance;
    private String topic = "socket-topic";

    private ChatCommConsumerFactory() {
        super();

    }


    public ChatCommConsumer getInstance(String type, String topic) {
        ChatCommConsumer scp = null;
        if (type.equalsIgnoreCase("kafka")) {
            if (topic.equalsIgnoreCase("socket-in-topic")) {
                scp = new KafkaChatCommInConsumer(topic);
            } else if (topic.equalsIgnoreCase("socket-out-topic")) {
                scp = null;//new KafkaChatCommOutConsumer(topic);
            }
        }
        return scp;
    }

//    public ChatCommConsumer getInstance(String type, String topic, Thread t, Queue<ChatServerNIOSocket> q) {
//        ChatCommConsumer scp = null;
//        if (type.equalsIgnoreCase("kafka")) {
//            if (topic.equalsIgnoreCase("socket-in-topic")) {
//                scp = new KafkaChatCommInConsumer(topic, q);
//            } else if (topic.equalsIgnoreCase("socket-out-topic")) {
//                scp = new KafkaChatCommOutConsumer(topic, q);
//            }
//        }
//        return scp;
//    }

    public ChatCommConsumer getInstance(String type, String topic, ChatServerProtocolHandler protocolHandler) {
        ChatCommConsumer scp = null;
        if (type.equalsIgnoreCase("kafka")) {
            scp = new KafkaChatCommInConsumer(topic, protocolHandler);
//			scp = topic.equalsIgnoreCase("socket-in-topic") ? new KafkaChatCommInConsumer(topic, protocolHandler) : new KafkaChatCommOutConsumer(topic);
        }
        return scp;
    }

    public static ChatCommConsumerFactory getInstance() {
        if (instance == null) {
            synchronized (ChatCommConsumerFactory.class) {
                if (instance == null) {
                    instance = new ChatCommConsumerFactory();
                }
            }
        }
        return instance;
    }

}
