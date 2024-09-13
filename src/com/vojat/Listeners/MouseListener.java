package com.vojat.Listeners;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.vojat.Frame;
import com.vojat.Main;
import com.vojat.Geometry.Circle;
import com.vojat.Geometry.Geometry;
import com.vojat.Geometry.Line;
import com.vojat.Geometry.Point;
import com.vojat.Panels.BluePrint;
import com.vojat.Panels.ButtonPanel;
import com.vojat.Panels.LineSettingsPanel;
import com.vojat.Panels.SettingsPanel;

public class MouseListener implements MouseInputListener {

    public boolean selectionIsContain;
    private JPanel parent;
    public ArrayList<Point> points = new ArrayList<Point>();
    private boolean mouseWheelDown = false;

    public MouseListener(JPanel bp) {

        this.parent = bp;

    }

    public void stopDrawing() { points.clear(); }

    public boolean isDrawing() { return points.size() > 0 ? true : false; }

    public Point getLastPoint() {

        if (points.size() == 0) return null;
        return points.get(0);

    }

    public Point getPoint(int i) { return points.get(i); }

    public int pointsSize() { return points.size(); }

    public static int snapX(int x) {

        int sum = 0;
        while (x > 10) { x -= 10; sum += 10; }
        return sum + (x > 5 ? 10 : 0);

    }

