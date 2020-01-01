package com.compassitesinc.chat.operator.constructs;

import java.nio.channels.SelectionKey;

/**
 * Created by prakashjetty on 11/24/18.
 */
public class NIOChannelActiveInActiveHandler implements Runnable {

    private SelectionKey key;

    public NIOChannelActiveInActiveHandler(SelectionKey key) {
        this.key = key;
    }

    @Override
    public void run() {

    }
}
