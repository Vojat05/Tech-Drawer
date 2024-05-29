package com.vojat;

import java.awt.Color;

import javax.swing.JFrame;

import com.vojat.Listeners.KeyboardListener;

public class Frame extends JFrame {

    public Frame() {
        setTitle("Tech Drawer");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(50, 50, 55));
        setLayout(null);
        setVisible(true);
    }

    public Frame(int width, int height, String title, boolean maximized, Color backgroundColor) {
        setSize(width, height);
        setLocation((int) (Main.screenSize[0] * .5 - 400), (int) (Main.screenSize[1] * .5 - 300));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(title);
        if (maximized) {

            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);

        }
        getContentPane().setBackground(backgroundColor);
        setLayout(null);
        setVisible(true);
    }

    public void setListeners(KeyboardListener kbl) {

        if (kbl == null) return;
        addKeyListener(kbl);
        
    }
}