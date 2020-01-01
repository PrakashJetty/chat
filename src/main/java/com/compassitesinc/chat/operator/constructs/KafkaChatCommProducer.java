package com.compassitesinc.chat.operator.constructs;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaChatCommProducer extends ChatCommProducer {

    private KafkaProducer<String, byte[]> kp;

    static {

        props.put("bootstrap.servers", "localhost:9092");
        props.put("metadata.broker.list", "localhost:9092");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
    }

    public KafkaChatCommProducer(String topic) {
        super(topic);

        kp = new KafkaProducer<>(props);

        // TODO Auto-generated constructor stub
    }

    public KafkaChatCommProducer getInstance(String topic) {
        return new KafkaChatCommProducer(topic);
    }

    @Override
    public void send(byte[] comm) {
        // TODO Auto-generated method stub
        kp.send(new ProducerRecord<String, byte[]>(topic, comm));
        System.out.println("sending message.." + topic);

    }
}
