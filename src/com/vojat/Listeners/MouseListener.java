package com.vojat.Listeners;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.vojat.Panels.BluePrint;
import com.vojat.Panels.ButtonPanel;

public class MouseListener implements MouseInputListener {

    private JPanel parent;

    public MouseListener(JPanel bp) {

        this.parent = bp;

    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {

        switch (me.getButton()) {
            case 1:
                System.out.println("BTN 1");
                break;
            
            case 2:
                System.out.println("BTN 2");
                break;
            
            case 3:
                System.out.println("BTN 3");
                break;
            
            default:
                System.out.println("BTN 4+");
                break;
        }

        System.out.println("Mouse pressed, location: " + me.getX() + ", " + me.getY());
        
        // Is it a BluePrint ??
        if (parent instanceof BluePrint) {}

        // It's a ButtonPanel
        if (me.getX() >= 30 && me.getX() <= 64 && me.getY() >= 40 && me.getY() <= 74) {

            ButtonPanel btnp = (ButtonPanel) parent;
            btnp.setSelected((byte) 0x0);
            btnp.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseMoved(MouseEvent me) {

        // Is it a BluePrint ??
        if (parent instanceof BluePrint) {

            BluePrint bp = (BluePrint) parent;
            bp.setMousePos(me.getX(), me.getY());
            bp.repaint();
            return;

        }
        // It's a ButtonPanel
    }

    @Override
    public void mouseDragged(MouseEvent me) { mouseMoved(me); }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
}
