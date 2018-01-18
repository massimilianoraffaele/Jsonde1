package com.jsonde.gui.dialog.error;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jsonde.gui.dialog.JSondeDialog;
/**
 * 
 * @author admin
 *
 */
public class ErrorsDialog extends JSondeDialog {

    private StringBuffer stringBuffer;

    /**
     * 
     * @param stringBuffer
     */
    public ErrorsDialog(StringBuffer stringBuffer) {

        this.stringBuffer = stringBuffer;

        setSize(780, 580);
        setTitle("jSonde Errors");
        setResizable(false);
        setModal(true);
        setBounds(getFrameBounds());

        Container contentPane = getContentPane();

        contentPane.setLayout(new BorderLayout());

        JTextArea errorsTextArea = new JTextArea(stringBuffer.toString());
        errorsTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(errorsTextArea);

        contentPane.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton(new AbstractAction() {

            {
                putValue(Action.NAME, "Close");
            }

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }

        });

        JPanel closeButtonPanel = new JPanel();
        closeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        closeButtonPanel.add(closeButton);

        contentPane.add(closeButtonPanel, BorderLayout.SOUTH);

    }


}
