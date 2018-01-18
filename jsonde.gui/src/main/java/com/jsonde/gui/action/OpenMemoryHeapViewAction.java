package com.jsonde.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jsonde.client.Client;
import com.jsonde.gui.ApplicationUserInterface;
import com.jsonde.gui.profiler.HeapProfilerView;
import com.jsonde.gui.reports.custom.DependencyReport;
/**
 * 
 * @author admin
 *
 */
public class OpenMemoryHeapViewAction extends AbstractAction {

    private ApplicationUserInterface applicationUserInterface;

    /**
     * 
     * @param applicationUserInterface
     */
    public OpenMemoryHeapViewAction(ApplicationUserInterface applicationUserInterface) {
        super(
                "Heap",
                new ImageIcon(
                        DependencyReport.class.getClassLoader().getResource("moc_src.png")
                )
        );
        putValue(
                LARGE_ICON_KEY,
                new ImageIcon(
                        DependencyReport.class.getClassLoader().getResource("moc_src_large.png")
                )
        );
        this.applicationUserInterface = applicationUserInterface;
    }

    public void actionPerformed(ActionEvent e) {

        final Client client = applicationUserInterface.getClient();

        if (client.isOnline()) {
            new Thread(new Runnable() {

                public void run() {

                    client.dumpHeap();

                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            JPanel heapProfilerView = new HeapProfilerView(client);
                            applicationUserInterface.addTab(heapProfilerView, "Memory Heap");
                        }

                    });

                }

            }).start();
        }

    }

}