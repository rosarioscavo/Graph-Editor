package nl.rug.oop.grapheditor.controller;

import nl.rug.oop.grapheditor.controller.items.Redo;
import nl.rug.oop.grapheditor.controller.items.Undo;
import nl.rug.oop.grapheditor.controller.items.io.LoadGraph;
import nl.rug.oop.grapheditor.controller.items.io.ResetGraph;
import nl.rug.oop.grapheditor.controller.items.io.SaveGraph;
import nl.rug.oop.grapheditor.controller.items.select.NodesDeselection;
import nl.rug.oop.grapheditor.controller.items.select.NodesSelection;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MenuBar extends JMenuBar implements PropertyChangeListener {
    private final JMenuItem undo;
    private final JMenuItem redo;
    private final JMenuItem select;
    private final JMenuItem deselect;
    private final GraphModel graph;

    /**
     * A basic menu bar that allows for saving and loading a graph
     *
     * @param graph the graph model that is edited by these actions
     * @param frame the frame of the mnu bar
     */
    public MenuBar(GraphModel graph, JFrame frame) {
        super();
        this.graph = graph;
        graph.addPropertyChangeListener(this);
        undo = new Undo(graph);
        redo = new Redo(graph);
        select = new NodesSelection(graph);
        deselect = new NodesDeselection(graph);

        NodeColorer colorChooser = new NodeColorer(graph, frame);

        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");

        file.add(new SaveGraph(graph));
        file.add(new LoadGraph(graph));
        file.add(new ResetGraph(graph));

        edit.add(select);
        edit.add(deselect);
        edit.add(colorChooser);
        edit.add(undo);
        edit.add(redo);

        updateItemStatus(0);

        add(file);
        add(edit);
    }

    /**
     * It activates and deactivates the buttons according to pre-conditions
     */
    private void updateItemStatus(Integer numSelectedNodes) {
        select.setEnabled(numSelectedNodes != graph.getNumNodes());
        deselect.setEnabled(numSelectedNodes != 0);
        undo.setEnabled(graph.isUndoable());
        redo.setEnabled(graph.canRedo());
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
                updateItemStatus((Integer) evt.getNewValue());
                break;
            case "loadGraph":
                updateItemStatus(0);
                break;
        }
    }
}
