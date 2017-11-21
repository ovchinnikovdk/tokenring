package ru.sbt.bit.tokenring;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 8;
        TokenRing tokenRing = new TokenRing(n);
        tokenRing.startTokenRing();
        for (int i = 0; i < 2000000; i++) {
            tokenRing.sendMessage(new NetMessage("Mes " + i, Math.min(n, 128)));
        }
        Thread.sleep(10000);
        tokenRing.stopTokenRing();
        tokenRing.printStatistics();
    }
}
