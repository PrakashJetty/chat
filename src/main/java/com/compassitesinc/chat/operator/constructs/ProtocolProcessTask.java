package com.compassitesinc.chat.operator.constructs;

/**
 * Created by prakashjetty on 11/15/18.
 */
public abstract class ProtocolProcessTask {

    public abstract void processStep(ChannelMessageContext channelMessageContext);
}
