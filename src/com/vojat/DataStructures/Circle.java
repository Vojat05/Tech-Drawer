package com.vojat.DataStructures;

public class Circle extends Geometry{
    private short startAngle;
    private short endAngle;
    private Point center;
    private double radius;
    private boolean selected = false;

    public Circle(Point center, int radius, short startAngle, short endAngle) {

        this.center = center;
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;

    }

    public Circle(int cx, int cy, int radius, short startAngle, short endAngle) {

        this.center = new Point(cx, cy);
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        
    }

    public short setStartAngle(short angle) { return this.startAngle = angle; }

    public short getStartAngle() { return this.startAngle; }

    public short setEndAngle(short angle) { return this.endAngle = angle; }

    public short getEndAngle() { return this.endAngle; }

    public Point setCenter(Point point) { return this.center = point; }

    public Point getCenter() { return this.center; }

    public double setRadius(int r) { return this.radius = r; }

    public double getRadius() { return this.radius; }

    public boolean select(boolean val) { return this.selected = val; }

    public boolean isSelected() { return this.selected; }

    public boolean isOnCircle(Point point) {

        int x = point.getX();
        int y = point.getY();

        if (Math.pow((x - center.getX()), 2) + Math.pow((y - center.getY()), 2) == Math.pow(radius, 2) && !center.equals(point)) return true;
        else return false;
        
    }
}
