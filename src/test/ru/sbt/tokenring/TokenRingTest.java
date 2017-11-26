package ru.sbt.tokenring;


import org.junit.Test;
import ru.sbt.bit.tokenring.DrawGraph;
import ru.sbt.bit.tokenring.IndexType;
import ru.sbt.bit.tokenring.Logger;
import ru.sbt.bit.tokenring.TokenRingRunner;

import java.util.ArrayList;
import java.util.List;

public class TokenRingTest {

    private final int NUM_MESSAGES = 2000000;
    private final int SLEEP_SEC = 50000;

    private int[] nodes = {8, 16, 24, 32, 40, 48, 56, 64};
    private DrawGraph drawGraph;
    private List<Double> latencyAverage;
    private List<Double> throughputAverage;
    private TokenRingRunner runner;

    public TokenRingTest(){
        drawGraph = new DrawGraph();
        latencyAverage = new ArrayList<Double>();
        throughputAverage = new ArrayList<Double>();
        runner = new TokenRingRunner();
    }

    @Test
    public void testTokenRingWithDifferentNodes() throws InterruptedException {
        for (int n : nodes) {
            Logger logger = runner.runTokenring(n, NUM_MESSAGES, SLEEP_SEC);
            double[][] throughPutLine = logger.getThroughPutLine();
            double[][] latencyLine = logger.getLatencyLine();
            /*
            * calculate average throughput
            * */
            double sum = 0.0;
            for (Double d : throughPutLine[1]) {
                sum += d;
            }
            System.out.println(n + " throughput: " + sum);
            throughputAverage.add(sum / throughPutLine[1].length);
            /*
            * calculate average latency
            * */
            sum = 0;
            for (Double d : latencyLine[1]) {
                sum += d;
            }
            System.out.println(n + " latency: " + sum);
            latencyAverage.add(sum / latencyLine[1].length);

            /*
            * Draw graphics
            * */
            drawGraph.drawGraphicIntoFile(throughPutLine[0],
                    throughPutLine[1],
                    "throughput" + n + "nodes",
                    IndexType.THROUGHPUT);
            drawGraph.drawGraphicIntoFile(latencyLine[0],
                    latencyLine[1],
                    "latency" + n + "nodes",
                    IndexType.LATENCY);
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

        /*
        * Print to See all average results
        * */
        for (int i = 0; i < throughputAvg.length; i++) {
            System.out.println("Latency: " + latencyAvg[i] + " Throughput: " + throughputAvg[i]);
        }

        double[] nodesDouble = new double[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            nodesDouble[i] = nodes[i];
        }
        /*
        * Draw average final graphics
        * */
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
