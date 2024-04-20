package com.vojat.Panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.vojat.Frame;
import com.vojat.Listeners.KeyboardListener;
import com.vojat.Listeners.MouseListener;

public class BluePrint extends JPanel {

    private Frame parentFrame;

    public BluePrint(int x, int y, int width, int height, Color color) {
        setSize(width, height);
        setLocation(x, y);
        setBackground(color);
        setFocusable(true);
        
        // Mouse and keyboard listener
        MouseListener mouselistener = new MouseListener(this);
        KeyboardListener keyboardListener = new KeyboardListener(this);
        addMouseListener(mouselistener);
        addKeyListener(keyboardListener);
        
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Paint the vertical and horizontal lines
        g2d.setStroke(new BasicStroke(1));

        // Horizontal
        for (int i = 0; i < this.getWidth(); i += 10) {
            if (i % 50 == 0) g2d.setPaint(new Color(200, 200, 200, 160));
            else g2d.setPaint(new Color(200, 200, 200, 80));
            g2d.drawLine(i, 0, i, this.getHeight());
        }

        // Vertical
        for (int i = 0; i < this.getHeight(); i += 10) {
            if (i % 50 == 0) g2d.setPaint(new Color(200, 200, 200, 160));
            else g2d.setPaint(new Color(200, 200, 200, 80));
            g2d.drawLine(0, i, this.getWidth(), i);
        }
    }

    @Override
    public void repaint() {
        
        if (parentFrame != null && parentFrame.hasFocus()) super.repaint();
        else return;

    }
}
