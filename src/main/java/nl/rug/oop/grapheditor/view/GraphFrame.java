package nl.rug.oop.grapheditor.view;

import nl.rug.oop.grapheditor.controller.MenuBar;
import nl.rug.oop.grapheditor.controller.ToolMenu;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.*;

public class GraphFrame extends JFrame {

    /**
     * Default Frame constructor for the graph editor
     *
     * @param graph Current graph
     */
    public GraphFrame(GraphModel graph) {
        super("Graph Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1440, 800));
        add(new ToolMenu(graph), BorderLayout.NORTH);
        setJMenuBar(new MenuBar(graph, this));
        add(new GraphPanel(graph));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
