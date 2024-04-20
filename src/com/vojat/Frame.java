package com.vojat;

import java.awt.Color;

import javax.swing.JFrame;

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
}