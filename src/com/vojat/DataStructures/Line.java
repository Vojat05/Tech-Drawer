package com.vojat.DataStructures;

public class Line extends Geometry {
    private Point start;
    private Point end;
    private boolean isSelected = false;

    public Line(Point start, Point end) {

        this.start = start;
        this.end = end;

    }

    public Point setStart(Point point) { return this.start = point; }

    public Point setEnd(Point point) { return this.end = point; }

    public Point getStart() { return this.start; }

    public Point getEnd() { return this.end; }

    public boolean select(boolean val) { return this.isSelected = val; }

    public boolean isSelected() { return this.isSelected; }

    public boolean isOnLine(Point point) {

        int x = point.getX();
        int y = point.getY();

        // The formula to calculate if the point is on the line
        if (end.getY() * x - start.getY() * x + start.getX() * y - end.getX() * y - end.getY() * start.getX() + start.getY() * end.getX() == 0) return true;
        else return false;
    }
}
