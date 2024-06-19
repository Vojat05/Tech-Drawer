package com.vojat.Panels;

import java.awt.Color;

import javax.swing.JPanel;

public class ErrorPanel extends JPanel {
    
    public static final int OK = 1;
    public static final int YESNO = 2;
    public static final int CLOSE = 3;

    public ErrorPanel(int width, int height, String text, Color backgroundColor, int type) {
        setSize(width, height);
        setLayout(null);

    }
}
