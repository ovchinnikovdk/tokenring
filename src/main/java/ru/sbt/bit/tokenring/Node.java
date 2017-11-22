package ru.sbt.bit.tokenring;

import java.util.Iterator;
import java.util.List;

public class Node implements Runnable {
    public static volatile boolean run = true;
    private List<NetMessage> getQueue;
    private List<NetMessage> putQueue;
    private TokenRing tokenRing;

    public Node(List<NetMessage> getQueue, List<NetMessage> putQueue, TokenRing tokenRing) {
        this.getQueue = getQueue;
        this.putQueue = putQueue;
        this.tokenRing = tokenRing;
    }

    public void run() {
        while(Node.run) {
            if (!getQueue.isEmpty()) {
                NetMessage netMessage;
                synchronized (getQueue) {
                    Iterator<NetMessage> iterator = getQueue.iterator();
                    netMessage = iterator.next();
                    iterator.remove();
                }
                netMessage.decTtl();
                if (netMessage.isDead()) {
                    //Log for statistics
                    tokenRing.log(netMessage);
                }
                else synchronized (putQueue) {
                    putQueue.add(netMessage);
                }
            }
        }
    }
}
