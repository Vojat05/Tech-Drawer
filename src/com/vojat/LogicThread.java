package com.vojat;

import java.util.ArrayList;

import com.vojat.Geometry.Point;
import com.vojat.Geometry.Circle;
import com.vojat.Geometry.Geometry;
import com.vojat.Geometry.Line;
import com.vojat.Panels.BluePrint;

public class LogicThread extends Thread {
    public LogicThread() {}

    @Override
    public void run() {
        BluePrint bp = Main.bluePrint.get(Main.activeBluePrint);
        ArrayList<Line> lines = new ArrayList<Line>();
        ArrayList<Circle> circles = new ArrayList<Circle>();
        ArrayList<Geometry> newGeom = new ArrayList<Geometry>();

        // Sort the geometry into lines and circles
        for (int i = 0; i < bp.geometrySize(); i++) {
            Geometry object = bp.getGeometryAt(i);

            if (object instanceof Line) {
                
                lines.add((Line) object);
                newGeom.add(object);

            } else circles.add((Circle) object);
        }

        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);

            // Defined line values
            double[] lineD = new double[2];
            double[] lineH = new double[2];
            lineD[0] = line.getStart().getX() > line.getEnd().getX() ? line.getEnd().getX() : line.getStart().getX();
            lineD[1] = line.getStart().getX() > line.getEnd().getX() ? line.getStart().getX() : line.getEnd().getX();
            lineH[0] = line.getStart().getY() < line.getEnd().getY() ? line.getStart().getY() : line.getEnd().getY();
            lineH[1] = line.getStart().getY() > line.getEnd().getY() ? line.getStart().getY() : line.getEnd().getY();
            
            for (int j = 0; j < circles.size(); j++) {
                Circle circle = circles.get(j);
                
                // Defined circle values
                double[] circleD = new double[2];
                double[] circleH = new double[2];
                circleD[0] = circle.getCenter().getX() - circle.getRadius();
                circleD[1] = circle.getCenter().getX() + circle.getRadius();
                circleH[0] = circle.getCenter().getY() - circle.getRadius();
                circleH[1] = circle.getCenter().getY() + circle.getRadius();

                // Is line in circlce
                

                Point[] intersectionPoints = circle.lineOnCircle(line);
                
                if (intersectionPoints.length == 2) {

                    double[] angles = new double[2];
                    for (int k = 0; k < 2; k++) {
                        double dy = Math.abs(intersectionPoints[k].getY() - circle.getCenter().getY());
                        double dx = Math.abs(intersectionPoints[k].getX() - circle.getCenter().getX());

                        angles[k] = Math.atan(dy / dx);
                        System.out.println("Angle " + k + ": " + angles[k]);
                    }

                    newGeom.add(new Circle(circle.getCenter(), circle.getRadius(), (short) angles[0], (short) angles[1], circle.getThickness()));
                    newGeom.add(new Circle(circle.getCenter(), circle.getRadius(), (short) angles[1], (short) angles[0], circle.getThickness()));
                } else {

                    newGeom.add(circle);
                    continue;

                }
            }
        }

        if (newGeom.size() > Main.bluePrint.get(Main.activeBluePrint).geometrySize()) {

            Main.bluePrint.get(Main.activeBluePrint).clearGeometry();
            Main.bluePrint.get(Main.activeBluePrint).setGeometry(newGeom);

        }
        System.out.println("Thread finished executing");
    }
}
