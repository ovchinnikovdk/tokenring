package ru.sbt.bit.tokenring;


public class NetMessage {
    private int ttl = 128;
    private String msg;

    public NetMessage(){
        this.msg = "bitbitbitbit";
        this.ttl = 128;
    }

    public NetMessage(int ttl) {
        this();
        this.ttl = ttl;
    }

    public NetMessage(String message, int ttl) {
        this.msg = message;
        this.ttl = ttl;
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
}
