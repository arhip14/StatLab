package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        StyleTheme.setup();
        SwingUtilities.invokeLater(() -> {
            AppWindow app = new AppWindow();
            app.setVisible(true);
        });
    }
}