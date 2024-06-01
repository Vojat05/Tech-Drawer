package com.vojat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vojat.Listeners.KeyboardListener;
import com.vojat.Panels.BluePrint;

public class Frame extends JFrame {

    public class Panel extends JPanel {

        public Panel(int width, int height, Color color) {
            setSize(width, height);
            setBackground(color);
            setLayout(null);

            JTextField input = new JTextField(7);
            input.setLocation(260, 80);
            input.setSize(80, 25);

            JButton button = new JButton("Apply");
            button.setSize(150, 60);
            button.setLocation(625, 515);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.addActionListener((e) -> apply(e, input));
            add(button);

            button = new JButton("X");
            button.setSize(50, 30);
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
            button.setLocation(740, 10);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.addActionListener((e) -> dispose());
            add(button);
            add(input);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Drawing the settings stuff
            g2d.setPaint(Color.WHITE);
            g2d.setFont(getFont().deriveFont(16f));
            g2d.drawString("Blueprint background color: ", 60, 100);
        }
    }

    public Panel content;

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
        
        content = new Panel(width, height, backgroundColor);
        add(content);
        setVisible(true);
    }

    public void setListeners(KeyboardListener kbl) {

        if (kbl == null) return;
        addKeyListener(kbl);
        
    }

    public void apply(ActionEvent e, JTextField input) {
        
        // Apply settings changes
        String colorHEX = input.getText();
        BluePrint.backColor = Color.decode((colorHEX.charAt(0) == '#' ? "" : "#") + colorHEX);
        Main.repaint();
        dispose();
    }
}