package com.vojat.Panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vojat.Frame;
import com.vojat.Main;
import com.vojat.DataStructures.Directory;

public class SavePanel extends JPanel {

    public static final byte SAVE = 1;
    public static final byte LOAD = 2;
    public byte type;
    private int width, height;
    private String title;

    public SavePanel(Frame parent, byte type) {

        this.width = parent.width;
        this.height = parent.height;
        this.title = parent.title;
        this.type = type;

        setSize(width, height);
        setBackground(parent.backColor);
        setLayout(null);

        JTextField fileLocation = new JTextField();
        fileLocation.setText(type == 1 ? "Untitled.txt" : "");
        fileLocation.setSize(400, 25);
        fileLocation.setLocation(150, 550);
        add(fileLocation);

        JButton button = new JButton("X");
        button.setSize(50, 30);
        button.setLocation(750, 0);
        button.setBackground(Color.RED);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener((e) -> { parent.dispose(); });
        add(button);

        button = new JButton(type == 1 ? "Save" : "Load");
        button.setSize(75, 25);
        button.setLocation(560, 550);
        if (type == 1) button.addActionListener((e) -> { Main.save(new File("../../Resources/Files/" + fileLocation.getText())); parent.dispose(); });
        else if (type == 2) button.addActionListener((e) -> { Main.load(new File("../../Resources/Files/" + fileLocation.getText())); parent.dispose(); });
        add(button);

        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the interaction bar
        g2d.setPaint(Color.WHITE);
        g2d.setStroke(new BasicStroke(1));
        g2d.setFont(g2d.getFont().deriveFont(16f));
        g2d.drawLine(0, 30, this.width, 30);
        g2d.drawString(title, 40, 20);

        // Draw the icon
        if (type == 1) {
            g2d.drawLine(10, 25, 30, 25);
            g2d.drawLine(20, 5, 20, 24);
            g2d.drawLine(15, 15, 20, 24);
            g2d.drawLine(25, 15, 20, 24);
        } else if (type == 2) {
            g2d.drawLine(10, 25, 30, 25);
            g2d.drawLine(20, 5, 20, 24);
            g2d.drawLine(15, 15, 20, 5);
            g2d.drawLine(25, 15, 20, 5);
        }

        // Draw the directory window frame
        g2d.setPaint(Color.WHITE);
        g2d.drawRect(20, 50, this.width - 40, this.height - 120);

        // Drawing the directories
        File currentFile;
        try {

            currentFile = new File("../../Resources/Files");
            if (!currentFile.exists()) currentFile.createNewFile();
            Directory dir = new Directory(currentFile.getName(), 0);
            String[] subFiles = currentFile.list();
            for (int i = 0; i < subFiles.length; i++) dir.addContent(new com.vojat.DataStructures.File(subFiles[i], ".txt"));
            com.vojat.DataStructures.Directory.draw(50, 60, dir, g2d);

        } catch(IOException ioe) { ioe.printStackTrace(); }
    }
}
