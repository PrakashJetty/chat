package com.compassitesinc.chat.operator.constructs;

//import com.compassitesinc.chat.socket.server.ChatServerNIOSocket;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class KafkaChatCommInConsumer extends ChatCommConsumer implements Runnable {

    private KafkaConsumer<String, byte[]> kc;
    private ExecutorService consumerExecutors;

    static {
        props.put("bootstrap.servers", "localhost:9092");

        props.put("enable.auto.commit", "false");

        props.put("group.id", "test");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
    }

    public KafkaChatCommInConsumer(String topic) {
        super(topic);

        kc = new KafkaConsumer<>(props);
        kc.subscribe(Arrays.asList(topic));
        // TODO Auto-generated constructor stub
    }

//    public KafkaChatCommInConsumer(String topic, Queue<ChatServerNIOSocket> q) {
//        super(topic, q);
//        kc = new KafkaConsumer<>(props);
//        kc.subscribe(Arrays.asList(topic));
//    }

    public KafkaChatCommInConsumer(String topic, ChatServerProtocolHandler protocolHandler) {
        super(topic, protocolHandler);
        kc = new KafkaConsumer<>(props);
        kc.subscribe(Arrays.asList(topic));
        consumerExecutors = Executors.newFixedThreadPool(4);


        // TODO Auto-generated constructor stub
    }

    public KafkaChatCommInConsumer getInstance(String topic) {
        return new KafkaChatCommInConsumer(topic);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        while (true) {
            ConsumerRecords<String, byte[]> records = kc.poll(100);
            for (ConsumerRecord<String, byte[]> record : records) {
                consume(record.value(), record.key());
            }

        }

    }

    @Override
    public void consume(byte[] commBytes, String topic) {
        // TODO Auto-generated method stub
//		if (topic.equalsIgnoreCase("socket-out-topic")) {
//			ExecutorService executor = Executors.newFixedThreadPool(1);
//	        executor.submit(new ChatNIOServerChannelWriteProcessor(ChatCommMessageEncoder.deserialize(commBytes)));
//		} else if (topic.equalsIgnoreCase("socket-in-topic")) {
//			Thread executor = new Thread(this.protocolHandler);
//			this.protocolHandler.setComm(ChatCommMessageEncoder.deserialize(commBytes));
//		System.out.println("in kafka consumed");
//	        executor.start();

//		Thread executor = new Thread(this.protocolHandler);
//		this.protocolHandler.setComm(ChannelContextSerializerDeserializer.deserialize(commBytes));
//		System.out.println("in kafka consumed");
//		executor.start();
        ChatServerProtocolHandler protocolHandler = new ChatServerProtocolHandler(ChannelContextSerializerDeserializer.deserialize(commBytes));
        consumerExecutors.submit(protocolHandler);
        System.out.println("in kafka consumed");
        //}
    }

}
