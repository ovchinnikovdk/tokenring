package ru.sbt.bit.tokenring;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TokenRing {
    private List<NetMessage>[] netMessageLists;
    private Thread[] nodes;
    private int deadMessages;
    private List<Double> latencies;

    public TokenRing(int n) {
        netMessageLists = new List[n];
        nodes = new Thread[n];
        for (int i = 0; i < n; i++) {
            netMessageLists[i] = new LinkedList<NetMessage>();
        }
        for (int i = 0; i < n; i++) {
            nodes[i] = new Thread(new Node(netMessageLists[i], netMessageLists[(i + 1) % n], this));
        }
        deadMessages = 0;
        latencies = new ArrayList<Double>();
    }

    public void startTokenRing() {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].start();
        }
    }

    public void stopTokenRing() {
        Node.run = false;
    }

    public void sendMessage(NetMessage message) {
        synchronized (netMessageLists[0]) {
            netMessageLists[0].add(message);
        }
    }

    public void markDead(NetMessage msg) {
        double latency = msg.latency();
        synchronized (this) {
            deadMessages++;
        }
        synchronized (latencies) {
            latencies.add(latency);
        }
        //System.out.println("Dead: " + deadMessages + ". Message was: " + msg.toString());
    }

    public void printStatistics(){
        System.out.println("-----====Statistics====-----");
        double sum = 0.0;
        for (double difftime : latencies) sum += difftime;
        double avg = sum / latencies.size();
        System.out.println("Average latency: " + avg);
    }
}
