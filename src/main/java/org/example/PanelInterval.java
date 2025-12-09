package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelInterval extends JPanel {
    public PanelInterval(double[] data) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.35);

        int k = 10;
        double min = data[0];
        double max = data[data.length - 1];
        double h = (max - min) / k;
        int n = data.length;

        double[] starts = new double[k];
        double[] ends = new double[k];
        double[] mids = new double[k];
        int[] ni = new int[k];

        for (int i = 0; i < k; i++) {
            starts[i] = min + i * h;
            ends[i] = min + (i + 1) * h;
            if (i == k - 1) ends[i] = max + 0.0001;
            mids[i] = (starts[i] + ends[i]) / 2.0;
        }

        for (double v : data) {
            for (int i = 0; i < k; i++) {
                boolean inRange = (i == k - 1) ? (v >= starts[i] && v <= ends[i]) : (v >= starts[i] && v < ends[i]);
                if (inRange) {
                    ni[i]++;
                    break;
                }
            }
        }

        String[] columns = {"Інтервал", "Середина (xi*)", "Частота (ni)", "Відн. част (wi)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (int i = 0; i < k; i++) {
            String interval = String.format("[%.2f; %.2f)", starts[i], ends[i]);
            model.addRow(new Object[]{interval, String.format("%.4f", mids[i]), ni[i], String.format("%.4f", ni[i] / (double) n)});
        }

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        leftPanel.add(new JScrollPane(StyleTheme.applyTableStyle(new JTable(model))));
        leftPanel.add(new JScrollPane(StyleTheme.applyTextStyle(Calculations.getIntervalReport(starts, h, ni, mids, n))));

        JTabbedPane chartsTabs = new JTabbedPane();

        // Гістограми
        chartsTabs.addTab("Гістограма частот", StyleTheme.applyChartStyle(ChartFactoryHelper.createHistogram(data, k)));
        chartsTabs.addTab("Гістограма відн. частот", StyleTheme.applyChartStyle(ChartFactoryHelper.createRelativeHistogram(starts, ends, ni, n)));

        // Полігони
        chartsTabs.addTab("Полігон інтерв. (Частоти)", StyleTheme.applyChartStyle(ChartFactoryHelper.createIntervalPolygon(mids, ni, n, false)));
        chartsTabs.addTab("Полігон інтерв. (Відносний)", StyleTheme.applyChartStyle(ChartFactoryHelper.createIntervalPolygon(mids, ni, n, true)));

        // Кумулята
        chartsTabs.addTab("Кумулята F(x)", StyleTheme.applyChartStyle(ChartFactoryHelper.createIntervalCDF(ends, ni, n)));

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(chartsTabs);
        add(splitPane, BorderLayout.CENTER);
    }
}