package com.vojat.Panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.vojat.Main;
import com.vojat.Geometry.Circle;
import com.vojat.Geometry.Geometry;
import com.vojat.Geometry.Line;
import com.vojat.Geometry.Point;
import com.vojat.Listeners.KeyboardListener;
import com.vojat.Listeners.MouseListener;

public class BluePrint extends JPanel {

    public static Color backColor = new Color(0, 91, 140);
    public boolean detailLines = true;
    public MouseListener mouseListener;
    public KeyboardListener keyboardListener;
    public int offsetX = 0, offsetY = 0;
    private int savedOffsetX = 0, savedOffsetY = 0;
    private int[] mousePos = new int[2];
    private ArrayList<Geometry> geometry = new ArrayList<Geometry>();

    public BluePrint(int x, int y, int width, int height, Color color) {
        setSize(width, height);
        setLocation(x, y);
        setBackground(color);
        setFocusable(true);

        setListeners(new MouseListener(this));
        setCursor(Main.blankCursor);

        mousePos[0] = 0;
        mousePos[1] = 0;
        
        setVisible(true);
    }

    public void setListeners(MouseListener ml) {

        // Mouse listener
        addMouseListener(ml);
        addMouseMotionListener(ml);

        this.mouseListener = ml;
    }

    public void setListeners(KeyboardListener kbl) {

        // Keyboard listener
        addKeyListener(kbl);

        this.keyboardListener = kbl;
    }

    public void writeThroughOffsets() {

        savedOffsetX += offsetX;
        savedOffsetY += offsetY;
        offsetX = 0;
        offsetY = 0;
        
    }

    public int[] getOffsetsXY() {

        int[] offsets = new int[2];
        offsets[0] = savedOffsetX;
        offsets[1] = savedOffsetY;
        return offsets;
        
    }

    public Geometry[] getGeometryCopy() {

        Geometry[] geometryCopy = new Geometry[geometry.size()];
        for (int i = 0; i < geometry.size(); i++) geometryCopy[i] = geometry.get(i);
        return geometryCopy;
        
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

    public void clearGeometry() { geometry.clear(); }

    public void setGeometry(ArrayList<Geometry> geom) { geometry = geom;}

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
            else if (!detailLines) continue;
            else g2d.setPaint(new Color(200, 200, 200, 80));
            g2d.drawLine(i, 0, i, this.getHeight());
        }
        
        // Vertical
        for (int i = 0; i < this.getHeight(); i += 10) {
            if (i % 50 == 0) g2d.setPaint(new Color(200, 200, 200, 160));
            else if (!detailLines) continue;
            else g2d.setPaint(new Color(200, 200, 200, 80));
            g2d.drawLine(0, i, this.getWidth(), i);
        }

