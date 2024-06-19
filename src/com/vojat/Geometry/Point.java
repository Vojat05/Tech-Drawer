package com.vojat.Geometry;

public class Point extends Geometry {
    private int x;
    private int y;
    private boolean selected = false;

    public Point(int coordx, int coordy) {

        this.x = coordx;
        this.y = coordy;

    }

    public int getX() { return this.x; }

    public int getY() { return this.y; }

    public boolean isSelected() { return this.selected; }

    public boolean select(boolean val) { return this.selected = val; }

    public int distance(Point point) { return (int) Math.sqrt((point.x - this.x) * (point.x - this.x) + (point.y - this.y) * (point.y - this.y)); }

    public boolean equals(Point point) {

        if (getX() == point.getX() && getY() == point.getY()) return true;
        else return false;

    }
}
