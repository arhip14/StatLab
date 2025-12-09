package org.example;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StyleTheme {

    // === ПАЛІТРА "DEEP OCEAN" ===
    public static final Color GRADIENT_START = new Color(15, 32, 39);
    public static final Color GRADIENT_END = new Color(32, 58, 67);
    public static final Color ACCENT_CYAN = new Color(0, 255, 200);
    public static final Color ACCENT_PURPLE = new Color(188, 19, 254);
    public static final Color CARD_BG = new Color(30, 40, 50, 200);
    public static final Color TEXT_MAIN = new Color(230, 230, 230);

    // Шрифти
    public static final Font FONT_MAIN = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_CODE = new Font("JetBrains Mono", Font.PLAIN, 13);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 16);

    public static void setup() {
        try {
            FlatMaterialDeepOceanIJTheme.setup();

            UIManager.put("Button.arc", 999);
            UIManager.put("Component.arc", 20);
            UIManager.put("TextComponent.arc", 20);

            UIManager.put("TabbedPane.showTabSeparators", true);
            UIManager.put("TabbedPane.underlineColor", ACCENT_CYAN);

            ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        } catch (Exception ignored) {}
    }

    public static JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, GRADIENT_START, getWidth(), getHeight(), GRADIENT_END);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    public static JTable applyTableStyle(JTable table) {
        table.setFont(FONT_MAIN);
        table.setRowHeight(35);
        table.setBackground(CARD_BG);
        table.setForeground(TEXT_MAIN);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(255, 255, 255, 20));

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(new Color(20, 30, 40));
        header.setForeground(ACCENT_CYAN);
        header.setPreferredSize(new Dimension(0, 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(new Color(0,0,0,0));
        table.setDefaultRenderer(Object.class, centerRenderer);

        return table;
    }

    public static JTextArea applyTextStyle(String text) {
        JTextArea area = new JTextArea(text);
        area.setFont(FONT_CODE);
        area.setEditable(false);
        area.setBackground(CARD_BG);
        area.setForeground(new Color(169, 255, 104));
        // ВИПРАВЛЕНО: Прибрано проблемний стиль 'arc', залишено лише відступи
        area.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return area;
    }

    public static ChartPanel applyChartStyle(JFreeChart chart) {
        chart.setBackgroundPaint(null);

        chart.getTitle().setFont(FONT_TITLE);
        chart.getTitle().setPaint(TEXT_MAIN);

        if (chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(null);
            chart.getLegend().setItemPaint(TEXT_MAIN);
        }

        if (chart.getPlot() instanceof XYPlot) {
            XYPlot plot = (XYPlot) chart.getPlot();
            stylePlot(plot);
            if (plot.getRenderer() instanceof XYLineAndShapeRenderer) {
                XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
                renderer.setSeriesStroke(0, new BasicStroke(3.0f));
                renderer.setSeriesPaint(0, ACCENT_CYAN);
            }
        }

        if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            stylePlot(plot);
            if (plot.getRenderer() instanceof BarRenderer) {
                BarRenderer renderer = (BarRenderer) plot.getRenderer();
                renderer.setBarPainter(new StandardBarPainter());
                renderer.setShadowVisible(false);
                GradientPaint gradient = new GradientPaint(0, 0, ACCENT_CYAN, 0, 0, ACCENT_PURPLE);
                renderer.setSeriesPaint(0, gradient);
            }
        }

        ChartPanel panel = new ChartPanel(chart);
        panel.setOpaque(false);

        // Анімація рамки при наведенні
        panel.setBorder(BorderFactory.createLineBorder(new Color(80,80,80), 1));
        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(ACCENT_CYAN, 1));
            }
            public void mouseExited(MouseEvent e) {
                panel.setBorder(BorderFactory.createLineBorder(new Color(80,80,80), 1));
            }
        });

        return panel;
    }

    private static void stylePlot(org.jfree.chart.plot.Plot plot) {
        plot.setBackgroundPaint(new Color(0, 0, 0, 50));
        plot.setOutlineVisible(false);

        if (plot instanceof XYPlot) {
            XYPlot xy = (XYPlot) plot;
            xy.setDomainGridlinePaint(new Color(255, 255, 255, 30));
            xy.setRangeGridlinePaint(new Color(255, 255, 255, 30));
            styleAxis(xy.getDomainAxis());
            styleAxis(xy.getRangeAxis());
        } else if (plot instanceof CategoryPlot) {
            CategoryPlot cat = (CategoryPlot) plot;
            cat.setRangeGridlinePaint(new Color(255, 255, 255, 30));
            styleAxis(cat.getDomainAxis());
            styleAxis(cat.getRangeAxis());
        }
    }

    private static void styleAxis(Object axisObj) {
        // ВИПРАВЛЕНО: Замість null використовуємо прозорий колір
        Color transparent = new Color(0, 0, 0, 0);

        if (axisObj instanceof ValueAxis) {
            ValueAxis axis = (ValueAxis) axisObj;
            axis.setLabelPaint(TEXT_MAIN);
            axis.setTickLabelPaint(TEXT_MAIN);
            axis.setAxisLinePaint(transparent); // Виправлено тут
        }
        if (axisObj instanceof CategoryAxis) {
            CategoryAxis axis = (CategoryAxis) axisObj;
            axis.setLabelPaint(TEXT_MAIN);
            axis.setTickLabelPaint(TEXT_MAIN);
            axis.setAxisLinePaint(transparent); // Виправлено тут
        }
    }
}