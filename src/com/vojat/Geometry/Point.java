package com.vojat.Geometry;

public class Point extends Geometry {
    private double x;
    private double y;
    private boolean selected = false;

    public Point(double coordx, double coordy) {

        this.x = coordx;
        this.y = coordy;

    }

    public double getX() { return this.x; }

    public double getY() { return this.y; }

    public boolean isSelected() { return this.selected; }

    public boolean select(boolean val) { return this.selected = val; }

    public double distance(Point point) { return Math.sqrt((point.x - this.x) * (point.x - this.x) + (point.y - this.y) * (point.y - this.y)); }

    public boolean equals(Point point) {

        if (getX() == point.getX() && getY() == point.getY()) return true;
        else return false;

    }
}
