package com.jsonde.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.jsonde.gui.ApplicationUserInterface;
import com.jsonde.gui.profiler.MethodCallProfilerView;
import com.jsonde.gui.reports.custom.DependencyReport;
/**
 * 
 * @author admin
 *
 */
public class OpenMethodCallProfilerViewAction extends AbstractAction {

    private ApplicationUserInterface applicationUserInterface;

    /**
     * 
     * @param applicationUserInterface
     */
    public OpenMethodCallProfilerViewAction(ApplicationUserInterface applicationUserInterface) {
        super(
                "Method Calls",
                new ImageIcon(
                        DependencyReport.class.getClassLoader().getResource("mycomputer.png")
                )
        );
        putValue(
                LARGE_ICON_KEY,
                new ImageIcon(
                        DependencyReport.class.getClassLoader().getResource("mycomputer_large.png")
                )
        );
        this.applicationUserInterface = applicationUserInterface;
    }

    public void actionPerformed(ActionEvent e) {

        JPanel methodCallProfilerView = new MethodCallProfilerView();
        applicationUserInterface.addTab(methodCallProfilerView, "Method Call Profiler");

    }

}