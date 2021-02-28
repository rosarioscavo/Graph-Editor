package nl.rug.oop.grapheditor.controller.undoRedo.remove;

import nl.rug.oop.grapheditor.model.Edge;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.Node;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RemoveNodeUndoable extends AbstractUndoableEdit {
    private final GraphModel graph;
    private final ArrayList<Node> selectedNodes;
    private final ArrayList<Edge> edgeSelectedNodes;
    private final int numNodesSelected;

    /**
     * Constructor that initialises the node to be removed
     *
     * @param graph the graph the user is editing
     */
    public RemoveNodeUndoable(GraphModel graph) {
        this.graph = graph;

        numNodesSelected = graph.getNumSelectedNodes();
        selectedNodes = graph.getNodes().stream().filter(Node::getSelected).collect(Collectors.toCollection(ArrayList::new));
        edgeSelectedNodes = graph.edgesSelectedNodes();

        graph.removeSelectedNodes(selectedNodes);
    }

    /**
     * Method to undo this action
     *
     * @throws CannotUndoException if the action cannot be undone
     */
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        graph.setNumNodesSelected(numNodesSelected);
        selectedNodes.forEach(graph::addNewNode);
        edgeSelectedNodes.forEach(graph::addEdge);
    }

    /**
     * Method to redo this action
     *
     * @throws CannotRedoException if the action cannot be redone
     */
    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        graph.removeSelectedNodes(selectedNodes);
    }

}
