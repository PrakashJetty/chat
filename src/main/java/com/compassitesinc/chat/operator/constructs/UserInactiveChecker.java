package com.compassitesinc.chat.operator.constructs;

import java.util.Map;

/**
 * Created by prakashjetty on 11/27/18.
 */
public class UserInactiveChecker implements Runnable {


    private boolean stop = false;

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    public void run() {

        while (!stop) {
            for (Map.Entry<String, Long> entry :
                    UserIdLastActiveTimestampMap.getLastActiveTimestampMap().entrySet()) {

            }
        }


    }
}
