package com.example.signalserver.model;

import com.example.signalserver.data.Constant;

public class Signal {
    private Constant.Event signal;
    private String localUser;
    private String remoteUser;
    private IceCandidate candidate;
    private SessionDescription sdp;
    private int code;
    private String msg;

    public Signal(Constant.Event signal, String localUser, String remoteUser, SessionDescription sdp, IceCandidate candidate) {
        this.signal = signal;
        this.localUser = localUser;
        this.remoteUser = remoteUser;
        this.candidate = candidate;
        this.sdp = sdp;
        this.msg = "";
        this.code = 0;
    }

    public Signal(int code, String msg, Constant.Event signal, String remoteUser, SessionDescription sdp, IceCandidate candidate) {
        this.signal = signal;
        this.localUser = null;
        this.remoteUser = remoteUser;
        this.candidate = candidate;
        this.sdp = sdp;
        this.msg = msg;
        this.code = code;
    }

    public Constant.Event getSignal() {
        return signal;
    }

    public void setSignal(Constant.Event signal) {
        this.signal = signal;
    }

    public String getLocalUser() {
        return localUser;
    }

    public void setLocalUser(String localUser) {
        this.localUser = localUser;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public SessionDescription getSdp() {
        return sdp;
    }

    public void setSdp(SessionDescription sdp) {
        this.sdp = sdp;
    }

    public IceCandidate getCandidate() {
        return candidate;
    }

    public void setCandidate(IceCandidate candidate) {
        this.candidate = candidate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
