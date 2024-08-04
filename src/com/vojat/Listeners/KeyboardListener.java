package com.vojat.Listeners;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.vojat.Frame;
import com.vojat.Main;
import com.vojat.Geometry.Circle;
import com.vojat.Geometry.Geometry;
import com.vojat.Geometry.Line;
import com.vojat.Panels.BluePrint;
import com.vojat.Panels.ButtonPanel;
import com.vojat.Panels.SavePanel;

public class KeyboardListener implements KeyListener {

    private BluePrint blueprint;
    private boolean ctrl = false, shift = false, alt = false;

    public KeyboardListener(BluePrint bluePrint) {
        this.blueprint = bluePrint;
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
                
                for (int i = 0; i < Main.bluePrint.get(Main.activeBluePrint).geometrySize(); i++) Main.bluePrint.get(Main.activeBluePrint).getGeometryAt(i).select(false);
                
                // Check if there is a start point placed
                if (ButtonPanel.getSelected() != 0) {
                    if (blueprint.mouseListener.isDrawing()) {
                    
                        blueprint.mouseListener.stopDrawing();
                        break;
                    
                    } else ButtonPanel.setSelected((byte) 0);
                }

                Main.bluePrint.get(Main.activeBluePrint).mouseListener.points.clear();
                Main.repaint();
                break;

            case KeyEvent.VK_S:

                // Save if control + S
                if (ctrl) {
                    Frame fileDialog = new Frame(800, 600, "Save", false, new Color(24, 24, 24));
                    fileDialog.addContentPanel(new SavePanel(fileDialog, SavePanel.SAVE));
                }
                else Main.snaptogrid = Main.snaptogrid ? false : true;
                break;

            case KeyEvent.VK_L:

                // Load if control + L
                if (ctrl) {
                    Frame fileDialog = new Frame(800, 600, "Load", false, new Color(24, 24, 24));
                    fileDialog.addContentPanel(new SavePanel(fileDialog, SavePanel.LOAD));
                }
                break;

            case KeyEvent.VK_Z:
                
                // Remove last element
                if (blueprint.geometrySize() == 0) break;
                if (ctrl) blueprint.removeGeometryAt(blueprint.geometrySize() - 1);
                blueprint.repaint();
                break;

            case KeyEvent.VK_A:
                if (ctrl) for (int i = 0; i < blueprint.geometrySize(); i++) blueprint.getGeometryAt(i).select(true);
                blueprint.repaint();
                break;

            case KeyEvent.VK_N:
                if (ctrl) blueprint.clearGeometry();
                blueprint.repaint();
                break;

            default: break;
        }

        // Clear the input modifiers
        ctrl = false;
        alt = false;
        shift = false;
    }

    @Override
    public void keyReleased(KeyEvent ke) {}

    @Override
    public void keyTyped(KeyEvent ke) {}
}
