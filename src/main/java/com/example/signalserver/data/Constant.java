/**
 * Constant.class
 * Author: Cui Donghang
 * Version: 1.0
 * Date: 2019.12.20
 */
package com.example.signalserver.data;

public class Constant {

    static final String HOST = "192.168.43.74";
    static final String PORT = "8080";
    public static final String WS_SERVER = "ws://"+HOST+":"+PORT;
    public static final String STUN_SERVER = "stun:stun.l.google.com:19302";
    public static final String CHANNEL = "channel";
    public static final String OPEN = "open";
    public static final int VOLUME = 5;
    public static final int VIDEO_WIDTH = 320;
    public static final int VIDEO_HEIGHT = 240;
    public static final int VIDEO_FPS = 60;
    public enum Event {
        JOIN, OFFER, ANSWER, CANDIDATE, LEAVE, STATUS
    }
}
