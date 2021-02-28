package nl.rug.oop.grapheditor.controller.undoRedo.remove;

import nl.rug.oop.grapheditor.model.Edge;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class RemoveEdgeUndoable extends AbstractUndoableEdit {
    private final GraphModel graph;
    private final Edge edge;

    /**
     * Constructor that initialize the edge to be deleted
     *
     * @param graph Current graph
     */
    public RemoveEdgeUndoable(GraphModel graph) {
        this.graph = graph;
        edge = new Edge(graph.getStartPointEdge(), graph.getEndPointEdge());
        graph.removeEdge(edge);
    }

    /**
     * Method to undo this action
     *
     * @throws CannotUndoException if the action cannot be undone
     */
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        graph.addNewEdge(edge);
    }

    /**
     * Method to redo this action
     *
     * @throws CannotRedoException if the action cannot be redone
     */
    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        graph.removeEdge(edge);
    }
}
