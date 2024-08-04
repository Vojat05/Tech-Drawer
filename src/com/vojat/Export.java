package com.vojat;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.vojat.Geometry.Circle;
import com.vojat.Geometry.Geometry;
import com.vojat.Geometry.Line;
import com.vojat.Panels.BluePrint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Export {
    
    public Export() {}

    public static void exportToPNG(File destinationFile, Geometry[] geometry) {
        
        BluePrint bp = Main.bluePrint.get(Main.activeBluePrint);

        // Return if no geometry exists
        if (bp.geometrySize() == 0) return;

        BufferedImage outputImg;
        int left = bp.getWidth(), right = 0, top = bp.getHeight(), bottom = 0;
        Geometry geom;

        // Get the image size
        for (int i = 0; i < geometry.length; i++) {

            geom = bp.getGeometryAt(i);
            if (geom instanceof Line) {

                Line line = (Line) geom;

                // Start point
                // X
                if (line.getStart().getX() < left) left = line.getStart().getX();
                if (line.getStart().getX() > right) right = line.getStart().getX();

                // Y
                if (line.getStart().getY() < top) top = line.getStart().getY();
                if (line.getStart().getY() > bottom) bottom = line.getStart().getY();

                // End point
                // X
                if (line.getEnd().getX() < left) left = line.getEnd().getX();
                if (line.getEnd().getX() > right) right = line.getEnd().getX();

                // Y
                if (line.getEnd().getY() < top) top = line.getEnd().getY();
                if (line.getEnd().getY() > bottom) bottom = line.getEnd().getY();

            } else if (geom instanceof Circle) {

                Circle circle = (Circle) geom;

                // X
                if (circle.getCenter().getX() - circle.getRadius() < left) left = (int) (circle.getCenter().getX() - circle.getRadius());
                if (circle.getCenter().getX() + circle.getRadius() > right) right = (int) (circle.getCenter().getX() + circle.getRadius());

                // Y
                if (circle.getCenter().getY() - circle.getRadius() < top) top = (int) (circle.getCenter().getY() - circle.getRadius());
                if (circle.getCenter().getY() + circle.getRadius() > bottom) bottom = (int) (circle.getCenter().getY() + circle.getRadius());

            }
        }

        // Creating the image itself
        outputImg = new BufferedImage(right - left + 10, bottom - top + 10, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = outputImg.createGraphics();

        // Paint the geometry onto the image
        g2d.setStroke(new BasicStroke(3));
        g2d.setPaint(Color.BLACK);

        for (int i = 0; i < bp.geometrySize(); i++) {

            geom = bp.getGeometryAt(i);

            if (geom instanceof Line) {

                Line line = (Line) geom;
                g2d.drawLine(line.getStart().getX() - left + 5, line.getStart().getY() - top + 5, line.getEnd().getX() - left + 5, line.getEnd().getY() - top + 5);

            } else if (geom instanceof Circle) {

                Circle circle = (Circle) geom;
                g2d.drawArc((int) (circle.getCenter().getX() - circle.getRadius()) - left + 5, (int) (circle.getCenter().getY() - circle.getRadius()) - top + 5, (int) (circle.getRadius() * 2), (int) (circle.getRadius() * 2), circle.getStartAngle(), circle.getEndAngle());

            }
        }

        try {
            ImageIO.write(outputImg, "png", destinationFile);
            System.out.println("Export was successfull !");
        } catch (IOException ioe) { ioe.printStackTrace(); }
    }
}