    public static int snapY(int y) {

        int sum = 0;
        while (y > 10) { y -= 10; sum += 10; }
        return sum + (y > 5 ? 10 : 0);
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {

        // It's a ButtonPanel
        ButtonPanel btnp = null;
        if (parent instanceof ButtonPanel) {
            btnp = (ButtonPanel) parent;

            if (me.getX() >= 30 && me.getX() <= 64 && me.getY() >= 40 && me.getY() <= 74) {
                
                // PTP line tool
                ButtonPanel.setSelected((byte) 1);
                btnp.repaint();
                return;
    
            } else if (me.getX() >= btnp.getWidth() - 40 && me.getX() <= btnp.getWidth() - 10 && me.getY() >= 10 && me.getY() <= 70) {
                
                // Close button
                System.exit(0);
                System.out.println("Shutting down!");

            } else if (me.getX() >= 308 && me.getX() <= 346 && me.getY() >= 36 && me.getY() <= 74) {

                // Center & radius circle tool
                ButtonPanel.setSelected((byte) 2);
                btnp.repaint();
                return;

            } else if (me.getX() >= btnp.getWidth() - 120 && me.getX() >= btnp.getWidth() - 90 && me.getY() >= 10 && me.getY() <= 70) {

                // Open settings
                if (SettingsPanel.isOpen) return;
                Frame settingsWindow = new Frame(800, 600, "Settings", false, Main.backgroundColor);
                settingsWindow.addContentPanel(new SettingsPanel(settingsWindow.width, settingsWindow.height, settingsWindow.backColor, settingsWindow));
                SettingsPanel.isOpen = true;
            } else if (me.getX() >= 220 && me .getY() <= 265 && me.getY() >= 30 && me.getY() <= 75) {

                // Open line settings
                if (SettingsPanel.isOpen) return;
                Frame lineSettings = new Frame(500, 300, "Line Settings", false, Main.backgroundColor);
                lineSettings.addContentPanel(new LineSettingsPanel(lineSettings.width, lineSettings.height, lineSettings.backColor, lineSettings));
            }
        }

        // Here the parent can only be a BluePrint
        BluePrint bp;
        if (parent instanceof BluePrint) bp = (BluePrint) parent;
        else return;

        switch (me.getButton()) {
            case 1:
                if (ButtonPanel.getSelected() == 0) {
                    // Selecting tool is selected
                    points.clear();
                    points.add(new Point(me.getX(), me.getY()));

                    for (int i = 0; i < bp.geometrySize(); i++) {

                        Geometry object = bp.getGeometryAt(i);
                        
                        // Is a line
                        if (object instanceof Line) {
                            
                            Line line = (Line) object;
                            if (line.isOnLine(new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY()))) line.select(true);

                        }

                        // Is a circle
                        if (object instanceof Circle) {

                            Circle circle = (Circle) object;
                            if (circle.isOnCircle(new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY()))) circle.select(true);

                        }
                    }
                } else if (ButtonPanel.getSelected() == 1) {
                    // The PTP line is selected
                    
                    if (points.size() == 1) {

                        Point end = new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY());
                        bp.addLine(new Line(points.get(0), end, LineSettingsPanel.thickness));
                        points.clear();
                        points.add(end);

                    }else points.add(new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY()));
                } else if (ButtonPanel.getSelected() == 2) {
                    // R circle is selected

                    if (points.size() == 1) {

                        Point end = new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY());
                        bp.addCircle(new Circle(points.get(0), points.get(0).distance(end), (short) 0, (short) 360, LineSettingsPanel.thickness));
                        points.clear();

                    } else points.add(new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY()));
                }
                break;
            
            case 2:
                points.clear();
                points.add(new Point(me.getX(), me.getY()));
                mouseWheelDown = true;
                break;
            
            case 3:
                System.out.println("RMB");
                break;
            
            default:
                System.out.println("BTN 4+");
                return;
        }
        bp.repaint();
        System.out.println("Mouse pressed, location: " + me.getX() + ", " + me.getY());
    }

    @Override
    public void mouseReleased(MouseEvent me) {

        // Return if parent panel isn't BluePrint
        if (!(parent instanceof BluePrint)) return;
        if (ButtonPanel.getSelected() == 0) points.add(new Point(me.getX(), me.getY()));
        else return;

        // Here the points arraylist always has 2 points
        BluePrint bp = (BluePrint) parent;

        if (me.getButton() == 2) {

            mouseWheelDown = false;
            bp.writeThroughOffsets();

        }

        // Change sheets
        if (me.getX() <= 100 * Main.bluePrint.size() & me.getY() >= bp.getHeight() - 30) {
            Main.changeSheet((int) (me.getX() * .01));
            points.clear();
            return;
        }

        // Create a new sheet
        if (me.getX() > 100 * Main.bluePrint.size() + 3 && me.getX() < 100 * Main.bluePrint.size() + 33 && me.getY() >= bp.getHeight() - 30) {
            Main.bluePrint.add(new BluePrint(10, 100, Main.screenSize[0] - 20, Main.screenSize[1] - 110, BluePrint.backColor));
            Main.changeSheet(Main.bluePrint.size() - 1);
            points.clear();
            return;
        }
        
        // Check if some geometry is to be selected
        for (int i = 0; i < bp.geometrySize(); i++) {

            if (points.size() < 2) break;
            Geometry object = bp.getGeometryAt(i);
            int[] offsets = bp.getOffsetsXY();

            // Is a line
            if (object instanceof Line) {

                Line line = (Line) object;
                Point left = points.get(0).getX() < points.get(1).getX() ? points.get(0) : points.get(1);
                Point right = points.get(0).getX() > points.get(1).getX() ? points.get(0) : points.get(1);
                Point start = line.getStart();
                Point end = line.getEnd();
                
                if (selectionIsContain) {
    
                    if (start.getX() >= left.getX() + offsets[0] && start.getX() <= right.getX() + offsets[0] && start.getY() >= points.get(0).getY() + offsets[1] && start.getY() <= points.get(1).getY() + offsets[1] && end.getX() >= left.getX() + offsets[0] && end.getX() <= right.getX() + offsets[0] && end.getY() >= points.get(0).getY() + offsets[1] && end.getY() <= points.get(1).getY() + offsets[1]) line.select(true);
                    else line.select(false);
                
                } else {
    
                    if (start.getX() < left.getX() + offsets[0] && end.getX() < left.getX() + offsets[0] || start.getX() > right.getX() + offsets[0] && end.getX() > right.getX() + offsets[0] || start.getY() < points.get(1).getY() + offsets[1] && end.getY() < points.get(1).getY() + offsets[1] || start.getY() > points.get(0).getY() + offsets[1] && end.getY() > points.get(0).getY() + offsets[1]) line.select(false);
                    else line.select(true);
    
                }
            } else if (object instanceof Circle) {
                Circle circle = (Circle) object;
                Point center = circle.getCenter();
                double radius = circle.getRadius();
                Point leftTop;
                Point rightBot;
                double x, y;

                // Left top
                if (points.get(0).getX() < points.get(1).getX()) x = points.get(0).getX();
                else x = points.get(1).getX();

                if (points.get(0).getY() < points.get(1).getY()) y = points.get(0).getY();
                else y = points.get(1).getY();

                leftTop = new Point(x, y);

                // Right bottom
                if (points.get(0).getX() > points.get(1).getX()) x = points.get(0).getX();
                else x = points.get(1).getX();

                if (points.get(0).getY() > points.get(1).getY()) y = points.get(0).getY();
                else y = points.get(1).getY();

                rightBot = new Point(x, y);

                if (selectionIsContain) {

                    if (center.getX() - radius >= leftTop.getX() + offsets[0] && center.getX() + radius <= rightBot.getX() + offsets[0] && center.getY() - radius >= leftTop.getY() + offsets[1] && center.getY() + radius <= rightBot.getY() + offsets[1]) circle.select(true);
                    else circle.select(false);

                } else {

                    if (rightBot.getX() + offsets[0] < center.getX() - radius || leftTop.getX() + offsets[0] > center.getX() || rightBot.getY() + offsets[1] < center.getY() - radius || leftTop.getY() + offsets[1] > center.getY() + radius) circle.select(false);
                    else circle.select(true);

                }
            }
        }
        bp.repaint();
        points.clear();
    }

    @Override
    public void mouseMoved(MouseEvent me) {

        // Is it a BluePrint ??
        if (parent instanceof BluePrint) {

            BluePrint bp = (BluePrint) parent;
            if (Main.snaptogrid) bp.setMousePos(snapX(me.getX()), snapY(me.getY()));
            else bp.setMousePos(me.getX(), me.getY());

            if (points.size() > 0 && mouseWheelDown) {

                bp.offsetX = (int) ((Main.snaptogrid ? snapX(me.getX()) : me.getX()) - points.get(0).getX());
                bp.offsetY = (int) ((Main.snaptogrid ? snapY(me.getY()) : me.getY()) - points.get(0).getY());
            
            }

            // Check if the selection is pass-through or contain
            if (ButtonPanel.getSelected() == 0 && points.size() != 0) {

                if (points.get(0).getY() < me.getY()) selectionIsContain = true;
                else selectionIsContain = false;
            
            }
            bp.repaint();
            return;

        }
        // It's a ButtonPanel
    }

    @Override
    public void mouseDragged(MouseEvent me) { mouseMoved(me); }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
}
