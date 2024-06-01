package com.vojat;

import java.awt.Color;

import javax.swing.JFrame;

import com.vojat.Listeners.KeyboardListener;
import com.vojat.Panels.SettingsPanel;

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
        
        SettingsPanel content = new SettingsPanel(width, height, backgroundColor, this);
        add(content);
        setVisible(true);
    }

    public void setListeners(KeyboardListener kbl) {

        if (kbl == null) return;
        addKeyListener(kbl);
        
    }
}