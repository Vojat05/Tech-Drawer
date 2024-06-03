package com.vojat;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.vojat.DataStructures.Picture;
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
    }

    public static void repaint() {

        bluePrint.repaint();
        buttonPanel.repaint();
        
    }

    public static void save(File file) {

    }

    public static boolean validHEX(String value) {

        if (value.length() > 7 || value.length() < 6) return false;
        else if (value.contains("#") && value.length() != 7) return false;
        else {
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                if (c < 48 && c > 57 || c < 97 && c > 102) return false;
            }
            return true;
        }

    }

    public static float[] quadratic(float a, float b, float c) {
        
        float[] solution = new float[2];
        float d = (float) Math.pow(Math.pow(b, 2) - 4 * a * c, .5);
        
        solution[0] = (-b + d) / (2 * a);
        solution[1] = (-b - d) / (2 * a);

        return solution;
    }
}