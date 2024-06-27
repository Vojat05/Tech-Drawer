package com.vojat.DataStructures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Directory {
    public String name;
    public int subDirOffset = 0;
    public ArrayList<File> content = new ArrayList<>();


    public Directory(String name, int subDirOffset) {

        this.name = name;
        this.subDirOffset = subDirOffset;

    }

    public void addContent(File file) { content.add(file); }
    
    public static void draw(int x, int y, Directory dir, Graphics2D g2d) {
        
        x += dir.subDirOffset * 10;
        int[] pointsX = {0 + x, 0 + x, 28 + x, 28 + x, 12 + x, 8 + x};
        int[] pointsY = {0 + y, 20 + y, 20 + y, 4 + y, 4 + y, 0 + y};
        
        g2d.setPaint(Color.YELLOW);
        g2d.fillPolygon(pointsX, pointsY, pointsX.length);
        g2d.setPaint(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(18f));
        g2d.drawString(dir.name, x + 40, y + 18);

        // Has conent
        if (dir.content.size() != 0) {

            int[] subLineX = {4 + x, 4 + x, 24 + x};
            int[] subLineY = {24 + y, 44 + y, 44 + y};
            g2d.setPaint(new Color(140, 140, 140));
            g2d.drawPolyline(subLineX, subLineY, subLineX.length);

        }
        for (int i = 0; i < dir.content.size(); i++) File.draw(x + 40, y + 30 + 35 * i, dir.content.get(i).name, g2d);
    }
}
