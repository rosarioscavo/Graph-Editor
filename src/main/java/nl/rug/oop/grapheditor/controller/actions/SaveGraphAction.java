package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveGraphAction extends AbstractAction {
    private final GraphModel graph;

    /**
     * Constructor for the save graph action
     *
     * @param graph the graph being saved
     */
    public SaveGraphAction(GraphModel graph) {
        super("Save Graph");
        this.graph = graph;
    }

    /**
     * Action performed event, saves the graph
     *
     * @param e the event that triggered the action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        graph.saveGraph();
    }
}
