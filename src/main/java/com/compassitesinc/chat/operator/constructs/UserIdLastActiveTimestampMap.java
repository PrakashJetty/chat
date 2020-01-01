package com.compassitesinc.chat.operator.constructs;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by prakashjetty on 11/27/18.
 */
public class UserIdLastActiveTimestampMap {

    private static ConcurrentHashMap<String, Long> lastActiveTimestampMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Long> getLastActiveTimestampMap() {
        return lastActiveTimestampMap;
    }

    public static void setLastActiveTimestampMap(ConcurrentHashMap<String, Long> lastActiveTimestampMap) {
        UserIdLastActiveTimestampMap.lastActiveTimestampMap = lastActiveTimestampMap;
    }
}
