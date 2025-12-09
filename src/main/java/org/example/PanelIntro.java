package org.example;

import javax.swing.*;
import java.awt.*;

public class PanelIntro extends JPanel {
    public PanelIntro(double[] data) {
        setLayout(new BorderLayout());
        setOpaque(false); // Важливо: панель має бути прозорою
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Більші відступи

        StringBuilder sb = new StringBuilder();
        sb.append("==========================================\n");
        sb.append("         УМОВА ЛАБОРАТОРНОЇ РОБОТИ        \n");
        sb.append("==========================================\n\n");
        sb.append(" • Обсяг вибірки (n):  ").append(data.length).append("\n");
        sb.append(" • Мінімум (min):      ").append(data[0]).append("\n");
        sb.append(" • Максимум (max):     ").append(data[data.length - 1]).append("\n");
        sb.append(" • Розмах (R):         ").append(String.format("%.2f", data[data.length - 1] - data[0])).append("\n\n");
        sb.append("ВАРІАЦІЙНИЙ РЯД (Перші 100 елементів):\n");
        sb.append("------------------------------------------\n");

        for (int i = 0; i < data.length; i++) {
            sb.append(String.format("%6.0f", data[i]));
            if ((i + 1) % 10 == 0) sb.append("\n");
        }

        // Застосовуємо наш стильний шрифт і кольори
        JTextArea textArea = StyleTheme.applyTextStyle(sb.toString());

        // Налаштовуємо скрол, щоб він був красивим і не перекривав дизайн
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null); // Прибираємо зайві рамки

        add(scrollPane, BorderLayout.CENTER);
    }
}