package com.vojat.Geometry;

import java.awt.Image;

public class Picture {
    private Image pic;
    private int x;
    private int y;
    private int width;
    private int height;

    public Picture(Image img, int x, int y, int width, int height) {

        this.pic = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public int setX(int x) { return this.x = x; }

    public int getX() { return this.x; }

    public int setY(int y) { return this.y = y; }

    public int getY() { return this.y; }

    public int setWidth(int width) { return this.width = width; }

    public int getWidth() { return this.width; }

    public int setHeight(int height) { return this.height = height; }

    public int getHeight() { return this.height; }

    public Image setPicture(Image img) { return this.pic = img; }

    public Image getPicture() { return this.pic; }
}
