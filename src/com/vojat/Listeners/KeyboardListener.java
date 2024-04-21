package com.vojat.Listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.vojat.Main;
import com.vojat.DataStructures.Circle;
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
                break;

            case KeyEvent.VK_SHIFT:
                shift = true;
                break;

            case KeyEvent.VK_ALT:
                alt = true;
                break;
        
            default: break;
        }

        // Modified key actions based on modifier
        if (ctrl) { return; }
        if (shift) { return; }
        if (alt) { return; }

        switch (key) {
            case KeyEvent.VK_DELETE:
                
                // Removing lines
                for (int i = 0; i < blueprint.getLinesSize(); i++) {
                    
                    Line line = blueprint.getLine(i);
                    if (line.isSelected()) blueprint.removeLine(i);

                }

                // Removing circles
                for (int i = 0; i < blueprint.getCirclesSize(); i++) {

                    Circle circle = blueprint.getCircle(i);
                    if (circle.isSelected()) blueprint.removeCircle(i);

                }
                blueprint.repaint();
                break;

            case KeyEvent.VK_ESCAPE:
                
                for (int i = 0; i < blueprint.getLinesSize(); i++) blueprint.getLine(i).select(false);
                for (int i = 0; i < blueprint.getCirclesSize(); i++) blueprint.getCircle(i).select(false);
                break;

            case KeyEvent.VK_S:
                Main.snaptogrid = Main.snaptogrid ? false : true;
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
