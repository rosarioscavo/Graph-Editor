package nl.rug.oop.grapheditor.controller.items.io;

import nl.rug.oop.grapheditor.controller.actions.SaveGraphAction;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.VK_S;

public class SaveGraph extends JMenuItem {

    /**
     * Constructor for a menu item that allows the user to save the graph to a file
     *
     * @param graph the graph that will be saved
     */
    public SaveGraph(GraphModel graph) {
        super(new SaveGraphAction(graph));
        setAccelerator(KeyStroke.getKeyStroke(
                VK_S, InputEvent.CTRL_DOWN_MASK));
    }
}
