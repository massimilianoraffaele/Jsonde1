package com.jsonde.gui.components.composite;

import javax.swing.Icon;
import javax.swing.JComponent;
/**
 * 
 * @author admin
 *
 */
public interface CompositeComponentProvider {

	/**
	 * 
	 * @return
	 */
    JComponent createCompositeComponent();

    String getTitle();

    Icon getSmallIcon();

    Icon getLargeIcon();

}
