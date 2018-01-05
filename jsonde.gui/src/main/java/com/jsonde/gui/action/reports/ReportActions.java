package com.jsonde.gui.action.reports;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

import com.jsonde.gui.ApplicationUserInterface;
import com.jsonde.gui.action.composite.CreateCompositeComponentTabAction;
import com.jsonde.gui.components.composite.CompositeComponentProvider;
import com.jsonde.gui.reports.Report;
import com.jsonde.gui.reports.ReportCompositeComponentProvider;
import com.jsonde.gui.reports.ReportException;
import com.jsonde.gui.reports.Reports;
/**
 * 
 * @author admin
 *
 */
public class ReportActions {

    private static ReportActions instance;

    private List<Action> actions;

    /**
     * 
     * @param applicationUserInterface
     * @throws ReportException
     */
    private ReportActions(ApplicationUserInterface applicationUserInterface) throws ReportException {

        actions = new ArrayList<Action>();

        for (Report report : Reports.getReportsByGroupId("reports")) {

            CompositeComponentProvider reportCompositeComponentProvider =
                new ReportCompositeComponentProvider(report);

            actions.add(new CreateCompositeComponentTabAction(
                    applicationUserInterface, reportCompositeComponentProvider));

        }

    }

    public static ReportActions getInstance(ApplicationUserInterface applicationUserInterface)
            throws ReportException {
        if (null == instance) {
            instance = new ReportActions(applicationUserInterface);
        }
        return instance;
    }

    public List<Action> getActions() {
        return actions;
    }
    
}
