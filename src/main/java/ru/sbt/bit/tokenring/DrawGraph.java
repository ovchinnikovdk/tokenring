package ru.sbt.bit.tokenring;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;

/**
 * Created by SBT-Ovchinnikov-DK on 22.11.2017.
 */
public class DrawGraph {
    public void draw(double[] x, double[] y) {
        XYSeries series = new XYSeries("Throughput");
        for (int i = 0; i < x.length; i++) {
            series.add(x[i], y[i]);
        }

        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart("Throughput",
                "time",
                "messages",
                collection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        try {
            ChartUtilities.saveChartAsPNG(new File("result/test_throughput.png"), chart, 800, 800);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
