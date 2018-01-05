package com.jsonde.gui.dialog.project;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsonde.api.configuration.ClassFilterDto;
/**
 * 
 * @author admin
 *
 */
public class FiltersPanelImpl extends JPanel implements ChangeListener, FiltersPanel {

    private JTabbedPane tabbedPane;

    private FiltersPanel concreteFiltersPanel;

    public void setClassFilters(List<ClassFilterDto> classFilters) {
        concreteFiltersPanel.setClassFilters(classFilters);
    }

    public List<ClassFilterDto> getClassFilters() {
        return concreteFiltersPanel.getClassFilters();
    }

    public void stateChanged(ChangeEvent e) {

        concreteFiltersPanel =
                (FiltersPanel) tabbedPane.getSelectedComponent();

    }

    public FiltersPanelImpl() {

        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        SimpleFiltersPanel simpleFiltersPanel = new SimpleFiltersPanel();
        concreteFiltersPanel = simpleFiltersPanel;
        tabbedPane.addTab("Package Filter", simpleFiltersPanel);
        tabbedPane.addTab("Custom Filter", new CustomFiltersPanel());

        tabbedPane.addChangeListener(this);

        tabbedPane.setSelectedIndex(0);


        add(tabbedPane, BorderLayout.CENTER);

    }

}
