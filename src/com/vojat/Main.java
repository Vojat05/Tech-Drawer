package com.vojat;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.vojat.Geometry.Circle;
import com.vojat.Geometry.Geometry;
import com.vojat.Geometry.Line;
import com.vojat.Geometry.Picture;
import com.vojat.Listeners.KeyboardListener;
import com.vojat.Listeners.MouseListener;
import com.vojat.Panels.BluePrint;
import com.vojat.Panels.ButtonPanel;

public class Main {

    public static BluePrint bluePrint;
    public static ButtonPanel buttonPanel;
    public static int[] screenSize = new int[2];
    public static HashMap<Integer, Picture> textures = new HashMap<>();
    public static boolean snaptogrid = true;
    public static Color backgroundColor = new Color(50, 50, 55);

    public static void main(String[] args) {

        System.out.println("Welcome to Tech Drawer !\nProgram for creating technical drawings.");
        screenSize[0] = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        screenSize[1] = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 70;

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor((Image) cursorImg, new Point(0, 0), "blank cursor");

        // Create the main frame and add the blueprint, button panel
        Frame frame = new Frame();
        buttonPanel = new ButtonPanel(10, 10, screenSize[0] - 20, 80, backgroundColor);
        bluePrint = new BluePrint(10, 100, screenSize[0] - 20, screenSize[1] - 110, BluePrint.backColor);
        bluePrint.setListeners(new MouseListener(bluePrint));
        bluePrint.setCursor(blankCursor);
        buttonPanel.setListeners(new MouseListener(buttonPanel));
        frame.add(buttonPanel);
        frame.add(bluePrint);
        frame.setListeners(new KeyboardListener(bluePrint, buttonPanel));

        // Fill the textures hashmap
        try {

            // 100x100 resized to 34x34
            textures.put(null, null);
            textures.put(1, new Picture(ImageIO.read(new File("../../Resources/Pictures/PTPline.png")), 30, 40, 34, 34));
            textures.put(2, new Picture(ImageIO.read(new File("../../Resources/Pictures/Rcircle.png")), 308, 36, 38, 38));

        } catch (IOException ioe) { System.err.println("IOException: Can't find image\n" + ioe.getCause()); }
        load(new File("../../Test.txt"));
    }

    public static void repaint() {

        bluePrint.repaint();
        buttonPanel.repaint();
        
    }

    public static void save(File file) {
        try {
            if (!file.exists()) file.createNewFile();
            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < bluePrint.geometrySize(); i++) {
    
                String write = "";
                Geometry object = bluePrint.getGeometryAt(i);
                if (object instanceof Line) {
                    
                    Line line = (Line) object;
                    write = "L:";
                    write += "" + line.getStart().getX() + ":" + line.getStart().getY() + ":";
                    write += "" + line.getEnd().getX() + ":" + line.getEnd().getY() + ";\n";
                
                } else if (object instanceof Circle) {
    
                    Circle circle = (Circle) object;
                    write = "C:";
                    write += "" + circle.getCenter().getX() + ":" + circle.getCenter().getY() + ":";
                    write += "" + circle.getRadius() + ":";
                    write += "" + circle.getStartAngle() + ":" + circle.getEndAngle() + ";\n";
                }
                fw.append(write);
            }
            fw.append("?");
            fw.close();
        } catch (IOException ioe) { ioe.printStackTrace(); }
    }

    public static void load(File file) {
        if (!file.exists()) return;
        bluePrint.clearGeometry();
        try {
            FileReader fr = new FileReader(file);
            String object = "";
            while (true) {
                char character = (char) fr.read();
                if (character == '?') break;
                switch (character) {
                    case ';':
                        generateGeometry(object);
                        object = "";                
                        break;

                    case '\n': break;
                    case '\r': break;
                
                    default:
                        object += character;
                        break;
                }
            }
            fr.close();
        } catch (IOException ioe) { ioe.printStackTrace(); }
        Main.repaint();
    }

    private static void generateGeometry(String data) {
        if (data.charAt(0) == 'L') {

            int[] values = new int[4]; // Sx, Sy, Ex, Ey
            int i = 2;
            int index;
            String value;

            for (int j = 0; j < 4; j++) {
                index = data.substring(i).indexOf(':');
                value = data.substring(i, j == 3 ? data.length() : index + i);
                i += index + 1;
                values[j] = (int) Double.parseDouble(value);
            }
            bluePrint.addLine(new Line(values[0], values[1], values[2], values[3]));

        } else if (data.charAt(0) == 'C') {
            
            int[] values = new int[5]; // Cx, Cy, radius, startAngle, endAngle
            int i = 2;
            int index;
            String value;

            for (int j = 0; j < 5; j++) {
                index = data.substring(i).indexOf(':');
                value = data.substring(i, j == 4 ? data.length() : index + i);
                i += index + 1;
                values[j] = (int) Double.parseDouble(value);
            }
            bluePrint.addCircle(new Circle(values[0], values[1], values[2], (short) values[3], (short) values[4]));
        }
    }
}