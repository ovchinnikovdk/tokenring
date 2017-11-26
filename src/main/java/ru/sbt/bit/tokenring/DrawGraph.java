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
    public void drawGraphicIntoFile(double[] x, double[] y, String graphicName, IndexType type) {
        String xAxisLabel = "time";
        String yAxisLabel = "messages";

        if (type == IndexType.LATENCY) {
            xAxisLabel = "time";
            yAxisLabel = "latency";
        }
        else if (type == IndexType.THROUGHPUT) {
            xAxisLabel = "time";
            yAxisLabel = "messages";
        }

        XYSeries series = new XYSeries(type.toString());
        for (int i = 0; i < x.length; i++) {
            series.add(x[i], y[i]);
        }

        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(graphicName,
                xAxisLabel,
                yAxisLabel,
                collection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        try {
            ChartUtilities.saveChartAsPNG(new File("result/" + graphicName + ".png"), chart, 800, 800);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
