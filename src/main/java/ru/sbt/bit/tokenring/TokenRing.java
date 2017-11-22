package ru.sbt.bit.tokenring;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TokenRing {
    private List<NetMessage>[] netMessageLists;
    private Thread[] nodes;
    private Logger logger;
    private Random random;

    public TokenRing(int n) {
        logger = new Logger();
        random = new Random();
        netMessageLists = new List[n];
        nodes = new Thread[n];
        for (int i = 0; i < n; i++) {
            netMessageLists[i] = new LinkedList<NetMessage>();
        }
        for (int i = 0; i < n; i++) {
            nodes[i] = new Thread(new Node(netMessageLists[i], netMessageLists[(i + 1) % n], this));
        }
    }

    public void startTokenRing() {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].start();
        }
    }

    public void plotThoughput(){
        double[][] throughPutLine = logger.getThroughPutLine();
        new DrawGraph().draw(throughPutLine[0], throughPutLine[1]);
    }

    public void stopTokenRing() {
        Node.run = false;
    }

    public void sendMessage(NetMessage message) {
        int index = random.nextInt(netMessageLists.length);
        synchronized (netMessageLists[index]) {
            netMessageLists[index].add(message);
        }
    }

    public void log(NetMessage msg) {
        logger.log(msg);
    }
}
