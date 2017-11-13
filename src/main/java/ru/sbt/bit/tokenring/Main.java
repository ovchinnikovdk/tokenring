package ru.sbt.bit.tokenring;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TokenRing tokenRing = new TokenRing(10);
        tokenRing.startTokenRing();
        for (int i = 0; i < 2000000; i++) {
            tokenRing.sendMessage(new NetMessage("Mes " + i, 128));
        }
        Thread.sleep(1000000);
        tokenRing.stopTokenRing();
    }
}
