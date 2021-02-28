package nl.rug.oop.grapheditor.controller.items.io;

import nl.rug.oop.grapheditor.controller.actions.LoadGraphAction;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.VK_L;

public class LoadGraph extends JMenuItem {

    /**
     * Constructor for a menu item that allows the user to load a graph from a file
     *
     * @param graph the graph that will be overwritten by the new graph from file
     */
    public LoadGraph(GraphModel graph) {
        super(new LoadGraphAction(graph));
        setAccelerator(KeyStroke.getKeyStroke(
                VK_L, InputEvent.CTRL_DOWN_MASK));
    }
}
