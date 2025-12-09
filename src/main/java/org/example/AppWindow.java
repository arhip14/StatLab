package org.example;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {
    public AppWindow() {
        setTitle("StatLab 2077: Cyberpunk Edition"); // Крутий заголовок
        setSize(1350, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. ВСТАНОВЛЮЄМО ГРАДІЄНТНИЙ ФОН
        // Беремо спеціальну панель з нашого StyleTheme
        JPanel mainPanel = StyleTheme.createGradientPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel); // Робимо її головною панеллю вікна

        double[] data = LabData.getSortedData();

        // 2. Налаштування вкладок
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(StyleTheme.FONT_BOLD);

        // 3. Створюємо панелі та РОБИМО ЇХ ПРОЗОРИМИ
        // Це необхідно, щоб крізь них було видно наш градієнт
        PanelIntro p1 = new PanelIntro(data);
        p1.setOpaque(false);

        PanelDiscrete p2 = new PanelDiscrete(data);
        p2.setOpaque(false);

        PanelInterval p3 = new PanelInterval(data);
        p3.setOpaque(false);

        // 4. Додаємо вкладки
        tabbedPane.addTab("  1. Головна  ", p1);
        tabbedPane.addTab("  2. Дискретний ряд  ", p2);
        tabbedPane.addTab("  3. Інтервальний ряд  ", p3);

        add(tabbedPane);
    }
}