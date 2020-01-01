package com.compassitesinc.chat.operator.constructs;

import java.util.Queue;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class NIOChannelSelectorPool {

    private Queue<NIOChannelSelector> selectorQueue;


    public NIOChannelSelectorPool(Queue<NIOChannelSelector> selectorQueue) {
        this.selectorQueue = selectorQueue;
    }

    public Queue<NIOChannelSelector> getSelectorQueue() {
        return selectorQueue;
    }

    public void setSelectorQueue(Queue<NIOChannelSelector> selectorQueue) {
        this.selectorQueue = selectorQueue;
    }
}
