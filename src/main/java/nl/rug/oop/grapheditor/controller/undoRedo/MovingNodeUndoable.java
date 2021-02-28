package nl.rug.oop.grapheditor.controller.undoRedo;

import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.Node;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.*;
import java.util.ArrayList;

public class MovingNodeUndoable extends AbstractUndoableEdit {
    private final GraphModel graph;
    private ArrayList<Node> selectedNodes;
    private final ArrayList<Rectangle> oldCoordinates;
    private final ArrayList<Rectangle> newCoordinates;
    private final int numNodesSelected;

    /**
     * Constructor that initialize the edge to be deleted
     *
     * @param graph Current graph
     */
    public MovingNodeUndoable(GraphModel graph, ArrayList<Rectangle> oldCoordinates, ArrayList<Rectangle> newCoordinates) {
        this.graph = graph;

        this.oldCoordinates = oldCoordinates;
        this.newCoordinates = newCoordinates;

        numNodesSelected = graph.getNumSelectedNodes();
        selectedNodes = graph.getSelectedNodes();

    }

    /**
     * Constructor that initializes moving an unselected node
     *
     * @param graph          Current graph
     * @param unselectedNode an unselected node being moved
     * @param oldCoordinates old position
     * @param newCoordinates new position
     */
    public MovingNodeUndoable(GraphModel graph, Node unselectedNode, ArrayList<Rectangle> oldCoordinates, ArrayList<Rectangle> newCoordinates) {
        this.graph = graph;

        this.oldCoordinates = oldCoordinates;
        this.newCoordinates = newCoordinates;

        numNodesSelected = graph.getNumSelectedNodes();
        if (numNodesSelected == 0) {
            selectedNodes = new ArrayList<>();
            selectedNodes.add(unselectedNode);
        }
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
        graph.resetShapeSelectedNodes(selectedNodes, oldCoordinates);
    }

    /**
     * Method to redo this action
     *
     * @throws CannotRedoException if the action cannot be redone
     */
    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        graph.resetShapeSelectedNodes(selectedNodes, newCoordinates);
    }
}