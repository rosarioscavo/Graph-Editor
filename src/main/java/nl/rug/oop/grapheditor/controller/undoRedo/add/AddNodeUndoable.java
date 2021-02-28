package nl.rug.oop.grapheditor.controller.undoRedo.add;

import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.Node;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class AddNodeUndoable extends AbstractUndoableEdit {
    private final GraphModel graph;
    private final Node node;

    /**
     * Constructor that initializes the new node and adds it to the graph
     *
     * @param graph Current graph
     */
    public AddNodeUndoable(GraphModel graph) {
        this.graph = graph;
        node = new Node();
        graph.addNewNode(node);
    }

    /**
     * Method to undo this action
     *
     * @throws CannotUndoException if the action cannot be undone
     */
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        graph.removeNode(node);
    }

    /**
     * Method to redo this action
     *
     * @throws CannotRedoException if the action cannot be redone
     */
    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        graph.addNewNode(node);
    }
}
