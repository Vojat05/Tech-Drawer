package com.vojat.Panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.vojat.Listeners.KeyboardListener;
import com.vojat.Listeners.MouseListener;

public class BluePrint extends JPanel {

    private int[] mousePos = new int[2];

    public BluePrint(int x, int y, int width, int height, Color color) {
        setSize(width, height);
        setLocation(x, y);
        setBackground(color);
        setFocusable(true);

        mousePos[0] = 0;
        mousePos[1] = 0;
        
        setVisible(true);
    }

    public void setListeners(KeyboardListener kbl, MouseListener ml) {

        // Mouse and keyboard listener
        addKeyListener(kbl);
        addMouseListener(ml);
        addMouseMotionListener(ml);
        
    }

    public int[] setMousePos(int x, int y) {

        mousePos[0] = x;
        mousePos[1] = y;
        return mousePos;

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
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

        // Mouse position box and values
        int recalcX = (int) (this.getWidth() - this.getWidth() * .12);
        int recalcY = this.getHeight() - 40;
        int[] x = {recalcX + 25, recalcX + 30, recalcX + 38, recalcX + 41, recalcX + 60, recalcX + 60};
        int[] y = {recalcY + 40, recalcY + 38, recalcY + 30, recalcY + 20, recalcY + 20, recalcY + 40};

        g2d.setPaint(new Color(50, 50, 55));
        g2d.setStroke(new BasicStroke(1));
        g2d.drawArc(recalcX, recalcY, 40, 40, 270, 90);
        g2d.fillArc(recalcX + 40, recalcY, 40, 40, 180, -90);
        g2d.fillRect(recalcX + 60, recalcY, this.getWidth(), this.getHeight());
        g2d.fillPolygon(x, y, x.length);

        g2d.setPaint(Color.WHITE);
        g2d.setFont(getFont().deriveFont(24f));
        g2d.drawString("X: " + mousePos[0], recalcX + 60, recalcY + 30);
        g2d.drawString("Y: " + mousePos[1], recalcX + 180, recalcY + 30);
    }

    @Override
    public void repaint() {
        
        if (hasFocus()) super.repaint();
        else return;

    }
}
