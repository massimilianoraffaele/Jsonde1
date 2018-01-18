package com.jsonde.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import com.jsonde.gui.sdedit.SdEditUIAdapter;

import net.sf.sdedit.icons.Icons;
/**
 * 
 * @author admin
 *
 */
public class CloseAllDiagramsAction extends AbstractAction {

    private SdEditUIAdapter sdEditUIAdapter;

    /**
     * 
     * @param sdEditUIAdapter
     */
    public CloseAllDiagramsAction(SdEditUIAdapter sdEditUIAdapter) {
        this.sdEditUIAdapter = sdEditUIAdapter;
    }

    {
        putValue(Action.SMALL_ICON,
                new ImageIcon(
                        Icons.class.getResource("close.png")
                ));
        putValue(Action.NAME, "Close All");
        putValue(Action.SHORT_DESCRIPTION, "Close all tabs");
    }

    public void actionPerformed(ActionEvent e) {

    	boolean b1 = sdEditUIAdapter.getUserInterface().removeCurrentTab(false);
        while (b1) {
        	
        	System.out.println(sdEditUIAdapter);
        	b1 = sdEditUIAdapter.getUserInterface().removeCurrentTab(false);
        }

    }

}
