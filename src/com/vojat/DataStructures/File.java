package com.vojat.DataStructures;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class File {
    public String name;
    public String type;

    public File(String name, String type) {

        this.name = name;
        this.type = type;

    }

    public static void draw(int x, int y, String name, Graphics2D g2d) {
        
        g2d.setPaint(new Color(140, 140, 140));
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(x, y, 20, 30, 1, 1);

        g2d.setPaint(Color.WHITE);
        for (int i = 0; i < 4; i++) g2d.drawLine(x + 4, y + 4 + 4 * i, x + 16, y + 4 + 4 * i);

        g2d.setFont(g2d.getFont().deriveFont(18f));
        g2d.drawString(name, x + 26, y + 22);
        
    }
}
