package org.example;

import javax.swing.*;

public class AppWindow extends JFrame {
    public AppWindow() {
        setTitle("StatLab Enterprise Edition");
        setSize(1300, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        double[] data = LabData.getSortedData();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(StyleTheme.FONT_BOLD);

        tabbedPane.addTab("  1. Умова  ", new PanelIntro(data));
        tabbedPane.addTab("  2. Дискретний ряд  ", new PanelDiscrete(data));
        tabbedPane.addTab("  3. Інтервальний ряд  ", new PanelInterval(data));

        add(tabbedPane);
    }
}