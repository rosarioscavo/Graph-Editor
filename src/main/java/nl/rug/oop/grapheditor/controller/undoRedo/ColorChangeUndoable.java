package nl.rug.oop.grapheditor.controller.undoRedo;

import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.Node;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.*;
import java.util.ArrayList;

public class ColorChangeUndoable extends AbstractUndoableEdit {
    private final GraphModel graph;
    private final ArrayList<Node> selectedNodes;
    private final Color oldColor;
    private final Color newColor;


    /**
     * Constructor that changes the colors of all the selected nodes
     *
     * @param graph    the graph that is being edited
     * @param oldColor the old color of the nodes
     * @param newColor the new color of the nodes
     */
    public ColorChangeUndoable(GraphModel graph, Color oldColor, Color newColor) {
        this.graph = graph;

        this.oldColor = oldColor;
        this.newColor = newColor;

        selectedNodes = graph.getSelectedNodes();
        graph.changeSelectedNodesColor(selectedNodes, newColor);
        graph.deselectNodes();
    }

    /**
     * Method to undo this action
     *
     * @throws CannotUndoException if the action cannot be undone
     */
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        graph.changeSelectedNodesColor(selectedNodes, oldColor);
    }

    /**
     * Method to redo this action
     *
     * @throws CannotRedoException if the action cannot be redone
     */
    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        graph.changeSelectedNodesColor(selectedNodes, newColor);
        graph.deselectNodes();
    }
}
