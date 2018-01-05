package com.jsonde.gui.dialog.project;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.jsonde.api.configuration.ClassFilterDto;
import com.jsonde.gui.action.ListSelectionListenerAction;
/**
 * 
 * @author admin
 *
 */
public class CustomFiltersPanel extends JPanel implements FiltersPanel {

    private FiltersTableModel filtersTableModel;

    /**
     * setClassFilters
     */
    public void setClassFilters(List<ClassFilterDto> classFilters) {
        filtersTableModel.setClassFilters(classFilters);
    }

    public List<ClassFilterDto> getClassFilters() {
        return filtersTableModel.getClassFilters();
    }

    public CustomFiltersPanel() {

        setLayout(new GridBagLayout());

        filtersTableModel = new FiltersTableModel();

        FiltersTablePanel filtersTablePanel = new FiltersTablePanel(filtersTableModel, "Filters");
        ListSelectionModel filtersTableSelectionModel =
                filtersTablePanel.
                        getFiltersTable().
                        getSelectionModel();

        NewFilterAction newFilterAction = new NewFilterAction(filtersTableModel);
        JButton newFilterButton = new JButton(newFilterAction);

        DeleteFilterAction deleteFilterAction = new DeleteFilterAction(filtersTableModel);
        JButton deleteFilterButton = new JButton(deleteFilterAction);

        filtersTableSelectionModel.addListSelectionListener(deleteFilterAction);

        MoveFilterUpAction moveFilterUpAction = new MoveFilterUpAction(filtersTableModel, filtersTableSelectionModel);
        filtersTableSelectionModel.addListSelectionListener(moveFilterUpAction);
        JButton moveFilterUpButton = new JButton(moveFilterUpAction);

        MoveFilterDownAction moveFilterDownAction = new MoveFilterDownAction(filtersTableModel, filtersTableSelectionModel);
        filtersTableSelectionModel.addListSelectionListener(moveFilterDownAction);
        JButton moveFilterDownButton = new JButton(moveFilterDownAction);

        add(
                filtersTablePanel,
                new GridBagConstraints(
                        0, 0,
                        1, 4,
                        1, 1,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                        new Insets(5, 5, 0, 0), 0, 0
                )
        );

        add(
                newFilterButton,
                new GridBagConstraints(
                        1, 0,
                        1, 1,
                        0, 0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                        new Insets(5, 5, 5, 5), 0, 0
                )
        );

        add(
                deleteFilterButton,
                new GridBagConstraints(
                        1, 1,
                        1, 1,
                        0, 0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 5, 5, 5), 0, 0
                )
        );

        add(
                moveFilterUpButton,
                new GridBagConstraints(
                        1, 2,
                        1, 1,
                        0, 0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 5, 5, 5), 0, 0
                )
        );

        add(
                moveFilterDownButton,
                new GridBagConstraints(
                        1, 3,
                        1, 1,
                        0, 0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 5, 5, 5), 0, 0
                )
        );

        List<ClassFilterDto> classFilters = Arrays.asList(
                new ClassFilterDto(false, "java.*"),
                new ClassFilterDto(false, "sun.*"),
                new ClassFilterDto(false, "com.sun.*")
        );

        setClassFilters(classFilters);

    }

    /**
     * 
     * @author albertomadio
     * NewFilterAction
     */
    private class NewFilterAction extends AbstractAction {

        private FiltersTableModel filtersTableModel;

        /**
         * 
         * @param filtersTableModel
         */
        private NewFilterAction(FiltersTableModel filtersTableModel) {
            super("New Filter");
            this.filtersTableModel = filtersTableModel;
        }

        /**
         * actionPerformed
         */
        public void actionPerformed(ActionEvent e) {
            filtersTableModel.addClassFilter(new ClassFilterDto(false, ""));
        }

    }

    /**
     * 
     * @author albertomadio
     * DeleteFilterAction
     *
     */
    private class DeleteFilterAction extends ListSelectionListenerAction {

        private FiltersTableModel filtersTableModel;

        /**
         * 
         * @param filtersTableModel
         */
        private DeleteFilterAction(FiltersTableModel filtersTableModel) {
            super("Delete Filter");
            this.filtersTableModel = filtersTableModel;
        }
        
        /**
         * actionPerformed
         */
        public void actionPerformed(ActionEvent e) {
            filtersTableModel.deleteClassFilter(selectedId);
        }

    }
    
    /**
     * 
     * @author albertomadio
     * MoveFilterUpAction
     */
    private class MoveFilterUpAction extends ListSelectionListenerAction {

        private FiltersTableModel filtersTableModel;
        private ListSelectionModel filtersTableSelectionModel;

        /**
         * 
         * @param filtersTableModel
         * @param filtersTableSelectionModel
         */
        private MoveFilterUpAction(FiltersTableModel filtersTableModel, ListSelectionModel filtersTableSelectionModel) {
            super("Move Up");
            this.filtersTableModel = filtersTableModel;
            this.filtersTableSelectionModel = filtersTableSelectionModel;
        }

        /**
         * actionPerformed
         */
        public void actionPerformed(ActionEvent e) {
            filtersTableModel.swapRows(selectedId, selectedId - 1);
            filtersTableSelectionModel.setSelectionInterval(selectedId - 1, selectedId - 1);
        }

        @Override
        public boolean isEnabled() {
            return super.isEnabled() && selectedId > 0;
        }

    }

    /**
     * 
     * @author albertomadio
     * MoveFilterDownAction
     */
    private class MoveFilterDownAction extends ListSelectionListenerAction {

        private FiltersTableModel filtersTableModel;
        private ListSelectionModel filtersTableSelectionModel;

        /**
         * 
         * @param filtersTableModel
         * @param filtersTableSelectionModel
         */
        private MoveFilterDownAction(FiltersTableModel filtersTableModel, ListSelectionModel filtersTableSelectionModel) {
            super("Move Down");
            this.filtersTableModel = filtersTableModel;
            this.filtersTableSelectionModel = filtersTableSelectionModel;
        }

        /**
         * actionPerformed
         */
        public void actionPerformed(ActionEvent e) {
            filtersTableModel.swapRows(selectedId, selectedId + 1);
            filtersTableSelectionModel.setSelectionInterval(selectedId + 1, selectedId + 1);
        }

        @Override
        public boolean isEnabled() {
            return super.isEnabled() && selectedId < filtersTableModel.getRowCount() - 1;
        }

    }


}