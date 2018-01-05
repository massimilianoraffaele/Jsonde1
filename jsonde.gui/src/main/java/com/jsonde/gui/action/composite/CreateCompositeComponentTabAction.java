package com.jsonde.gui.action.composite;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jsonde.gui.ApplicationUserInterface;
import com.jsonde.gui.components.composite.CompositeComponentProvider;
/**
 * 
 * @author admin
 *
 */
public class CreateCompositeComponentTabAction extends AbstractAction implements Runnable {

    private ApplicationUserInterface applicationUserInterface;
    private CompositeComponentProvider compositeComponentProvider;

    /**
     * JPanel(new BorderLayout())
     */
    JPanel panel = new JPanel(new BorderLayout());

    public CreateCompositeComponentTabAction(
            ApplicationUserInterface applicationUserInterface,
            CompositeComponentProvider compositeComponentProvider) {

        super(compositeComponentProvider.getTitle(), compositeComponentProvider.getSmallIcon());

        putValue(LARGE_ICON_KEY, compositeComponentProvider.getLargeIcon());

        this.applicationUserInterface = applicationUserInterface;
        this.compositeComponentProvider = compositeComponentProvider;

        panel.add(getWaitComponent(), BorderLayout.CENTER);

    }

    /**
     * 
     * @return
     */
    private JComponent getWaitComponent() {
        return new JLabel("Loading...");
    }

    public void actionPerformed(ActionEvent e) {

        applicationUserInterface.addTab(panel,compositeComponentProvider.getTitle());

        new Thread(this).start();

    }

    public void run() {

        final JComponent component =
                compositeComponentProvider.createCompositeComponent();

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                panel.removeAll();
                panel.add(component, BorderLayout.CENTER);
                panel.validate();

            }

        });

    }

}
