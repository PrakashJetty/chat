package com.compassitesinc.chat.operator.constructs;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by prakashjetty on 1/30/16.
 */
public class ChatCommMessageEncoder {


    public static byte[] serialize(ChannelMessage comm) {
        try {
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            byte[] serialized = mapper.writeValueAsBytes(comm);
            return serialized;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // TODO
        }
    }

    public static ChannelMessage deserialize(byte[] commBytes) {

        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            //TODO handle error
            return mapper.readValue(commBytes, ChannelMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ChannelMessage deserialize(String jsonString) {

        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            //TODO handle error
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            return mapper.readValue(jsonString, ChannelMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ChannelMessage deserialize(InputStream inputStream) {

        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            //TODO handle error
            return mapper.readValue(inputStream, ChannelMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
