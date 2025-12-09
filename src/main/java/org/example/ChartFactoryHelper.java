package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class ChartFactoryHelper {

    // --- ДИСКРЕТНІ ---

    public static JFreeChart createPolygon(TreeMap<Double, Integer> freq, boolean isRelative, int n) {
        String title = isRelative ? "Полігон відносних частот" : "Полігон частот";
        String yLabel = isRelative ? "wi (Відносна частота)" : "ni (Частота)";

        XYSeries series = new XYSeries("Polygon");
        for (Map.Entry<Double, Integer> entry : freq.entrySet()) {
            double y = isRelative ? (double) entry.getValue() / n : entry.getValue();
            series.add((Number) entry.getKey(), (Number) y);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(title, "X", yLabel, new XYSeriesCollection(series), PlotOrientation.VERTICAL, true, true, false);
        customizeXYPlot(chart, isRelative ? new Color(255, 100, 100) : new Color(100, 200, 255));
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

        JFreeChart chart = ChartFactory.createXYStepChart("Емпірична функція F(x)", "X", "F(x)", new XYSeriesCollection(series), PlotOrientation.VERTICAL, true, true, false);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, new Color(255, 150, 100));
        return chart;
    }

    // --- ІНТЕРВАЛЬНІ ---

    public static JFreeChart createHistogram(double[] data, int bins) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("Histogram", data, bins);

        JFreeChart chart = ChartFactory.createHistogram("Гістограма частот", "Інтервали", "ni (Частота)", dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, new Color(100, 255, 100));
        return chart;
    }

    // Гістограма відносних частот (через BarChart для точності відображення wi)
    public static JFreeChart createRelativeHistogram(double[] starts, double[] ends, int[] ni, int n) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < starts.length; i++) {
            String label = String.format("[%.1f; %.1f)", starts[i], ends[i]);
            double wi = (double) ni[i] / n;
            dataset.addValue(wi, "Relative Freq", label);
        }

        JFreeChart chart = ChartFactory.createBarChart("Гістограма відносних частот", "Інтервали", "wi (Відносна частота)", dataset, PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(100, 255, 150));
        plot.setBackgroundPaint(new Color(43, 43, 43));
        plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
        plot.getRangeAxis().setTickLabelPaint(Color.WHITE);

        return chart;
    }

    // Полігон інтервального розподілу (по серединах)
    public static JFreeChart createIntervalPolygon(double[] mids, int[] ni, int n, boolean isRelative) {
        String title = isRelative ? "Полігон інтерв. розподілу (Відносний)" : "Полігон інтерв. розподілу (Частоти)";
        XYSeries series = new XYSeries("Interval Polygon");

        for (int i = 0; i < mids.length; i++) {
            double y = isRelative ? (double) ni[i] / n : ni[i];
            series.add(mids[i], y);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(title, "Середини інтервалів (xi*)", isRelative ? "wi" : "ni", new XYSeriesCollection(series), PlotOrientation.VERTICAL, true, true, false);
        customizeXYPlot(chart, new Color(255, 100, 255));
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
        customizeXYPlot(chart, new Color(200, 100, 255));
        return chart;
    }

    // Допоміжний метод для стилю
    private static void customizeXYPlot(JFreeChart chart, Color color) {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, color);

        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(renderer);
    }
}