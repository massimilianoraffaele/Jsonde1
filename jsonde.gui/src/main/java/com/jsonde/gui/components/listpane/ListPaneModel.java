package com.jsonde.gui.components.listpane;

import javax.swing.Action;
import javax.swing.Icon;
/**
 * 
 * @author admin
 *
 */
public interface ListPaneModel {

	/**
	 * 
	 * @return
	 */
    int getSize();

    String getLabelAt(int index);

    Icon getIconAt(int index);

    Action getActionAt(int index);

}
