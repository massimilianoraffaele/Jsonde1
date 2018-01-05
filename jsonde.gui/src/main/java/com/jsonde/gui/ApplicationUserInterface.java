package com.jsonde.gui;

import java.awt.Component;

import javax.swing.JFrame;

import com.jsonde.client.Client;

/**
 * Created by IntelliJ IDEA.
 * User: dmitrybedrin
 * Date: Oct 21, 2009
 * Time: 3:16:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ApplicationUserInterface {

	/**
	 * 
	 * @param component
	 * @param title
	 */
    void addTab(Component component, String title);

    Client getClient();

    void setClient(Client client);

    void processException(Throwable e);

    JFrame getFrame();

}
