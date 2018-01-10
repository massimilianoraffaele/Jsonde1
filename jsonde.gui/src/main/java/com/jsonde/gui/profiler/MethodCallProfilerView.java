package com.jsonde.gui.profiler;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.freehep.swing.treetable.AbstractTreeTableModel;
import org.freehep.swing.treetable.JTreeTable;
import org.freehep.swing.treetable.TreeTableModel;

import com.jsonde.client.dao.DaoException;
import com.jsonde.client.dao.DaoFactory;
import com.jsonde.client.domain.Method;
import com.jsonde.client.domain.MethodCallSummary;
import com.jsonde.gui.Main;
/**
 * 
 * @author admin
 *
 */
public class MethodCallProfilerView extends JPanel {

	/**
	 * 
	 * @author albertomadio
	 * MethodCallProfilerNode
	 */
    private static class MethodCallProfilerNode {

    	/**
    	 * id
    	 */
        final Long id;

        final String name;
        final long invocationCount;
        final String totalTime;

        List<MethodCallProfilerNode> childNodes;

        @Override
        public String toString() {
            return name;
        }

        /**
         * 
         * @param id
         * @param name
         * @param invocationCount
         * @param totalTime
         */
        private MethodCallProfilerNode(Long id, String name, long invocationCount, String totalTime) {
            this.id = id;
            this.name = name;
            this.invocationCount = invocationCount;
            this.totalTime = totalTime;
        }

        public List<MethodCallProfilerNode> getChildNodes() {

            if (null == childNodes) {

                try {
                  
                    List<MethodCallSummary> methodCallSummaries =
                            DaoFactory.getMethodCallSummaryDao().getCpuProfilerData(id);

                    childNodes = new ArrayList<MethodCallProfilerNode>(methodCallSummaries.size());

                    for (MethodCallSummary methodCallSummary : methodCallSummaries) {

                        Method method = DaoFactory.getMethodDao().get(methodCallSummary.getMethodId());

                        String name =
                                DaoFactory.getClazzDao().get(method.getClassId()).getName() +
                                        "." +
                                        method.getName();

                        long time = methodCallSummary.getExecutionTime();

                        StringBuilder timeStringBuilder = new StringBuilder();

                        time /= 1000L;

                        timeStringBuilder.append(time % 1000L + " ms");

                        timeStringBuilder.insert(0, time % 1000L + ".");

                        if (time > 0) {

                            timeStringBuilder.insert(0, time % 60L + " s ");
                            timeStringBuilder.insert(0, time % 60L + " m ");
                            timeStringBuilder.insert(0, time + " h ");
                            
                            time /= 60L;
                        }

                        MethodCallProfilerNode childNode = new MethodCallProfilerNode(
                                methodCallSummary.getId(),
                                name,
                                methodCallSummary.getInvocationCount(),
                                timeStringBuilder.toString()
                        );

                        childNodes.add(childNode);

                    }

                } catch (DaoException e) {
                    Main.getInstance().processException(e);
                }

            }

            return childNodes;
        }

    }

    public MethodCallProfilerView() {

        MethodCallProfilerNode rootNode = new MethodCallProfilerNode(
                null, "Method calls", 0, null
        );

        MethodCallProfilerViewTreeTableModel treeTableModel = new MethodCallProfilerViewTreeTableModel(rootNode);

        JTreeTable treeTable = new JTreeTable(treeTableModel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(treeTable);

        setLayout(new BorderLayout());

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * 
     * @author albertomadio
     * MethodCallProfilerViewTreeTableModel
     */
    private class MethodCallProfilerViewTreeTableModel extends AbstractTreeTableModel {

    	/**
    	 * 
    	 * @param root
    	 */
        private MethodCallProfilerViewTreeTableModel(MethodCallProfilerNode root) {
            super(root);
        }

        public MethodCallProfilerNode getChild(Object parent, int index) {
            MethodCallProfilerNode parentNode = (MethodCallProfilerNode) parent;
            return parentNode.getChildNodes().get(index);
        }

        public int getChildCount(Object parent) {
            MethodCallProfilerNode parentNode = (MethodCallProfilerNode) parent;
            return parentNode.getChildNodes().size();
        }

        public int getColumnCount() {
            return 3;
        }

        public String getColumnName(int i) {
        	
            switch (i) {
                case 0:
                    return "Name";
                case 1:
                    return "Invocation Count";
                default:
                    return "Invocation Time";
            }

        }

        public Class getColumnClass(int column) {
        	
            switch (column) {
                case 0:
                    return TreeTableModel.class;
                case 1:
                    return Long.class;
                default:
                    return String.class;
                  
            }
        }

        public String getValueAt(Object o, int i) {

            MethodCallProfilerNode node = (MethodCallProfilerNode) o;

            switch (i) {
                case 0:
                    return node.name;
                case 1:
                    String invocationCount1 = new String(); 
                    invocationCount1 = String.valueOf(node.invocationCount);
                    return invocationCount1;
                default:
                    return node.totalTime;
                    
            }

        }


    }

}
