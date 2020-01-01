package com.compassitesinc.chat.operator.constructs;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class ChannelMessage implements Comparable<Long> {
    private String message;
    private String contentType;
    private String requestType;
    private ChannelFileMessageFragment channelFileMessageFragment;
    @JsonDeserialize(using = Base64Deserializer.class)
    private Byte[] messageBytes;
    private String toUserId;
    private String fromUserId;
    private String topic;
    private int start;
    private int end;
    private Long sequenceId;
    private String accept;

    public ChannelMessage() {
    }

    public ChannelMessage(String message) {
        this.message = message;
    }

    public ChannelMessage(String message, String toUserId) {
        this.message = message;
        this.toUserId = toUserId;
    }

    public ChannelMessage(String message, String toUserId, String fromUserId) {
        this.message = message;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }

    public ChannelMessage(String message, String toUserId, String fromUserId, String topic) {
        this.message = message;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.topic = topic;
    }

    public ChannelMessage(String message, String toUserId, String fromUserId, String topic, long sequenceId) {
        this.message = message;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.topic = topic;
        this.sequenceId = sequenceId;
    }

    public ChannelMessage(String message, Byte[] messageBytes, String toUserId, String fromUserId, String topic, int start, int end, Long sequenceId) {
        this.message = message;
        this.messageBytes = messageBytes;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.topic = topic;
        this.start = start;
        this.end = end;
        this.sequenceId = sequenceId;
    }

    public Byte[] getMessageBytes() {
        return messageBytes;
    }

    public void setMessageBytes(Byte[] messageBytes) {
        this.messageBytes = messageBytes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ChannelFileMessageFragment getChannelFileMessageFragment() {
        return channelFileMessageFragment;
    }

    public void setChannelFileMessageFragment(ChannelFileMessageFragment channelFileMessageFragment) {
        this.channelFileMessageFragment = channelFileMessageFragment;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    @Override
    public int compareTo(Long other) {

        return this.compareTo(other);
    }


}
