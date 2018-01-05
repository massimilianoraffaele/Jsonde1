package com.jsonde.gui.reports.custom;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import com.jsonde.client.dao.DaoException;
import com.jsonde.client.dao.DaoFactory;
import com.jsonde.client.domain.CodeSource;
import com.jsonde.gui.Main;
/**
 * 
 * @author admin
 *
 */
public class DependencyReport implements ReportGenerator {

    public JComponent generateReport() {

        Map<Long, Set<Long>> dependencies = DaoFactory.getReportDao().getDependencies();

        JTree dependencyTree = new JTree();

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Dependency tree");
        DefaultTreeModel dependencyTreeModel = new DefaultTreeModel(rootNode);

        int i = 0;

        for (Long codeSourceId : dependencies.keySet()) {

            try {

                CodeSource codeSource = DaoFactory.getCodeSourceDao().get(codeSourceId);

                MutableTreeNode dependencyNode = new DefaultMutableTreeNode(codeSource.getSource());
                dependencyTreeModel.insertNodeInto(dependencyNode, rootNode, i);

                createTree(
                        dependencies,
                        dependencyTreeModel,
                        dependencyNode,
                        dependencies.get(codeSourceId),
                        new LinkedHashSet<Long>(Arrays.asList(codeSourceId)));

            } catch (DaoException e) {
                Main.getInstance().processException(e);
            }

            i++;

        }

        dependencyTree.setModel(dependencyTreeModel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(dependencyTree);

        return scrollPane;
    }

    /**
     * 
     * @param dependencies
     * @param dependencyTreeModel
     * @param parent
     * @param codeSourceIds
     * @param shownSourceIds
     */
    private void createTree(
            Map<Long, Set<Long>> dependencies,
            DefaultTreeModel dependencyTreeModel,
            MutableTreeNode parent,
            Set<Long> codeSourceIds,
            Set<Long> shownSourceIds) {

        int i = 0;

        for (Long codeSourceId : codeSourceIds) {

            try {

                CodeSource codeSource = DaoFactory.getCodeSourceDao().get(codeSourceId);

                MutableTreeNode dependencyNode = new DefaultMutableTreeNode(codeSource.getSource());
                dependencyTreeModel.insertNodeInto(dependencyNode, parent, i);

                if (!shownSourceIds.contains(codeSourceId)) {
                    shownSourceIds.add(codeSourceId);
                    createTree(dependencies, dependencyTreeModel, dependencyNode, dependencies.get(codeSourceId), shownSourceIds);
                }

            } catch (DaoException e) {
                Main.getInstance().processException(e);
            }

            i++;

        }

    }

}