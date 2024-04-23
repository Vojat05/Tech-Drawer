package com.vojat.Listeners;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.vojat.Main;
import com.vojat.DataStructures.Circle;
import com.vojat.DataStructures.Line;
import com.vojat.DataStructures.Point;
import com.vojat.Panels.BluePrint;
import com.vojat.Panels.ButtonPanel;

public class MouseListener implements MouseInputListener {

    public boolean selectionIsContain;
    private JPanel parent;
    private ArrayList<Point> points = new ArrayList<Point>();

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
        if (parent instanceof ButtonPanel) btnp = (ButtonPanel) parent;
        if (btnp != null) {

            if (me.getX() >= 30 && me.getX() <= 64 && me.getY() >= 40 && me.getY() <= 74) {
    
                ButtonPanel.setSelected((byte) 1);
                btnp.repaint();
                return;
    
            } else if (me.getX() >= btnp.getWidth() - 40 && me.getX() <= btnp.getWidth() - 10 && me.getY() >= 10 && me.getY() <= 70) System.exit(0);
            else if (me.getX() >= 308 && me.getX() <= 346 && me.getY() >= 36 && me.getY() <= 74) {

                ButtonPanel.setSelected((byte) 2);
                btnp.repaint();
                return;

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

                    for (int i = 0; i < bp.getLinesSize(); i++) {
                        
                        Line line = bp.getLine(i);
                        if (line.isOnLine(new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY()))) line.select(true);

                    }

                    for (int i = 0; i < bp.getCirclesSize(); i++) {

                        Circle circle = bp.getCircle(i);
                        if (circle.isOnCircle(new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY()))) circle.select(true);

                    }
                } else if (ButtonPanel.getSelected() == 1) {
                    // The PTP line is selected
                    
                    if (points.size() == 1) {

                        Point end = new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY());
                        bp.addLine(new Line(points.get(0), end));
                        points.clear();
                        points.add(end);

                    }else points.add(new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY()));
                } else if (ButtonPanel.getSelected() == 2) {
                    // R circle is selected

                    if (points.size() == 1) {

                        Point end = new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY());
                        bp.addCircle(new Circle(points.get(0), points.get(0).distance(end), (short) 0, (short) 360));
                        points.clear();

                    } else points.add(new Point(Main.snaptogrid ? snapX(me.getX()) : me.getX(), Main.snaptogrid ? snapY(me.getY()) : me.getY()));
                }
                break;
            
            case 2:
                System.out.println("MMB");
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
        bp.repaint();
        
        // Check if some line is to be selected
        for (int i = 0; i < bp.getLinesSize(); i++) {
            
            Line line = bp.getLine(i);
            Point left = points.get(0).getX() < points.get(1).getX() ? points.get(0) : points.get(1);
            Point right = points.get(0).getX() > points.get(1).getX() ? points.get(0) : points.get(1);
            Point start = line.getStart();
            Point end = line.getEnd();
            
            if (selectionIsContain) {

                if (start.getX() > left.getX() && start.getX() < right.getX() && start.getY() > points.get(0).getY() && start.getY() < points.get(1).getY() && end.getX() > left.getX() && end.getX() < right.getX() && end.getY() > points.get(0).getY() && end.getY() < points.get(1).getY()) line.select(true);
                else line.select(false);
            
            } else {

                if (start.getX() < left.getX() && end.getX() < left.getX() || start.getX() > right.getX() && end.getX() > right.getX() || start.getY() < points.get(1).getY() && end.getY() < points.get(1).getY() || start.getY() > points.get(0).getY() && end.getY() > points.get(0).getY()) line.select(false);
                else line.select(true);
            }
        }
        points.clear();
    }

    @Override
    public void mouseMoved(MouseEvent me) {

        // Is it a BluePrint ??
        if (parent instanceof BluePrint) {

            BluePrint bp = (BluePrint) parent;
            if (Main.snaptogrid) bp.setMousePos(snapX(me.getX()), snapY(me.getY()));
            else bp.setMousePos(me.getX(), me.getY());

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
