package com.jsonde.gui.components.composite;

import javax.swing.*;
/**
 * 
 * @author admin
 *
 */
public interface CompositeComponentProvider {

    JComponent createCompositeComponent();

    String getTitle();

    Icon getSmallIcon();

    Icon getLargeIcon();

}
