package ru.sbt.bit.tokenring;


import java.util.Date;

public class NetMessage {
    private int ttl = 128;
    private String msg;
    private Date date;

    public NetMessage(String message, int ttl) {
        this.msg = message;
        this.ttl = ttl;
        this.date = new Date();
    }

    public synchronized void decTtl() {
        this.ttl--;
    }

    public boolean isDead() {
        return ttl <= 0;
    }

    @Override
    public String toString() {
        return "NetMessage{" +
                "msg='" + msg + '\'' +
                '}';
    }

    public double latency() {
        return ((new Date()).getTime() - this.date.getTime()) / 1000.0;
    }
}
