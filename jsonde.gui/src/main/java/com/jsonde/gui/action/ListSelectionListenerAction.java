package com.jsonde.gui.action;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * 
 * @author admin
 *
 */
public abstract class ListSelectionListenerAction
        extends AbstractAction implements
        ListSelectionListener {

    protected ListSelectionListenerAction() {
    }

    protected ListSelectionListenerAction(String name) {
        super(name);
    }

    protected ListSelectionListenerAction(String name, Icon icon) {
        super(name, icon);
    }

    /**
     * selectedId
     */
    protected int selectedId = -1;

    /**
     * 
     * @param e
     */
    public void valueChanged(ListSelectionEvent e) {
        findSelection(e);
    }

    /**
     * 
     * @param e
     */
    private void findSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            ListSelectionModel mod = (ListSelectionModel) e.getSource();
            int newSelectedId = getSelectedId(mod, e);
            if (selectedId != newSelectedId) {

                boolean wasEnabled = isEnabled();

                selectedId = newSelectedId;

                if (wasEnabled != isEnabled()) {
                    firePropertyChange("enabled", wasEnabled, isEnabled());
                }

            }
        }
    }

    private int getSelectedId(ListSelectionModel mod, ListSelectionEvent e) {
        int result = -1;
        int a = e.getFirstIndex();
        boolean b1 = a <= e.getLastIndex();
        
        for (int i=a; b1; i++) {
            if (mod.isSelectedIndex(i)) {
                result = i;
                break;
            }
            i = e.getFirstIndex();
            b1 = i <= e.getLastIndex();
        }
        return result;
    }

    @Override
    public boolean isEnabled() {
        return -1 != selectedId;
    }

}
