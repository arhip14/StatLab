package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;

import javax.swing.*;
import java.awt.*;

public class StyleTheme {
    public static final Font FONT_REGULAR = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONT_MONO = new Font("Monospaced", Font.PLAIN, 13);

    public static void setup() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        } catch (Exception ignored) {
        }
    }

    public static JTable applyTableStyle(JTable table) {
        table.setFont(FONT_REGULAR);
        table.setRowHeight(24);
        table.getTableHeader().setFont(FONT_BOLD);
        return table;
    }

    public static JTextArea applyTextStyle(String text) {
        JTextArea area = new JTextArea(text);
        area.setFont(FONT_MONO);
        area.setEditable(false);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return area;
    }

    public static ChartPanel applyChartStyle(JFreeChart chart) {
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 16));

        if (chart.getPlot() instanceof org.jfree.chart.plot.XYPlot) {
            org.jfree.chart.plot.XYPlot plot = (org.jfree.chart.plot.XYPlot) chart.getPlot();
            plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        }

        ChartPanel panel = new ChartPanel(chart);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return panel;
    }
}