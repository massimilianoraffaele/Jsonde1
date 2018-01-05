package com.jsonde.gui.dialog.project;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
/**
 * 
 * @author admin
 *
 */
public class FiltersTablePanel extends JPanel {

    private JTable filtersTable;

    public FiltersTablePanel(FiltersTableModel filtersTableModel, String title) {

        setLayout(new BorderLayout());

        filtersTable = new JTable();

        filtersTable.setModel(filtersTableModel);

        filtersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane pane = new JScrollPane(filtersTable);
        add(pane, BorderLayout.CENTER);
        pane.setBorder(new TitledBorder(title));

    }

    public JTable getFiltersTable() {
        return filtersTable;
    }

}
