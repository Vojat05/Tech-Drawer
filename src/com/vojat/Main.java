package com.vojat;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.vojat.Listeners.KeyboardListener;
import com.vojat.Listeners.MouseListener;
import com.vojat.Panels.BluePrint;
import com.vojat.Panels.ButtonPanel;

public class Main {

    public static HashMap<String, BufferedImage> textures = new HashMap<>();

    public static void main(String[] args) {

        System.out.println("Welcome to Tech Drawer !\nProgram for creating technical drawings.");
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 70;

        // Create the main frame and add the blueprint, button panel
        Frame frame = new Frame();
        ButtonPanel buttons = new ButtonPanel(10, 10, width - 20, 80, new Color(50, 50, 55));
        BluePrint bleuprint = new BluePrint(10, 100, width - 20, height - 110, new Color(0, 91, 140));
        bleuprint.setListeners(new KeyboardListener(bleuprint), new MouseListener(bleuprint));
        buttons.setListeners(new MouseListener(buttons));
        frame.add(buttons);
        frame.add(bleuprint);

        // Fill the textures hashmap
        try {

            // 100x100 resized to 34x34
            textures.put(null, null);
            textures.put("PTPline", ImageIO.read(new File("../../Resources/Pictures/PTPline.png")));

        } catch (IOException ioe) { System.err.println("IOException: Can't find image\n" + ioe.getCause()); }
    }
}