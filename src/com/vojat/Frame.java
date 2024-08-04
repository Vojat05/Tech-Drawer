package com.vojat;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {

    public int width;
    public int height;
    public String title;
    public Color backColor;

    public Frame() {
        this.title = "Tech Drawer";
        
        setTitle("Tech Drawer");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(50, 50, 55));
        setLayout(null);
        setVisible(true);
    }

    public Frame(int width, int height, String title, boolean maximized, Color backgroundColor) {

        this.title = title;
        this.backColor = backgroundColor;
        this.width = width;
        this.height = height;

        setSize(width, height);
        setLocation((int) (Main.screenSize[0] * .5 - 400), (int) (Main.screenSize[1] * .5 - 300)); // Centers the window
        setTitle(title);
        getContentPane().setBackground(backgroundColor);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setLayout(null);
        
        if (maximized) {

            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);

        }
        setVisible(true);
    }

    public JPanel addContentPanel(JPanel panel) {
        this.add(panel);
        return panel;
    }
}