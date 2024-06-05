package com.vojat.Panels;

import javax.swing.JButton;
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
    private Color backColor = Color.decode("#005b8c");
    private boolean detailLines = true;
    
    public SettingsPanel(int width, int height, Color backgroundColor, Frame parent) {

        setSize(width, height);
        setBackground(backgroundColor);
        setLayout(null);

        JTextField newColor = new JTextField(7);
        newColor.setLocation(220, 40);
        newColor.setSize(80, 25);
        newColor.setText("#005b8c");
        newColor.addActionListener((e) -> {
            try {
                backColor = Color.decode((newColor.getText().charAt(0) == '#' ? "" : "#") + newColor.getText());
            } catch (NumberFormatException nfe) {
                backColor = Color.decode("#005b8c");
            } finally {
                this.repaint();
            }
        });

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
        button.addActionListener((e) -> { detailLines = detailLines ? false : true; this.repaint(); });
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
        
        g2d.setPaint(detailLines ? new Color(40, 255, 45) : Color.RED);
        g2d.fillRect(200, 100, 50, 50);

        g2d.setPaint(backColor);
        g2d.fillRect(320, 40, 50, 25);
    }

    private void apply(ActionEvent e, JTextField newColor) {
        try {
            backColor = Color.decode((newColor.getText().charAt(0) == '#' ? "" : "#") + newColor.getText());
        } catch (NumberFormatException nfe) {
            backColor = Color.decode("#005b8c");
        } finally {
            this.repaint();
        }
        BluePrint.backColor = this.backColor;
        BluePrint.detailLines = this.detailLines;
        Main.repaint();
    }
}
