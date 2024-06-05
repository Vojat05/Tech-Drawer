package com.vojat.Listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import com.vojat.Main;
import com.vojat.DataStructures.Circle;
import com.vojat.DataStructures.Geometry;
import com.vojat.DataStructures.Line;
import com.vojat.Panels.BluePrint;
import com.vojat.Panels.ButtonPanel;

public class KeyboardListener implements KeyListener {

    private BluePrint blueprint;
    private ButtonPanel bp;
    private boolean ctrl = false, shift = false, alt = false;

    public KeyboardListener(BluePrint bluePrint, ButtonPanel bp) {

        this.blueprint = bluePrint;
        this.bp = bp;

    }

    @Override
    public void keyPressed(KeyEvent ke) {

        // The modifier keys
        int key = ke.getKeyCode();

        switch (key) {
            case KeyEvent.VK_CONTROL:
                ctrl = true;
                return;

            case KeyEvent.VK_SHIFT:
                shift = true;
                return;

            case KeyEvent.VK_ALT:
                alt = true;
                return;
        
            default: break;
        }

        switch (key) {
            case KeyEvent.VK_DELETE:
                
                // Removing geometry
                for (int i = 0; i < blueprint.geometrySize(); i++) {

                    Geometry object = blueprint.getGeometryAt(i);
                    
                    // Removing lines
                    if (object instanceof Line) {

                        Line line = (Line) object;
                        if (line.isSelected()) blueprint.removeGeometryAt(i--);

                    }

                    // Removing circles
                    if (object instanceof Circle) {

                        Circle circle = (Circle) object;
                        if (circle.isSelected()) blueprint.removeGeometryAt(i--);

                    }
                }
                blueprint.repaint();
                break;

            case KeyEvent.VK_ESCAPE:
                
                for (int i = 0; i < blueprint.geometrySize(); i++) blueprint.getGeometryAt(i).select(false);
                blueprint.repaint();
                break;

            case KeyEvent.VK_S:
                // Save if control + S
                if (ctrl) {
                    try {
                        File saveData = new File("../../Data.txt");
                        saveData.createNewFile();
                        Main.save(saveData);
                        System.out.println("Saved to file: " + saveData.getAbsolutePath());
                    } catch (IOException ioe) { ioe.printStackTrace(); }
                }
                else Main.snaptogrid = Main.snaptogrid ? false : true;
                break;

            case KeyEvent.VK_L:
                if (ctrl) {
                    File saveData = new File("../../Data.txt");
                    if (!saveData.exists()) break;
                    Main.load(saveData);
                }
                break;

            case KeyEvent.VK_Z:
                
                // Remove last element
                if (blueprint.geometrySize() == 0) break;
                if (ctrl) blueprint.removeGeometryAt(blueprint.geometrySize() - 1);
                blueprint.repaint();
                break;

            default: break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();

        switch (key) {
            case KeyEvent.VK_CONTROL:
                ctrl = false;
                break;
            
            case KeyEvent.VK_SHIFT:
                shift = false;
                break;

            case KeyEvent.VK_ALT:
                alt = false;
                break;

            case KeyEvent.VK_ESCAPE:
                if (ButtonPanel.getSelected() != 0) {

                    // Check if there is a start point placed
                    if (blueprint.mouseListener.isDrawing()) {

                        blueprint.mouseListener.stopDrawing();
                        break;

                    } else ButtonPanel.setSelected((byte) 0);
                }

                bp.repaint();
                break;

            default: break;
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {}
}
