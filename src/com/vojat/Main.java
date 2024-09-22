package com.vojat;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.vojat.DataStructures.JSONEditor;
import com.vojat.Geometry.Circle;
import com.vojat.Geometry.Geometry;
import com.vojat.Geometry.Line;
import com.vojat.Geometry.Picture;
import com.vojat.Listeners.KeyboardListener;
import com.vojat.Listeners.MouseListener;
import com.vojat.Panels.BluePrint;
import com.vojat.Panels.ButtonPanel;

public class Main {

    public static ArrayList<BluePrint> bluePrint = new ArrayList<>();
    public static ButtonPanel buttonPanel;
    public static int[] screenSize = new int[2];
    public static HashMap<Integer, Picture> textures = new HashMap<>();
    public static boolean snaptogrid = true;
    public static Color backgroundColor = new Color(50, 50, 55);
    public static int activeBluePrint = 0;
    public static Cursor blankCursor;
    public static Cursor defaultCursor;
    public static LogicThread logicThread;
    public static boolean enableLogicThread = false;
    public static boolean donateButton = true;
    public static boolean donatePanel = false;
    public static boolean debug = false;
    private static Frame frame;

    public static void main(String[] args) {
        System.out.println("Welcome to Tech Drawer !\nProgram for creating technical drawings.");

        // Get the startup config
        try {
            JSONEditor je = new JSONEditor("../../Resources/Config.json");
            donateButton = Boolean.parseBoolean(je.readData("Donate-Button"));
            debug = Boolean.parseBoolean(je.readData("Debug"));
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }

        screenSize[0] = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        screenSize[1] = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 70;

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor
        defaultCursor = Cursor.getDefaultCursor();
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor((Image) cursorImg, new Point(0, 0), "blank cursor");

        // Create the main frame and add the blueprint, button panel
        frame = new Frame();
        buttonPanel = new ButtonPanel(10, 10, screenSize[0] - 20, 80, backgroundColor);
        bluePrint.add(new BluePrint(10, 100, screenSize[0] - 20, screenSize[1] - 110, BluePrint.backColor));
        buttonPanel.setListeners(new MouseListener(buttonPanel));
        frame.add(buttonPanel);
        frame.add(bluePrint.get(0));
        frame.addKeyListener(new KeyboardListener(bluePrint.get(activeBluePrint)));

        // Fill the textures hashmap
        try {

            // 100x100 resized to 34x34
            textures.put(null, null);
            textures.put(1, new Picture(ImageIO.read(new File("../../Resources/Pictures/PTPline.png")), 30, 40, 34, 34));
            textures.put(2, new Picture(ImageIO.read(new File("../../Resources/Pictures/Rcircle.png")), 308, 36, 38, 38));
            textures.put(3, new Picture(ImageIO.read(new File("../../Resources/Pictures/LineSettings.png")), 220, 30, 45, 45));

        } catch (IOException ioe) { System.err.println("IOException: Can't find image\n" + ioe.getCause()); }
        load(new File("../../Test.txt"));
    }

    public static void repaint() {

        bluePrint.get(activeBluePrint).repaint();
        buttonPanel.repaint();
        
    }

    public static void save(File file) {
        try {
            if (!file.exists()) file.createNewFile();
            String fileExtension = file.getName().substring(file.getName().indexOf('.') + 1);
            
            if (fileExtension.equals("png")) {
                Export.exportToPNG(file, bluePrint.get(0).getGeometryCopy());
                return;
            }

            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < bluePrint.get(0).geometrySize(); i++) {
    
                String write = "";
                Geometry object = bluePrint.get(0).getGeometryAt(i);
                if (object instanceof Line) {
                    
                    Line line = (Line) object;
                    write = "L:";
                    write += "" + line.getStart().getX() + ":" + line.getStart().getY() + ":";
                    write += "" + line.getEnd().getX() + ":" + line.getEnd().getY() + ":";
                    write += "" + line.getThickness() + ";\n";
                
                } else if (object instanceof Circle) {
    
                    Circle circle = (Circle) object;
                    write = "C:";
                    write += "" + circle.getCenter().getX() + ":" + circle.getCenter().getY() + ":";
                    write += "" + circle.getRadius() + ":";
                    write += "" + circle.getStartAngle() + ":" + circle.getEndAngle() + ":";
                    write += "" + circle.getThickness() + ";\n";
                }
                fw.append(write);
            }
            fw.append("?");
            fw.close();
        } catch (IOException ioe) { ioe.printStackTrace(); }
        System.gc();
    }

    public static void load(File file) {
        if (!file.exists()) return;
        bluePrint.get(0).clearGeometry();
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
        System.gc();
    }

    private static void generateGeometry(String data) {
        if (data.charAt(0) == 'L') {

            int[] values = new int[5]; // Sx, Sy, Ex, Ey
            int i = 2;
            int index = 0;
            String value = "";

            for (int j = 0; j < 5; j++) {
                index = data.substring(i).indexOf(':');
                value = data.substring(i, j == 4 ? data.length() : index + i);
                i += index + 1;
                values[j] = (int) Double.parseDouble(value);
            }
            bluePrint.get(activeBluePrint).addLine(new Line(values[0], values[1], values[2], values[3], (byte) values[4]));

        } else if (data.charAt(0) == 'C') {
            
            int[] values = new int[6]; // Cx, Cy, radius, startAngle, endAngle
            int i = 2;
            int index;
            String value;

            for (int j = 0; j < 6; j++) {
                index = data.substring(i).indexOf(':');
                value = data.substring(i, j == 5 ? data.length() : index + i);
                i += index + 1;
                values[j] = (int) Double.parseDouble(value);
            }
            bluePrint.get(activeBluePrint).addCircle(new Circle(values[0], values[1], values[2], (short) values[3], (short) values[4], (byte) values[5]));
        }
    }

    public static void changeSheet(int sheetNumber) {
        frame.remove(bluePrint.get(activeBluePrint));
        frame.add(bluePrint.get(sheetNumber));
        activeBluePrint = sheetNumber;
        repaint();
    }
}