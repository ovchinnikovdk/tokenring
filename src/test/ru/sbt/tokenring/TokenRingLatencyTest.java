package ru.sbt.tokenring;


import org.junit.Before;
import org.junit.Test;
import ru.sbt.bit.tokenring.DrawGraph;
import ru.sbt.bit.tokenring.IndexType;
import ru.sbt.bit.tokenring.Logger;
import ru.sbt.bit.tokenring.TokenRingRunner;

import java.util.ArrayList;
import java.util.List;

public class TokenRingLatencyTest {

    private final int NUM_MESSAGES = 2000000;
    private final int SLEEP_SEC = 20000;

    private int[] nodes = {8, 16, 24, 32, 40, 48, 56, 64};
    private DrawGraph drawGraph;
    private List<Double> latencyAverage;
    private List<Double> throughputAverage;

    @Before
    public void before(){
        drawGraph = new DrawGraph();
        latencyAverage = new ArrayList<Double>();
        throughputAverage = new ArrayList<Double>();
    }

    @Test
    public void testTokenRingWithDifferentNodes() throws InterruptedException {
        for (int n : nodes) {
            Logger logger = TokenRingRunner.runTokenring(n, NUM_MESSAGES, SLEEP_SEC);
            double[][] throughPutLine = logger.getThroughPutLine();
            drawGraph.drawGraphicIntoFile(throughPutLine[0],
                    throughPutLine[1],
                    "throughput" + n + "nodes",
                    IndexType.THROUGHPUT);
            double[][] latencyLine = logger.getLatencyLine();
            drawGraph.drawGraphicIntoFile(latencyLine[0],
                    latencyLine[1],
                    "latency" + n + "nodes",
                    IndexType.LATENCY);
            double sum = 0.0;
            for (Double d : throughPutLine[1]) {
                sum += d;
            }
            throughputAverage.add(sum / throughPutLine[1].length);
            sum = 0;
            for (Double d : latencyLine[1]) {
                sum += d;
            }
            latencyAverage.add(sum / latencyLine[1].length);
        }
        double[] latencyAvg = new double[latencyAverage.size()];
        double[] throughputAvg = new double[throughputAverage.size()];
        int index = 0;
        for (Double d : latencyAverage) {
            latencyAvg[index++] = d;
        }
        index = 0;
        for (Double d : throughputAverage) {
            throughputAvg[index++] = d;
        }
        double[] nodesDouble = new double[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            nodesDouble[i] = nodes[i];
        }
        drawGraph.drawGraphicIntoFile(nodesDouble,
                throughputAvg,
                "throughput_average",
                IndexType.THROUGHPUT);
        drawGraph.drawGraphicIntoFile(nodesDouble,
                latencyAvg,
                "latency_average",
                IndexType.LATENCY);
    }

}
