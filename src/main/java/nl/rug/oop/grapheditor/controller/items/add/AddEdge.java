package nl.rug.oop.grapheditor.controller.items.add;

import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;

public class AddEdge extends JMenuItem {

    /**
     * Constructor for a button that allows the user to add an edge
     *
     * @param graph the graph the user is using
     */
    public AddEdge(GraphModel graph) {
        super("Add Edge");
        addActionListener(e -> graph.setAddingNewEdge());
    }
}
