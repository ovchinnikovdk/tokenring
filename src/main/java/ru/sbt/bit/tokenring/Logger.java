package ru.sbt.bit.tokenring;

import java.util.*;

public class Logger {
    private Map<Long, List<Double>> latencyMap;
    private Map<Long, Integer> throughputMap;
    private Date startDate;

    public Logger() {
        this.latencyMap = new TreeMap<Long, List<Double>>();
        this.throughputMap = new TreeMap<Long, Integer>();
        this.startDate = new Date();
    }

    public void log(NetMessage msg) {
        Date date = new Date();
        long time = (date.getTime() - this.startDate.getTime()) / 1000;
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

    public double[][] getThroughPutLine() {
        double result[][] = new double[2][throughputMap.size()];
        int index = 0;
        for (Map.Entry<Long, Integer> entry : throughputMap.entrySet()) {
            result[0][index] = entry.getKey();
            result[1][index++] = entry.getValue();
        }
        return result;
    }

    public double[][] getLatencyLine() {
        double result[][] = new double[2][latencyMap.size()];
        int index = 0;
        for (Map.Entry<Long, List<Double>> entry : latencyMap.entrySet()) {
            double sum = 0.0;
            for (Double value : entry.getValue()) {
                sum += value;
            }
            result[0][index] = entry.getKey();
            result[1][index++] = sum / entry.getValue().size();
        }
        return result;
    }



}
