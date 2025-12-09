package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class PanelDiscrete extends JPanel {
    public PanelDiscrete(double[] data) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.35);

        TreeMap<Double, Integer> freq = new TreeMap<>();
        for (double v : data) freq.put(v, freq.getOrDefault(v, 0) + 1);

        String[] columns = {"Значення (xi)", "Частота (ni)", "Відн. част. (wi)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Map.Entry<Double, Integer> entry : freq.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue(), String.format("%.4f", entry.getValue() / (double) data.length)});
        }

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        leftPanel.add(new JScrollPane(StyleTheme.applyTableStyle(new JTable(model))));
        leftPanel.add(new JScrollPane(StyleTheme.applyTextStyle(Calculations.getDiscreteReport(data, freq))));

        JPanel rightPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        rightPanel.add(StyleTheme.applyChartStyle(ChartFactoryHelper.createPolygon(freq)));
        rightPanel.add(StyleTheme.applyChartStyle(ChartFactoryHelper.createStepCDF(freq)));

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        add(splitPane, BorderLayout.CENTER);
    }
}