package com.vojat.Panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ConsolePanel extends JPanel {

    private Color backgroundColor = Color.BLACK;
    private Color foregroundColor = Color.WHITE;
    private String PATH;

    public ConsolePanel(int width, int height, int x, int y, String path) {
        
        this.PATH = path;

        setSize(width, height);
        setLocation(x, y);
        setBackground(backgroundColor);
        setLayout(null);

    }

    public Color setForegroundColor(Color color) { return this.foregroundColor = color; }
    
    public Color setBackgroundColor(Color color) { return this.backgroundColor = color; }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(foregroundColor);
    }
}
