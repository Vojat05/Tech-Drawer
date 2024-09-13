package com.vojat.Geometry;

public class Line extends Geometry {
    private Point start;
    private Point end;
    private boolean isSelected = false;
    private byte thickness;

    public Line(Point start, Point end, byte thickness) {

        this.start = start;
        this.end = end;
        this.thickness = thickness;

    }

    public Line(double sx, double sy, double ex, double ey, byte thickness) {

        this.start = new Point(sx, sy);
        this.end = new Point(ex, ey);
        this.thickness = thickness;
        
    }

    public Point setStart(Point point) { return this.start = point; }

    public Point setEnd(Point point) { return this.end = point; }

    public Point getStart() { return this.start; }

    public Point getEnd() { return this.end; }

    public boolean select(boolean val) { return this.isSelected = val; }

    public boolean isSelected() { return this.isSelected; }

    public byte getThickness() { return this.thickness; }

    public byte setThickness(byte thickness) { return this.thickness = thickness; }

    public boolean isOnLine(Point point) {

        double x = point.getX();
        double y = point.getY();

        // If it's outside of the line either up, down, left or right
        double leftMost = start.getX() < end.getX() ? start.getX() : end.getX();
        double rightMost = leftMost == start.getX() ? end.getX() : start.getX();
        double upper = start.getY() < end.getY() ? start.getY() : end.getY();
        double lower = upper == start.getY() ? end.getY() : start.getY();

        if (x < leftMost || x > rightMost || y < upper || y > lower) return false;

        // The formula to calculate if the point is on the line
        if (end.getY() * x - start.getY() * x + start.getX() * y - end.getX() * y - end.getY() * start.getX() + start.getY() * end.getX() == 0) return true;
        else return false;
    }
}
