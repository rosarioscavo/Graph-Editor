package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoadGraphAction extends AbstractAction {
    private final GraphModel graph;

    /**
     * Constructor for the load graph action
     *
     * @param graph the graph object that will have a graph loaded from a file over it
     */
    public LoadGraphAction(GraphModel graph) {
        super("Load Graph");
        this.graph = graph;
    }

    /**
     * Action performed event, loads the graph from a file
     *
     * @param e the event that triggered the action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        graph.loadFromFile();
    }
}
