/**
 * ClientMapUtil.class
 * Author: Cui Donghang
 * Version: 1.0
 * Date: 2019.12.20
 */
package com.example.signalserver;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClientMapUtil {
    public static ConcurrentMap<String, String> webSocketMap = new ConcurrentHashMap<>();

    public static void put(String key, String value) {
        webSocketMap.put(key, value);
    }

    public static String get(String key) {
        return webSocketMap.get(key);
    }

    public static void remove(String key) {
        webSocketMap.remove(key);
    }

    public static Collection<String> getValues() {
        return webSocketMap.values();
    }
}