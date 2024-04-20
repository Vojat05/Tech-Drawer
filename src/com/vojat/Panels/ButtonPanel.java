package com.vojat.Panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.vojat.Main;
import com.vojat.Listeners.MouseListener;

public class ButtonPanel extends JPanel {

    private byte selected = -1;
    
    public ButtonPanel(int x, int y, int width, int height, Color color) {
        setSize(width, height);
        setLocation(x, y);
        setBackground(color);
    }

    public void setListeners(MouseListener ml) {

        addMouseListener(ml);

    }

    // Sets the selected option to a value
    public byte setSelected(byte val) { return this.selected = val; }

    public byte getSelected() { return this.selected; }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Drawing the border
        g2d.setPaint(new Color(255, 255, 255, 150));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(0, this.getHeight(), 0, 20);
        g2d.drawLine(this.getWidth() - 1, this.getHeight(), this.getWidth() - 1, 20);
        g2d.drawLine(20, 0, this.getWidth() - 20, 0);
        g2d.drawArc(0, 0, 30, 30, 180, -90);
        g2d.drawArc(this.getWidth() - 31, 0, 30, 30, 0, 90);
        g2d.drawLine(280, 10, 280, this.getHeight());

        // Drawing the section labels
        g2d.setFont(getFont().deriveFont(20f));
        g2d.drawString("Lines", 120, 20);

        //Drawing the buttons images
        for (int i = 0; i < Main.textures.size(); i++) {

            if (i == selected) {
                g2d.setStroke(new BasicStroke(1));
                g2d.setPaint(new Color(255, 255, 255, 200));
                g2d.drawRoundRect(25 + i * 30, 35, 42, 42, 10, 10);
            }
            g2d.drawImage(Main.textures.get("PTPline"), 30, 40, 34, 34, null);
        }
    }
}
