package com.compassitesinc.chat.operator.constructs;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.channels.SocketChannel;

/**
 * Created by prakashjetty on 11/15/18.
 */
public class ChannelMessageContext {


    private ChannelMessage channelMessage;

    private String fileDirectory;


    @JsonIgnore
    private SocketChannel client;

    private String topic;

    private boolean success;

    private String statsuMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public SocketChannel getClient() {
        return client;
    }

    public void setClient(SocketChannel client) {
        this.client = client;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ChannelMessage getChannelMessage() {
        return channelMessage;
    }

    public void setChannelMessage(ChannelMessage channelMessage) {
        this.channelMessage = channelMessage;
    }

    public String getStatsuMessage() {
        return statsuMessage;
    }

    public void setStatsuMessage(String statsuMessage) {
        this.statsuMessage = statsuMessage;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }
}
