package nl.rug.oop.grapheditor.controller;

import nl.rug.oop.grapheditor.controller.undoRedo.ColorChangeUndoable;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.*;

public class NodeColorer extends JMenuItem {

    private Color color;

    /**
     * Constructor for the NodeColorer, which allows users to color all the nodes they have selected
     *
     * @param graph the graph which the user is selecting from
     * @param frame the frame which the JColorChooser dialog will use as its parent component
     */
    public NodeColorer(GraphModel graph, JFrame frame) {
        super("Change Selected Nodes Color");
        addActionListener(e -> {
            if (graph.getNumSelectedNodes() == 0) {
                return;
            }
            Color initialColor = graph.getSelectedNodes().get(0).getBackgroundColor();
            color = JColorChooser.showDialog(frame, "Select a color", initialColor);
            if (color != null) {
                ColorChangeUndoable changeColor = new ColorChangeUndoable(graph, initialColor, color);
                graph.addOperation(changeColor);
            }
        });
    }
}
