package ru.sbt.bit.tokenring;

public class TokenRingRunner {
    public static Logger runTokenring(int nodes, int messages, int sleep) throws InterruptedException {
        TokenRing tokenRing = new TokenRing(nodes);
        tokenRing.startTokenRing();
        for (int i = 0; i < messages; i++) {
            tokenRing.sendMessage(new NetMessage(nodes * 2));
        }
        Thread.sleep(sleep);
        tokenRing.stopTokenRing();
        return tokenRing.getLogger();
    }
}
