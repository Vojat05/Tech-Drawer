package com.vojat.Panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.vojat.DataStructures.Point;
import com.vojat.DataStructures.Circle;
import com.vojat.DataStructures.Line;
import com.vojat.Listeners.KeyboardListener;
import com.vojat.Listeners.MouseListener;

public class BluePrint extends JPanel {

    public MouseListener mouseListener;
    public KeyboardListener keyboardListener;
    private int[] mousePos = new int[2];
    private ArrayList<Line> lines = new ArrayList<Line>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();

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

        this.mouseListener = ml;
        this.keyboardListener = kbl;
        
    }

    public int[] setMousePos(int x, int y) {

        mousePos[0] = x;
        mousePos[1] = y;
        return mousePos;

    }

    public Point getMousePos() { return new Point(mousePos[0], mousePos[1]); }

    public Line addLine(Line line) {

        lines.add(line);
        return line;

    }

    public Line getLine(int i) { return lines.get(i); }

    public Circle addCircle(Circle circle) {

        circles.add(circle);
        return circle;

    }

    public Circle getCircle(int i) { return circles.get(i); }

    public void drawCursor(Graphics2D g2d) {

        // Draw the cursor lines
        g2d.setStroke(new BasicStroke(1));
        g2d.setPaint(new Color(16, 230, 42));
        g2d.drawLine(mousePos[0] - 40, mousePos[1], mousePos[0] + 40, mousePos[1]);
        g2d.drawLine(mousePos[0], mousePos[1] - 40, mousePos[0], mousePos[1] + 40);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Rendering hints smoothening
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        
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

        // Draw the lines
        g2d.setPaint(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));

        for (int i = 0; i < lines.size(); i++) {

            Line line = lines.get(i);
            g2d.drawLine(line.getStart().getX(), line.getStart().getY(), line.getEnd().getX(), line.getEnd().getY());
        
        }

        // Draw the circles
        for (int i = 0; i < circles.size(); i++) {

            Circle circle = circles.get(i);
            g2d.drawArc(circle.getCenter().getX(), circle.getCenter().getY(), circle.getRadius() * 2, circle.getRadius() * 2, circle.getStartAngle(), circle.getStartAngle() - circle.getEndAngle());
        }

        // Draw the line from the last line point to the current mouse position if the line brush is selected
        if (ButtonPanel.getSelected() != 0) {

            // If 0 lines have been drawn
            if (mouseListener.getLastPoint() == null) {

                drawCursor(g2d);
                return;

            }
            Point point = mouseListener.getLastPoint();
            g2d.drawLine(point.getX(), point.getY(), mousePos[0], mousePos[1]);
            
        }
        drawCursor(g2d);
    }

    @Override
    public void repaint() {
        
        if (hasFocus()) super.repaint();
        else return;

    }
}
