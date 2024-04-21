package com.vojat.DataStructures;

public class Circle {
    private short startAngle;
    private short endAngle;
    private Point center;
    private int radius;
    private boolean selected = false;

    public Circle(Point center, int radius, short startAngle, short endAngle) {

        this.center = center;
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

    public int setRadius(int r) { return this.radius = r; }

    public int getRadius() { return this.radius; }

    public boolean select() { return this.selected = true; }

    public boolean isSelected() { return this.selected; }

    public boolean isOnCircle(Point point) {

        int x = point.getX();
        int y = point.getY();

        if ((x - center.getX()) * (x - center.getX()) + (y - center.getY()) * (y - center.getY()) == radius * radius) return true;
        else return false;
    }
}
