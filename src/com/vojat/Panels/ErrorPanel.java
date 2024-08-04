package com.vojat.Panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.vojat.Frame;
import com.vojat.Main;

public class ErrorPanel extends JPanel {
    
    public static final int OK = 1;
    public static final int YESNO = 2;
    public static final int CLOSE = 3;
    private String message;
    private String name;

    public ErrorPanel(int width, int height, String text, String name, Color backgroundColor, File file, Frame parent, int type) {
        
        this.message = text;
        this.name = name;

        setSize(width, height);
        setLayout(null);
        setBackground(backgroundColor);

        if (type == 1) {
            JButton okBTN = new JButton("Ok");
            JButton close = new JButton("X");

            okBTN.setSize(80, 50);
            close.setSize(50, 30);

            okBTN.setLocation(400, 230);
            close.setLocation(450, 0);

            close.setForeground(Color.WHITE);
            close.setBackground(Color.RED);

            okBTN.setFocusPainted(false);
            close.setFocusPainted(false);
            close.setBorderPainted(false);

            okBTN.addActionListener((e) -> parent.dispose());
            close.addActionListener((e) -> parent.dispose());

            add(okBTN);
            add(close);
        } else if (type == 2) {
            JButton yesBTN = new JButton("Yes");
            JButton noBTN = new JButton("No");
            JButton close = new JButton("X");

            yesBTN.setSize(80, 50);
            noBTN.setSize(80, 50);
            close.setSize(50, 30);

            yesBTN.setLocation(300, 230);
            noBTN.setLocation(400, 230);
            close.setLocation(450, 0);

            yesBTN.setFocusPainted(false);
            noBTN.setFocusPainted(false);
            close.setFocusPainted(false);
            close.setBorderPainted(false);

            close.setForeground(Color.WHITE);
            close.setBackground(Color.RED);

            yesBTN.addActionListener((e) -> { Main.save(file); parent.dispose(); });
            noBTN.addActionListener((e) -> parent.dispose());
            close.addActionListener((e) -> parent.dispose());

            add(yesBTN);
            add(noBTN);
            add(close);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setFont(g2d.getFont().deriveFont(20f));
        g2d.setPaint(Color.WHITE);
        g2d.drawString(message, 20, 160);
        g2d.setFont(g2d.getFont().deriveFont(16f));
        g2d.drawString(name, 20, 22);

        // Draw the title bar line
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(0, 31, 500, 31);
    }
}
