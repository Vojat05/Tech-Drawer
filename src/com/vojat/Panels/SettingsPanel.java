package com.vojat.Panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vojat.Frame;
import com.vojat.Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

public class SettingsPanel extends JPanel {

    public static boolean isOpen = false;
    private Frame parent;
    
    public SettingsPanel(int width, int height, Color backgroundColor, Frame parent) {
        this.parent = parent;

        setSize(width, height);
        setBackground(backgroundColor);
        setLayout(null);

        JTextField newColor = new JTextField(7);
        newColor.setLocation(220, 40);
        newColor.setSize(80, 25);

        JButton button = new JButton("Apply");
        button.setSize(150, 60);
        button.setLocation(625, 515);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener((e) -> apply(e, newColor));
        add(button);

        button = new JButton("X");
        button.setLocation(740, 10);
        button.setSize(50, 30);
        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener((e) -> { parent.dispose(); isOpen = false; });
        add(button);

        button = new JButton("Background Lines");
        button.setSize(150, 50);
        button.setLocation(20, 100);
        button.addActionListener((e) -> apply(e));

        JLabel linesIcon = new JLabel();
        linesIcon.setBackground(new Color(35, 233, 19));
        linesIcon.setSize(50, 50);
        linesIcon.setLocation(200, 100);
        add(linesIcon);
        add(button);
        add(newColor);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.WHITE);
        g2d.setFont(g2d.getFont().deriveFont(16f));
        g2d.drawString("Blueprint bakground color:", 20, 60);
    }

    private void apply(ActionEvent e, JTextField newColor) {
        
        String text = newColor.getText();
        BluePrint.backColor = Color.decode((text.charAt(0) == '#' ? "" : "#") + text);
        Main.repaint();
    }

    private void apply(ActionEvent e) {

        BluePrint.detailLines = BluePrint.detailLines ? false : true;
        Main.repaint();
    }
}
