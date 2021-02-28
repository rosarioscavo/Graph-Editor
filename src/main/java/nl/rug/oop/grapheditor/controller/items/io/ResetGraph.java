package nl.rug.oop.grapheditor.controller.items.io;

import nl.rug.oop.grapheditor.controller.actions.ResetGraphAction;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;

public class ResetGraph extends JMenuItem {

    /**
     * Constructor for a menu item that allows the user to reset the graph
     *
     * @param graph the graph that will be reset
     */
    public ResetGraph(GraphModel graph) {
        super(new ResetGraphAction(graph));
    }
}
