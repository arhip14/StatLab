package org.example;

import javax.swing.*;
import java.awt.*;

public class PanelIntro extends JPanel {
    public PanelIntro(double[] data) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        StringBuilder sb = new StringBuilder();
        sb.append("==========================================\n");
        sb.append("         УМОВА ЛАБОРАТОРНОЇ РОБОТИ        \n");
        sb.append("==========================================\n\n");
        sb.append(" • Обсяг вибірки (n):  ").append(data.length).append("\n");
        sb.append(" • Мінімум (min):      ").append(data[0]).append("\n");
        sb.append(" • Максимум (max):     ").append(data[data.length - 1]).append("\n");
        sb.append(" • Розмах (R):         ").append(data[data.length - 1] - data[0]).append("\n\n");
        sb.append("ВАРІАЦІЙНИЙ РЯД (Перші 100 елементів):\n");
        sb.append("------------------------------------------\n");

        for (int i = 0; i < data.length; i++) {
            sb.append(String.format("%6.0f", data[i]));
            if ((i + 1) % 10 == 0) sb.append("\n");
        }

        add(new JScrollPane(StyleTheme.applyTextStyle(sb.toString())), BorderLayout.CENTER);
    }
}