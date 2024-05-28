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

    public static HashMap<Integer, Picture> textures = new HashMap<>();
    public static boolean snaptogrid = true;

    public static void main(String[] args) {

        System.out.println("Welcome to Tech Drawer !\nProgram for creating technical drawings.");
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 70;

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor((Image) cursorImg, new Point(0, 0), "blank cursor");

        // Create the main frame and add the blueprint, button panel
        Frame frame = new Frame();
        ButtonPanel buttons = new ButtonPanel(10, 10, width - 20, 80, new Color(50, 50, 55));
        BluePrint blueprint = new BluePrint(10, 100, width - 20, height - 110, new Color(0, 91, 140));
        blueprint.setListeners(new MouseListener(blueprint));
        blueprint.setCursor(blankCursor);
        buttons.setListeners(new MouseListener(buttons));
        frame.add(buttons);
        frame.add(blueprint);
        frame.setListeners(new KeyboardListener(blueprint, buttons));

        // Fill the textures hashmap
        try {

            // 100x100 resized to 34x34
            textures.put(null, null);
            textures.put(1, new Picture(ImageIO.read(new File("../../Resources/Pictures/PTPline.png")), 30, 40, 34, 34));
            textures.put(2, new Picture(ImageIO.read(new File("../../Resources/Pictures/Rcircle.png")), 308, 36, 38, 38));

        } catch (IOException ioe) { System.err.println("IOException: Can't find image\n" + ioe.getCause()); }
    }

    public static float[] quadratic(float a, float b, float c) {
        
        float[] solution = new float[2];
        float d = (float) Math.pow(Math.pow(b, 2) - 4 * a * c, .5);
        
        solution[0] = (-b + d) / (2 * a);
        solution[1] = (-b - d) / (2 * a);

        return solution;
    }
}