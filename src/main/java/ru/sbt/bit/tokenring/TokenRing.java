package ru.sbt.bit.tokenring;

import java.util.LinkedList;
import java.util.List;

public class TokenRing {
    private List<NetMessage>[] netMessageLists;
    private Thread[] nodes;
    private Logger logger;

    public TokenRing(int n) {
        netMessageLists = new List[n];
        nodes = new Thread[n];
        for (int i = 0; i < n; i++) {
            netMessageLists[i] = new LinkedList<NetMessage>();
        }
        for (int i = 0; i < n; i++) {
            nodes[i] = new Thread(new Node(netMessageLists[i], netMessageLists[(i + 1) % n], this));
        }
        logger = new Logger();
    }

    public void startTokenRing() {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].start();
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void stopTokenRing() {
        Node.run = false;
    }

    public void sendMessage(NetMessage message) {
        synchronized (netMessageLists[0]) {
            netMessageLists[0].add(message);
        }
    }
}
