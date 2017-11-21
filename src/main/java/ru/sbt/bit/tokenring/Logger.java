package ru.sbt.bit.tokenring;

import java.util.*;

public class Logger {
    private Map<Long, List<Double>> latencyMap;
    private Map<Long, Integer> throughputMap;

    public Logger() {
        this.latencyMap = new TreeMap<Long, List<Double>>();
        this.throughputMap = new TreeMap<Long, Integer>();
    }

    public void log(NetMessage msg) {
        Date date = new Date();
        long time = date.getTime() / 1000;
        synchronized (latencyMap) {
            List<Double> list = latencyMap.get(time);
            if (list == null) {
                List<Double> newList = new ArrayList<Double>();
                newList.add((date.getTime() - msg.getDate().getTime()) / 1000.0);
                latencyMap.put(time, newList);
            }
            else {
                list.add((date.getTime() - msg.getDate().getTime()) / 1000.0);
            }
        }
        synchronized (throughputMap) {
            Integer count = throughputMap.get(time);
            if (count == null) {
                throughputMap.put(time, 1);
            }
            else {
                throughputMap.put(time, count + 1);
            }
        }
    }
    public void printStatistics() {
        double sum = 0;
        for (Integer value : throughputMap.values()) {
            sum += value;
        }
        System.out.println("Average throughput: " + sum / throughputMap.size() + " messages/sec");
        for (Long time : latencyMap.keySet()) {
            double sumLatency = 0;
            for (double latency : latencyMap.get(time)) {
                sumLatency += latency;
            }
            System.out.println("Average latency at time: " + time + " is "
                    + sumLatency / latencyMap.get(time).size()
                    + "(" + latencyMap.get(time).size() + " messages)");
        }

    }

}