        // Draw the lines
        g2d.setPaint(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));

        for (int i = 0; i < geometry.size(); i++) {

            Geometry object = geometry.get(i);
            
            // Object is line
            if (object instanceof Line) {
                
                Line line = (Line) object;
                if (line.isSelected()) {

                    g2d.setStroke(new BasicStroke(line.getThickness() + 2));
                    g2d.setPaint(new Color(255, 255, 255, 100));
                    g2d.drawLine((int) (line.getStart().getX() + offsetX + savedOffsetX), (int) (line.getStart().getY() + offsetY + savedOffsetY), (int) (line.getEnd().getX() + offsetX + savedOffsetX), (int) (line.getEnd().getY() + offsetY + savedOffsetY));

                }

                g2d.setPaint(line.isSelected() ? new Color(225, 255, 138) : Color.WHITE);    
                g2d.setStroke(new BasicStroke(line.getThickness()));
                g2d.drawLine((int) (line.getStart().getX() + offsetX + savedOffsetX), (int) (line.getStart().getY() + offsetY + savedOffsetY), (int) (line.getEnd().getX() + offsetX + savedOffsetX), (int) (line.getEnd().getY() + offsetY + savedOffsetY));
            }

            // Object is a circle
            if (object instanceof Circle) {

                Circle circle = (Circle) object;
                if (circle.isSelected()) {

                    g2d.setStroke(new BasicStroke(circle.getThickness() + 2));
                    g2d.setPaint(new Color(255, 255, 255, 100));
                    g2d.drawArc((int) (circle.getCenter().getX() - circle.getRadius() + offsetX + savedOffsetX), (int) (circle.getCenter().getY() - circle.getRadius() + offsetY + savedOffsetY), (int) (circle.getRadius() * 2), (int) (circle.getRadius() * 2), circle.getStartAngle(), circle.getStartAngle() - circle.getEndAngle());
                
                }
    
                g2d.setPaint(circle.isSelected() ? new Color(225, 255, 138) : Color.WHITE);
                g2d.setStroke(new BasicStroke(circle.getThickness()));
                g2d.drawArc((int) (circle.getCenter().getX() - circle.getRadius() + offsetX + savedOffsetX), (int) (circle.getCenter().getY() - circle.getRadius() + offsetY + savedOffsetY), (int) (circle.getRadius() * 2), (int) (circle.getRadius() * 2), circle.getStartAngle(), circle.getStartAngle() - circle.getEndAngle());
            }
        }

        // Draw the line from the last line point to the current mouse position if the line brush is selected
        if (ButtonPanel.getSelected() == 0 && mouseListener.pointsSize() == 1) {

            int mouseX = Main.snaptogrid ? MouseListener.snapX((int) mouseListener.getLastPoint().getX()) : (int) mouseListener.getLastPoint().getX();
            int mouseY = Main.snaptogrid ? MouseListener.snapY((int) mouseListener.getLastPoint().getY()) : (int) mouseListener.getLastPoint().getY();
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

            // Draw a line from the start point to the cursor
            if (mouseListener.getLastPoint() != null) {

                Point point = mouseListener.getLastPoint();
                g2d.setStroke(new BasicStroke(LineSettingsPanel.thickness));
                g2d.drawLine((int) point.getX(), (int) point.getY(), mousePos[0], mousePos[1]);

            }
        } else if (ButtonPanel.getSelected() == 2) {

            // Draw a circle from the center point to the cursor
            if (mouseListener.getLastPoint() != null) {

                Point point = mouseListener.getLastPoint();
                double radius = point.distance(new Point(mousePos[0], mousePos[1]));
                g2d.setStroke(new BasicStroke(LineSettingsPanel.thickness));
                g2d.drawArc((int) (point.getX() - radius), (int) (point.getY() - radius), (int) (radius * 2), (int) (radius * 2), 0, 360);

            }
        }

        // Another blueprint sheets
        g2d.setPaint(new Color(50, 50, 55));
        for (int i = 0; i < Main.bluePrint.size(); i++) {

            g2d.setPaint(new Color(50, 50, 55));
            g2d.fillRect(100 * i, this.getHeight() - 30, 100, 30);
            g2d.setFont(g2d.getFont().deriveFont(17f));
            g2d.setPaint(Color.WHITE);
            g2d.drawString("Sheet " + i, 100 * i + 15, this.getHeight() - 8);
            
            // Color decoration
            if (Main.activeBluePrint == i) {

                g2d.setPaint(new Color(45, 160, 55));
                g2d.drawLine(100 * i, this.getHeight() - 30, 100 * i, this.getHeight());
                g2d.drawLine(100 * (i + 1), this.getHeight() - 30, 100 * (i + 1), this.getHeight());
                g2d.fillRect(100 * i, this.getHeight() - 35, 100, 5);
            
            } else {
                
                g2d.setPaint(new Color(145, 145, 145));
                g2d.drawLine(100 * (i + 1), this.getHeight() - 28, 100 * (i + 1), this.getHeight() - 2);

            }
        }

        // Draw the add button for adding another sheet
        g2d.setPaint(new Color(50, 50, 55));
        g2d.fillRect(100 * Main.bluePrint.size() + 3, this.getHeight() - 30, 30, 30);
        g2d.setPaint(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(100 * Main.bluePrint.size() + 2, this.getHeight() - 30, 100 * Main.bluePrint.size() + 2, this.getHeight());
        g2d.setPaint(new Color(200, 200, 200));
        g2d.drawLine(100 * Main.bluePrint.size() + 10, this.getHeight() - 15, 100 * Main.bluePrint.size() + 26, this.getHeight() - 15);
        g2d.drawLine(100 * Main.bluePrint.size() + 18, this.getHeight() - 23, 100 * Main.bluePrint.size() + 18, this.getHeight() - 7);

        // Draw the donate panel
        if (Main.donatePanel) {
            g2d.setPaint(new Color(27, 27, 27));
            g2d.fillRoundRect((int) (this.getWidth() * .5) - 300, 10, 600, 400, 10, 10);
            g2d.setPaint(Color.WHITE);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.drawString("Press ESC to close", (int) (this.getWidth() * .5) - 75, 400);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            try {
                g2d.drawImage(ImageIO.read(new File("../../Resources/Pictures/bmc-button-smol.png")), (int) (this.getWidth() * .5) - 250, 50, null);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            // Reset the cursor
            if (mousePos[0] >= this.getWidth() * .5 - 300 && mousePos[0] <= this.getWidth() * .5 + 300 && mousePos[1] >= 10 && mousePos[1] <= 410) {
                this.setCursor(Main.defaultCursor);

                // Buy me a coffee hover
                if (mousePos[0] >= this.getWidth() * .5 - 250 && mousePos[0] <= this.getWidth() * .5 - 10 && mousePos[1] >= 50 && mousePos[1] <= 120) {
                    g2d.setPaint(new Color(255, 221, 0));
                    g2d.drawRoundRect((int) (this.getWidth() * .5) - 255, 45, 250, 77, 25, 25);
                }
            }
            else {

                this.setCursor(Main.blankCursor);
                drawCursor(g2d);

            }
        } else drawCursor(g2d);
        
        

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
        g2d.drawString("X: " + mousePos[0] + (mousePos[0] < 1000 ? "  " : " ") + "Y: " + mousePos[1], recalcX + 60, recalcY + 30);

    }

    public void repaint() {
        super.repaint();
        this.setBackground(backColor);
    }
}
