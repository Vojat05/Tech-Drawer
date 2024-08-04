package com.vojat.Panels;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LineSettingsPanel extends JPanel {

    public static byte thickness = 3;
    private int width;
    
    public LineSettingsPanel(int width, int height, Color backgroundColor, Frame parent) {
        this.width = width;

        setLayout(null);
        setSize(width, height);
        setBackground(backgroundColor);

        JTextField thicknessTF = new JTextField();
        thicknessTF.setText("" + thickness);
        thicknessTF.setSize(50, 25);
        thicknessTF.setLocation(20, 60);
        add(thicknessTF);

        JButton close = new JButton("X");
        close.setSize(50, 30);
        close.setLocation(width - 50, 0);
        close.setForeground(Color.WHITE);
        close.setBackground(Color.RED);
        close.setFocusPainted(false);
        close.setBorderPainted(false);
        close.addActionListener((e) -> parent.dispose());
        add(close);

        JButton apply = new JButton("Apply");
        apply.setSize(100, 50);
        apply.setLocation(width - 120, height - 70);
        apply.addActionListener((e) -> {

            thickness = Byte.parseByte(thicknessTF.getText());
            parent.dispose();

        });
        add(apply);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 1; i < 10; i++) {
            g2d.drawLine(10, 35 * i, width - 10, 35 * i);
        }
    }
}
