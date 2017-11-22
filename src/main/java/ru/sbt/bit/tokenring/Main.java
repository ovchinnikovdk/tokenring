package ru.sbt.bit.tokenring;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 8;
        TokenRing tokenRing = new TokenRing(n);
        tokenRing.startTokenRing();
        for (int i = 0; i < 2000000; i++) {
            tokenRing.sendMessage(new NetMessage(n * 2));
        }
        Thread.sleep(20000);
        tokenRing.stopTokenRing();
        tokenRing.plotThoughput();
    }
}
