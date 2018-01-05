package com.jsonde.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import com.jsonde.gui.ApplicationUserInterface;

import net.sf.sdedit.icons.Icons;
/**
 * 
 * @author admin
 *
 */
public class ExitJSondeAction extends AbstractAction {

    private ApplicationUserInterface sdEditUIAdapter;

    public ExitJSondeAction(ApplicationUserInterface sdEditUIAdapter) {
        this.sdEditUIAdapter = sdEditUIAdapter;
    }

    {
        putValue(Action.SMALL_ICON,
                new ImageIcon(
                        Icons.class.getResource("exit.png")
                ));
        putValue(Action.NAME, "Exit jSonde");
        putValue(Action.SHORT_DESCRIPTION, "Exit jSonde");
    }

    public void actionPerformed(ActionEvent e) {

        System.exit(0);

    }

}