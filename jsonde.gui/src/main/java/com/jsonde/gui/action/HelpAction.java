package com.jsonde.gui.action;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import com.jsonde.gui.ApplicationUserInterface;
import com.jsonde.gui.Main;
import com.jsonde.gui.sdedit.SdEditUIAdapter;

import net.sf.sdedit.icons.Icons;
/**
 * 
 * @author admin
 *
 */
public class HelpAction extends AbstractAction {

    private ApplicationUserInterface applicationUserInterface;

    public HelpAction(SdEditUIAdapter sdEditUIAdapter) {
        this.applicationUserInterface = sdEditUIAdapter;
    }

    {
        putValue(Action.SMALL_ICON,
                new ImageIcon(
                        Icons.class.getResource("help.png")
                ));
        putValue(Action.NAME, "Help");
        putValue(Action.SHORT_DESCRIPTION, "Open jSonde Help");
    }

    public void actionPerformed(ActionEvent e) {

        JComponent helpPane = createHTMLPane();

        applicationUserInterface.addTab(helpPane, "Help");

    }

    /**
     * 
     * @return
     */
    private JScrollPane createHTMLPane() {

        HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        HTMLDocument htmlDocument =
                (HTMLDocument) htmlEditorKit.createDefaultDocument();

        URL baseURL = getClass().getClassLoader().getResource(("help/help.html"));
        htmlDocument.setBase(baseURL);

        JEditorPane editor = new JEditorPane();
        editor.setEditable(false);

        editor.setEditorKit(htmlEditorKit);
        try {
            String resPath = "help/help.html";
            editor.read(getClass().getClassLoader().getResourceAsStream(resPath), htmlDocument);
        }
        catch (IOException e) {
            Main.getInstance().processException(e);
        }

        return new JScrollPane(editor);
    }

}