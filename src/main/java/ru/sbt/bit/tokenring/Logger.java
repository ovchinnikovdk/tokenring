package ru.sbt.bit.tokenring;

import java.util.*;

public class Logger {
    private Map<Long, List<Double>> latencyMap;
    private Map<Long, Integer> throughputMap;

    public Logger() {
        this.latencyMap = new HashMap<Long, List<Double>>();
        this.throughputMap = new HashMap<Long, Integer>();
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

}
