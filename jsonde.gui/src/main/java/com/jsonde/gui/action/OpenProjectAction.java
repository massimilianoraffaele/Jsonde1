package com.jsonde.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.jsonde.client.Client;
import com.jsonde.gui.ApplicationUserInterface;
import com.jsonde.gui.Main;
import com.jsonde.gui.configuration.SessionConfiguration;
import com.jsonde.gui.configuration.SessionConfigurationException;
import com.jsonde.gui.sdedit.SdEditUIAdapter;

import net.sf.sdedit.icons.Icons;
/**
 * 
 * @author admin
 *
 */
public class OpenProjectAction extends AbstractAction {

    private ApplicationUserInterface applicationUserInterface;

    public OpenProjectAction(SdEditUIAdapter sdEditUIAdapter) {
        this.applicationUserInterface = sdEditUIAdapter;
    }

    {
        putValue(Action.SMALL_ICON,
                new ImageIcon(
                        Icons.class.getResource("open.png")
                ));
        putValue(Action.NAME, "Open Project");
        putValue(Action.SHORT_DESCRIPTION, "Open existing jSonde Project");
    }

    public void actionPerformed(ActionEvent e) {

        JFileChooser openDialog = new JFileChooser(
                System.getProperty("user.home") +
                        System.getProperty("file.separator") +
                        "jSondeProjects");

        openDialog.addChoosableFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".jss");
            }

            @Override
            public String getDescription() {
                return "jSonde Session Files";
            }

        });

        if (JFileChooser.APPROVE_OPTION == openDialog.showOpenDialog(applicationUserInterface.getFrame())) {

            try {
                SessionConfiguration sessionConfiguration = SessionConfiguration.loadSessionConfiguration(openDialog.getSelectedFile());

                Client client = new Client(sessionConfiguration.getDatabaseFileName());

                applicationUserInterface.setClient(client);

                client.loadMethodCalls();

            } catch (SessionConfigurationException e1) {
                Main.getInstance().processException(e1);
            }

        }

    }

}
