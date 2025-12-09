package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class ChartFactoryHelper {

    public static JFreeChart createPolygon(TreeMap<Double, Integer> freq) {
        XYSeries series = new XYSeries("Polygon");
        for (Map.Entry<Double, Integer> entry : freq.entrySet()) {
            series.add((Number) entry.getKey(), (Number) entry.getValue());
        }
        JFreeChart chart = ChartFactory.createXYLineChart("Полігон частот", "X", "n", new XYSeriesCollection(series), PlotOrientation.VERTICAL, true, true, false);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, new Color(100, 200, 255));
        chart.getXYPlot().setRenderer(renderer);
        return chart;
    }

    public static JFreeChart createStepCDF(TreeMap<Double, Integer> freq) {
        XYSeries series = new XYSeries("F(x)");
        int total = 0;
        for (int v : freq.values()) total += v;
        double sum = 0;
        series.add((Number) (freq.firstKey() - 5), (Number) 0);
        for (double x : freq.keySet()) {
            sum += freq.get(x);
            series.add((Number) x, (Number) (sum / total));
        }
        series.add((Number) (freq.lastKey() + 5), (Number) 1);
        JFreeChart chart = ChartFactory.createXYStepChart("Емпірична F(x)", "X", "F(x)", new XYSeriesCollection(series), PlotOrientation.VERTICAL, true, true, false);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, new Color(255, 150, 100));
        return chart;
    }

    public static JFreeChart createHistogram(double[] data, int bins) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("Histogram", data, bins);
        JFreeChart chart = ChartFactory.createHistogram("Гістограма частот", "Інтервали", "Частота", dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, new Color(100, 255, 100));
        return chart;
    }

    public static JFreeChart createIntervalCDF(double[] ends, int[] ni, int n) {
        XYSeries series = new XYSeries("CDF");
        double sum = 0;
        double start = ends[0] - (ends[1] - ends[0]);
        series.add((Number) start, (Number) 0);
        for (int i = 0; i < ends.length; i++) {
            sum += ni[i];
            series.add((Number) ends[i], (Number) (sum / n));
        }
        JFreeChart chart = ChartFactory.createXYLineChart("Кумулята (Інтервальна)", "X", "F(x)", new XYSeriesCollection(series), PlotOrientation.VERTICAL, true, true, false);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, new Color(255, 100, 255));
        chart.getXYPlot().setRenderer(renderer);
        return chart;
    }
}