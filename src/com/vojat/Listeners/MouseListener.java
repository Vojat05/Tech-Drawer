package com.vojat.Listeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import com.vojat.Panels.BluePrint;

public class MouseListener implements MouseInputListener {

    private BluePrint bluePrint;

    public MouseListener(BluePrint bp) {

        this.bluePrint = bp;

    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {
        System.out.println("Mouse pressed, location: " + me.getX() + ", " + me.getY());
    }

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseMoved(MouseEvent me) {}

    @Override
    public void mouseDragged(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
}
