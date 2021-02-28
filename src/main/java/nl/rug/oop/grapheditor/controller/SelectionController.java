package nl.rug.oop.grapheditor.controller;

import nl.rug.oop.grapheditor.controller.undoRedo.MovingNodeUndoable;
import nl.rug.oop.grapheditor.controller.undoRedo.RenameNodeUndoable;
import nl.rug.oop.grapheditor.controller.undoRedo.add.AddEdgeUndoable;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SelectionController extends MouseAdapter {


    private final Node node;
    private static GraphModel graph;
    private ArrayList<Rectangle> coordinates;
    private boolean startDragging;

    /**
     * Constructor for a mouseAdapter that is on a Node usually passed by a NodeLabel
     *
     * @param node the node that the adapter stores and can edit
     */
    public SelectionController(Node node) {
        this.node = node;
        startDragging = false;
    }

    /**
     * Check whenever the mouse is clicked. If the mouse is clicked only once,
     * it makes the node selected, if it's clicked twice, it allows to change the name of the node
     *
     * @param e mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            graph.selectedNode(node);
            RenameNodeUndoable renameNodeUndoable;

            String inputName = JOptionPane.showInputDialog("Type the new name for the node:");

            if (inputName != null && !inputName.equals("")) {
                renameNodeUndoable = new RenameNodeUndoable(node, inputName);
                graph.addOperation(renameNodeUndoable);
            }
        } else if (graph.isAddingNewEdge()) {
            AddEdgeUndoable addEdge = new AddEdgeUndoable(graph, node);
            graph.addOperation(addEdge);
        } else graph.selectedNode(node);
    }

    /**
     * It drags the centre of the nodes to the centre mouse pointer
     *
     * @param e mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if (startDragging && node.getSelected()) {
            ArrayList<Rectangle> newCoordinates = graph.getSelectedNodesShape();
            MovingNodeUndoable movingNode = new MovingNodeUndoable(graph, coordinates, newCoordinates);
            graph.addOperation(movingNode);

            startDragging = false;
        } else if (startDragging && graph.getNumSelectedNodes() == 0) {
            ArrayList<Rectangle> newCoordinates = new ArrayList<>();
            newCoordinates.add(node.getShape());
            MovingNodeUndoable movingNode = new MovingNodeUndoable(graph, node, coordinates, newCoordinates);
            graph.addOperation(movingNode);

            startDragging = false;
        }
    }

    /**
     * It drags the centre of the nodes to the centre mouse pointer
     *
     * @param e mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        //save only one time the initial coordinates
        if (!startDragging) {
            startDragging = true;
            if (graph.getNumSelectedNodes() == 0) {
                coordinates = new ArrayList<>();
                coordinates.add(node.getShape());
            } else {
                coordinates = graph.getSelectedNodesShape();
            }
        }
        if (node.getSelected()) {
            graph.moveSelectedNodes(graph.getSelectedNodes(), e.getX(), e.getY());
        } else if (graph.getNumSelectedNodes() == 0) {
            graph.moveNode(node, e.getX(), e.getY());
        }
    }

    /**
     * Setter for the graph stored by the selection controller
     *
     * @param graphModel new graph to be used
     */
    public static void setGraph(GraphModel graphModel) {
        graph = graphModel;
    }
}
