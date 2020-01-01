package com.compassitesinc.chat.operator.constructs;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by prakashjetty on 11/15/18.
 */
public class ChannelContextSerializerDeserializer {

    public static byte[] serialize(ChannelMessageContext comm) {
        try {
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            byte[] serialized = mapper.writeValueAsBytes(comm);
            return serialized;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // TODO
        }
    }

    public static ChannelMessageContext deserialize(byte[] commBytes) {

        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            //TODO handle error
            return mapper.readValue(commBytes, ChannelMessageContext.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
