package com.vojat.Listeners;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.vojat.DataStructures.Circle;
import com.vojat.DataStructures.Line;
import com.vojat.DataStructures.Point;
import com.vojat.Panels.BluePrint;
import com.vojat.Panels.ButtonPanel;

public class MouseListener implements MouseInputListener {

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

                    for (int i = 0; i < bp.getLinesSize(); i++) {
                        
                        Line line = bp.getLine(i);
                        if (line.isOnLine(new Point(me.getX(), me.getY()))) line.select();

                    }

                    for (int i = 0; i < bp.getCirclesSize(); i++) {

                        Circle circle = bp.getCircle(i);
                        if (circle.isOnCircle(new Point(me.getX(), me.getY()))) circle.isSelected();
                        
                    }
                } else if (ButtonPanel.getSelected() == 1) {
                    // The PTP line is selected
                    
                    if (points.size() == 1) {

                        Point end = new Point(me.getX(), me.getY());
                        bp.addLine(new Line(points.get(0), end));
                        points.clear();
                        points.add(end);

                    }else points.add(new Point(me.getX(), me.getY()));
                } else if (ButtonPanel.getSelected() == 2) {

                    if (points.size() == 1) {
                        // R circle is selected

                        Point end = new Point(me.getX(), me.getY());
                        bp.addCircle(new Circle(points.get(0), points.get(0).distance(end), (short) 0, (short) 360));
                        points.clear();

                    } else points.add(new Point(me.getX(), me.getY()));
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
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseMoved(MouseEvent me) {

        // Is it a BluePrint ??
        if (parent instanceof BluePrint) {

            BluePrint bp = (BluePrint) parent;
            bp.setMousePos(me.getX(), me.getY());
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
