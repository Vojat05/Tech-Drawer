package com.vojat.Geometry;

public class Circle extends Geometry{
    private short startAngle;
    private short endAngle;
    private Point center;
    private double radius;
    private boolean selected = false;
    private byte thickness;

    public Circle(Point center, double radius, short startAngle, short endAngle, byte thickness) {

        this.center = center;
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.thickness = thickness;

    }

    public Circle(double cx, double cy, double radius, short startAngle, short endAngle, byte thickness) {

        this.center = new Point(cx, cy);
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.thickness = thickness;
        
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

    public byte setThickness(byte thickness) { return this.thickness = thickness; }

    public byte getThickness() { return this.thickness; }

    public boolean isOnCircle(Point point) {

        double x = point.getX();
        double y = point.getY();

        if (Math.pow((x - center.getX()), 2) + Math.pow((y - center.getY()), 2) == Math.pow(radius, 2) && !center.equals(point)) return true;
        else return false;
        
    }

    public Point[] lineOnCircle(Line line) {
        Point[] points;
        
        // Calculate General equation of the line
        double A = line.getStart().getY() - line.getEnd().getY();
        double B = line.getEnd().getX() - line.getStart().getX();
        double C = line.getStart().getX() * line.getEnd().getY() - line.getEnd().getX() * line.getStart().getY();

        // Calculate helper values
        double a = A * A + B * B;
        double b = 2  * A * C + 2 * A * B * this.center.getY() - 2 * B * B * this.center.getX();
        double c = C * C + 2 * B * C * this.center.getY() - B * B * (this.radius * this.radius - this.center.getX() * this.center.getX() - this.center.getY() * this.center.getY());
        double D = b * b - 4 * a * c;

        // Figure out how many points are in the line circle intersection
        if (D > 0) {
            points = new Point[2];
            double x1 = (-b + Math.sqrt(D)) / (2 * a);
            double x2 = (-b - Math.sqrt(D)) / (2 * a);

            points[0] = new Point(x1, -(A * x1 + C) / B);
            points[1] = new Point(x2, -(A * x2 + C) / B);
        } else if (D == 0) {
            points = new Point[1];
            double x = -b / (2 * a);
            points[0] = new Point(x, -(A * x + C) / B);
        }
        else return new Point[0];
        return points;
    } 
}
