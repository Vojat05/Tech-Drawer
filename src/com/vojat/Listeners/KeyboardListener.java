package com.vojat.Listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.vojat.Panels.BluePrint;

public class KeyboardListener implements KeyListener {

    private BluePrint bluePrint;
    private boolean ctrl = false, shift = false, alt = false;

    public KeyboardListener(BluePrint bp) {

        this.bluePrint = bp;
    
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
        if (ctrl) {}
        if (shift) {}
        if (alt) {}
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

            default: break;
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {}
}
