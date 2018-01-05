package com.jsonde.gui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.border.Border;
/**
 * 
 * @author admin
 *
 */
public class JActionIcon extends JLabel implements MouseListener {

    private Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
    private Action action;

    public JActionIcon(Action action) {

        super();

        Object smallIconObject = action.getValue(Action.SMALL_ICON);
        if (null != smallIconObject) {
            setIcon((Icon) smallIconObject);
        }

        this.action = action;

        setBorder(emptyBorder);
        addMouseListener(this);

    }

    public void mouseEntered(MouseEvent e) {
        setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public void mouseExited(MouseEvent e) {
        setBorder(emptyBorder);
    }

    public void mousePressed(MouseEvent e) {
        setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void mouseReleased(MouseEvent e) {
        setBorder(emptyBorder);

        Component component = e.getComponent();
        if (component.contains(e.getPoint())) {
            ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "iconClicked");
            action.actionPerformed(event);
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

}
