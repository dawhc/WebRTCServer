/**
 * SignalServer.class
 * Author: Cui Donghang
 * Version: 1.0
 * Date: 2019.12.20
 */

package com.example.signalserver;

import com.example.signalserver.data.Constant;
import com.example.signalserver.model.Signal;
import com.google.gson.Gson;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@ServerEndpoint("/websocket")
public class SignalServer {

    private Session session;

    /**
     * Trigger when client joins
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("onOpen " + session.getId());
        WebSocketMapUtil.put(session.getId(), this);
    }


    /**
     * Trigger when client leaves
     */

    @OnClose
    public void onClose() {
        //从map中删除
        WebSocketMapUtil.remove(session.getId());
        Set<Map.Entry<String, String>> entries = ClientMapUtil.webSocketMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String value = next.getValue();
            if (StringUtils.pathEquals(session.getId(), value)) {
                iterator.remove();
            }
        }
        System.out.println("====== onClose:" + session.getId() + " ======");
    }


    /**
     * Trigger when server receives message from socket client
     */

    @OnMessage
    public void onMessage(String params, Session session) {
        SignalServer myWebSocket = WebSocketMapUtil.get(session.getId());
        System.out.println("收到来自" + session.getId() + "的消息" + params);
        if (!StringUtils.isEmpty(params)) {
            Gson gson = new Gson();
            try {
                Signal s = gson.fromJson(params, Signal.class);
                if (s != null) {
                    Constant.Event event = s.getSignal();
                    if (event != null) {
                        switch (event) {
                            case JOIN:
                                register(myWebSocket, s);
                                break;
                            case ANSWER:
                                answer(s);
                                break;
                            case OFFER:
                                offer(s);
                                break;
                            case CANDIDATE:
                                candidate(s);
                                break;
                            case LEAVE:
                                leave(s);
                                break;
                        }
                    }
                }
            } catch (Exception e) {

            }
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println(session.getId() + "连接发生错误" + error.getMessage());
        error.printStackTrace();
    }

    /** Convert Signal to String in json format */
    private void sendMessage(Signal message) {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(message));
        } catch (Exception e) {

        }
    }

    /**
     * Register after signing in
     *
     * @param myWebSocket
     * @param s
     */
    private void register(SignalServer myWebSocket, Signal s) {
        String from = s.getLocalUser();
        String sessionFrom = ClientMapUtil.webSocketMap.get(from);
        if (StringUtils.isEmpty(sessionFrom)) {
            ClientMapUtil.webSocketMap.put(from, myWebSocket.session.getId());
            myWebSocket.sendMessage(new Signal(0, "Successful", Constant.Event.JOIN, "", null, null));
        } else {
            myWebSocket.sendMessage(new Signal(1, "Failed to register!", Constant.Event.JOIN, "", null, null));
        }
    }

    /**
     * Check the availability send OfferSdp to another client
     *
     * @param s
     */
    private void offer(Signal s) {
        String to = s.getRemoteUser();
        String sessionTo = ClientMapUtil.get(to);
        if (!StringUtils.isEmpty(sessionTo)) {
            SignalServer socketServer = WebSocketMapUtil.get(sessionTo);
            if (socketServer != null)
                socketServer.sendMessage(new Signal(0, "Successful", Constant.Event.OFFER, s.getLocalUser(), s.getSdp(), null));
            else
                WebSocketMapUtil.get(ClientMapUtil.get(s.getLocalUser())).sendMessage(new Signal(1, "Remote user is not online", Constant.Event.ANSWER, "", null, null));
        }
        else
            WebSocketMapUtil.get(ClientMapUtil.get(s.getLocalUser())).sendMessage(new Signal(1, "Remote user is not online", Constant.Event.ANSWER, "", null, null));
    }

    /**
     * Send AnswerSdp to another client
     *
     * @param s
     */
    private void answer(Signal s) {
        String to = s.getRemoteUser();
        String sessionTo = ClientMapUtil.get(to);
        if (!StringUtils.isEmpty(sessionTo)) {
            SignalServer socketServer = WebSocketMapUtil.get(sessionTo);
            if (socketServer != null) {
                if (s.getSdp() == null)
                    socketServer.sendMessage(new Signal(1, "Remote user refused your offer", Constant.Event.ANSWER, s.getLocalUser(), null, null));
                else
                    socketServer.sendMessage(new Signal(0, "Successful", Constant.Event.ANSWER, s.getLocalUser(), s.getSdp(), null));
            }
        }
    }

    /**
     * Transmit IceCandidate between two clients
     *
     * @param s
     */
    private void candidate(Signal s) {
        String to = s.getRemoteUser();
        String sessionTo = ClientMapUtil.get(to);
        if (!StringUtils.isEmpty(sessionTo)) {
            SignalServer socketServer = WebSocketMapUtil.get(sessionTo);
            if (socketServer != null)
                socketServer.sendMessage(new Signal(0, "Successful", Constant.Event.CANDIDATE, s.getLocalUser(), null, s.getCandidate()));
        }
    }

    /**
     * Trigger when a client want to shutdown the connection
     *
     * @param s
     * @throws IOException
     */
    private void leave(Signal s) throws IOException {
        String to = s.getRemoteUser();

        String sessionTo = ClientMapUtil.get(to);
        if (!StringUtils.isEmpty(sessionTo)) {
            SignalServer socketServer = WebSocketMapUtil.get(sessionTo);
            if (socketServer != null) {
                socketServer.sendMessage(new Signal(0, "Successful", Constant.Event.LEAVE, s.getLocalUser(), null, null));
            }
        }
        //this.session.close();
    }


}