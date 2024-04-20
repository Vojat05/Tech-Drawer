package com.vojat;

import java.awt.Color;
import java.awt.Toolkit;

import com.vojat.Panels.BluePrint;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to Tech Drawer !\nProgram for creating technical drawings.");
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 70;

        // Create the main frame and add the blueprint panel
        Frame frame = new Frame();
        BluePrint bleuprint = new BluePrint(10, 100, width - 20, height - 110, new Color(0, 91, 140));
        frame.add(bleuprint);
        bleuprint.repaint();
    }
}