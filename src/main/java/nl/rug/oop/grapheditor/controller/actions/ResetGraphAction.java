package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ResetGraphAction extends AbstractAction {
    private final GraphModel graph;

    /**
     * Constructor for the reset graph action
     *
     * @param graph the graph being reset
     */
    public ResetGraphAction(GraphModel graph) {
        super("Create a new graph");
        this.graph = graph;
    }

    /**
     * Action performed event, resets the graph
     *
     * @param e the event that triggered the action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        graph.resetGraph();
    }
}