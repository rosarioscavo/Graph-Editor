package nl.rug.oop.grapheditor.controller;

import nl.rug.oop.grapheditor.controller.items.Redo;
import nl.rug.oop.grapheditor.controller.items.Undo;
import nl.rug.oop.grapheditor.controller.items.add.AddEdge;
import nl.rug.oop.grapheditor.controller.items.add.AddNode;
import nl.rug.oop.grapheditor.controller.items.remove.RemoveEdge;
import nl.rug.oop.grapheditor.controller.items.remove.RemoveNode;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ToolMenu extends JToolBar implements PropertyChangeListener {
    private final JMenuItem removeNode;
    private final JMenuItem addEdge;
    private final JMenuItem removeEdge;
    private final JMenuItem undo;
    private final JMenuItem redo;

    private final GraphModel graph;

    /**
     * Constructor for the toolBar MenuItems
     */
    public ToolMenu(GraphModel graph) {
        super();
        this.graph = graph;
        graph.addPropertyChangeListener(this);
        setBorder(BorderFactory.createEmptyBorder());

        JMenuItem addNode = new AddNode(graph);
        addEdge = new AddEdge(graph);
        removeNode = new RemoveNode(graph);
        removeEdge = new RemoveEdge(graph);
        undo = new Undo(graph);
        redo = new Redo(graph);

        updateItemStatus(0, false);

        add(addNode);
        add(removeNode);
        add(addEdge);
        add(removeEdge);
        add(undo);
        add(redo);
    }

    /**
     * It activates and deactivates the buttons according to pre-conditions
     *
     * @param numNodesSelected number of nodes that are selected
     * @param isEdge           True if there is an edge between the two nodes. False otherwise
     */
    private void updateItemStatus(int numNodesSelected, boolean isEdge) {
        addEdge.setEnabled(numNodesSelected == 1);

        if (numNodesSelected == 2) {
            removeEdge.setEnabled(isEdge);
        } else
            removeEdge.setEnabled(false);

        undo.setEnabled(graph.isUndoable());
        redo.setEnabled(graph.canRedo());

        removeNode.setEnabled(numNodesSelected > 0);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "nodeSelected":
            case "addOperation":
            case "redo":
            case "undo":
                updateItemStatus((Integer) evt.getNewValue(), false);
                break;
            case "edgePresent":
                updateItemStatus(2, true);
                break;
            case "resetGraph":
            case "newEdgeCreation":
                updateItemStatus(0, false);
                break;
        }
    }
}
