package com.vojat.Panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.vojat.DataStructures.Point;
import com.vojat.Main;
import com.vojat.DataStructures.Circle;
import com.vojat.DataStructures.Geometry;
import com.vojat.DataStructures.Line;
import com.vojat.Listeners.KeyboardListener;
import com.vojat.Listeners.MouseListener;

public class BluePrint extends JPanel {

    public MouseListener mouseListener;
    public KeyboardListener keyboardListener;
    private int[] mousePos = new int[2];
    private ArrayList<Geometry> geometry = new ArrayList<Geometry>();

    public BluePrint(int x, int y, int width, int height, Color color) {
        setSize(width, height);
        setLocation(x, y);
        setBackground(color);
        setFocusable(true);

        mousePos[0] = 0;
        mousePos[1] = 0;
        
        setVisible(true);
    }

    public void setListeners(MouseListener ml) {

        // Mouse and keyboard listener
        addMouseListener(ml);
        addMouseMotionListener(ml);

        this.mouseListener = ml;
        
    }

    public int[] setMousePos(int x, int y) {

        mousePos[0] = x;
        mousePos[1] = y;
        return mousePos;

    }

    public Point getMousePos() { return new Point(mousePos[0], mousePos[1]); }

    public Line addLine(Line line) {

        geometry.add(line);
        return line;

    }

    public Circle addCircle(Circle circle) {

        geometry.add(circle);
        return circle;

    }

    public int geometrySize() { return geometry.size(); }

    public Geometry getGeometryAt(int i) {

        // Out of bounds
        if (i >= geometry.size() || i < 0) return null;
        else return geometry.get(i);
    }

    public Geometry removeGeometryAt(int i) {

        // Out of bounds
        if (i >= geometry.size() || i < 0) return null;
        else {

            Geometry obj = geometry.get(i);
            geometry.remove(i);
            return obj;

        }
    }

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

        for (int i = 0; i < geometry.size(); i++) {

            Geometry object = geometry.get(i);
            
            // Object is line
            if (object instanceof Line) {
                
                Line line = (Line) object;
                if (line.isSelected()) {

                    g2d.setStroke(new BasicStroke(5));
                    g2d.setPaint(new Color(255, 255, 255, 100));
                    g2d.drawLine(line.getStart().getX() + line.offsetX, line.getStart().getY() + line.offsetY, line.getEnd().getX() + line.offsetX, line.getEnd().getY() + line.offsetY);

                }

                g2d.setPaint(line.isSelected() ? new Color(225, 255, 225) : Color.WHITE);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(line.getStart().getX() + line.offsetX, line.getStart().getY() + line.offsetY, line.getEnd().getX() + line.offsetX, line.getEnd().getY() + line.offsetY);
            }

            // Object is a circle
            if (object instanceof Circle) {

                Circle circle = (Circle) object;
                if (circle.isSelected()) {

                    g2d.setStroke(new BasicStroke(5));
                    g2d.setPaint(new Color(255, 255, 255, 100));
                    g2d.drawArc(circle.getCenter().getX() - circle.getRadius() + circle.offsetX, circle.getCenter().getY() - circle.getRadius() + circle.offsetX, circle.getRadius() * 2, circle.getRadius() * 2, circle.getStartAngle(), circle.getStartAngle() - circle.getEndAngle());
                }
    
                g2d.setPaint(circle.isSelected() ? new Color(225, 255, 138) : Color.WHITE);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawArc(circle.getCenter().getX() - circle.getRadius() + circle.offsetX, circle.getCenter().getY() - circle.getRadius() + circle.offsetY, circle.getRadius() * 2, circle.getRadius() * 2, circle.getStartAngle(), circle.getStartAngle() - circle.getEndAngle());
            }
        }

        // Draw the line from the last line point to the current mouse position if the line brush is selected
        if (ButtonPanel.getSelected() == 0 && mouseListener.pointsSize() == 1) {

            int mouseX = Main.snaptogrid ? MouseListener.snapX(mouseListener.getLastPoint().getX()) : mouseListener.getLastPoint().getX();
            int mouseY = Main.snaptogrid ? MouseListener.snapY(mouseListener.getLastPoint().getY()) : mouseListener.getLastPoint().getY();
            g2d.setStroke(new BasicStroke(1));
            
            if (mouseListener.selectionIsContain) {

                // Blue
                g2d.setPaint(new Color(69, 143, 255, 100));
                g2d.fillRect(mouseX < mousePos[0] ? mouseX : mousePos[0], mouseY, (mouseX - mousePos[0]) * (mouseX < mousePos[0] ? -1 : 1), (mouseY - mousePos[1]) * -1);
                
            } else {
                
                // Green
                g2d.setPaint(new Color(97, 255, 69, 100));
                g2d.fillRect(mouseX < mousePos[0] ? mouseX : mousePos[0], mousePos[1], (mouseX - mousePos[0]) * (mouseX < mousePos[0] ? -1 : 1), (mouseY - mousePos[1]));

            }
        }
        else if (ButtonPanel.getSelected() == 1) {

            // If 0 lines have been drawn
            if (mouseListener.getLastPoint() == null) {

                drawCursor(g2d);
                return;

            }
            Point point = mouseListener.getLastPoint();
            g2d.drawLine(point.getX(), point.getY(), mousePos[0], mousePos[1]);
            
        } else if (ButtonPanel.getSelected() == 2) {

            if (mouseListener.getLastPoint() == null) {

                drawCursor(g2d);
                return;

            }
            Point point = mouseListener.getLastPoint();
            int radius = point.distance(new Point(mousePos[0], mousePos[1]));
            g2d.drawArc(point.getX() - radius, point.getY() - radius, radius * 2, radius * 2, 0, 360);
        }
        drawCursor(g2d);
    }
}
