/**
 * WebSocketMapUtil.class
 * Author: Cui Donghang
 * Version: 1.0
 * Date: 2019.12.20
 */
package com.example.signalserver;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebSocketMapUtil {
    public static ConcurrentMap<String, SignalServer> webSocketMap = new ConcurrentHashMap<>();

    public static void put(String key, SignalServer webSocketServer) {
        webSocketMap.put(key, webSocketServer);
    }

    public static SignalServer get(String key) {
        return webSocketMap.get(key);
    }

    public static void remove(String key) {
        webSocketMap.remove(key);
    }

    public static Collection<SignalServer> getValues() {
        return webSocketMap.values();
    }
}