package nl.rug.oop.grapheditor.controller.undoRedo.add;

import nl.rug.oop.grapheditor.model.Edge;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.Node;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class AddEdgeUndoable extends AbstractUndoableEdit {
    private final GraphModel graph;
    private final Edge edge;

    /**
     * Constructor that initialize the new edge with endpoint the nodeB
     *
     * @param graph Current graph
     * @param nodeB the endpoint of the edge
     */
    public AddEdgeUndoable(GraphModel graph, Node nodeB) {
        this.graph = graph;
        edge = new Edge(graph.getStartPointEdge(), nodeB);
        graph.addNewEdge(edge);
    }

    /**
     * Method to undo this action
     *
     * @throws CannotUndoException if the action cannot be undone
     */
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        graph.removeEdge(edge);
    }

    /**
     * Method to redo this action
     *
     * @throws CannotRedoException if the action cannot be redone
     */
    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        graph.addNewEdge(edge);
    }
}
