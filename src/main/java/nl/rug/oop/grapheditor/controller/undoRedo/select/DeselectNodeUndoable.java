package nl.rug.oop.grapheditor.controller.undoRedo.select;

import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;


public class DeselectNodeUndoable extends AbstractUndoableEdit {
    private final GraphModel graph;

    /**
     * Constructor for the undoable deselect all nodes action
     *
     * @param graph the graph the user is selecting from
     */
    public DeselectNodeUndoable(GraphModel graph) {
        this.graph = graph;
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
        graph.selectAllNodes();
    }

    /**
     * Method to redo this action
     *
     * @throws CannotRedoException if the action cannot be redone
     */
    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        graph.deselectNodes();
    }
}
