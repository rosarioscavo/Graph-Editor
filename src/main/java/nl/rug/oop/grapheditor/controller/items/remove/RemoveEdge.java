package nl.rug.oop.grapheditor.controller.items.remove;

import nl.rug.oop.grapheditor.controller.undoRedo.remove.RemoveEdgeUndoable;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;

public class RemoveEdge extends JMenuItem {

    /**
     * Constructor for a button that allows the user to remove an edge
     *
     * @param graph the graph the user is editing
     */
    public RemoveEdge(GraphModel graph) {
        super("Remove Edge");

        addActionListener(e -> {
            RemoveEdgeUndoable removeEdge = new RemoveEdgeUndoable(graph);
            graph.addOperation(removeEdge);
        });
    }
}
