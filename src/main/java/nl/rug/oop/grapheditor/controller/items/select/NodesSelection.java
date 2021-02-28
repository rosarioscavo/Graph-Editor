package nl.rug.oop.grapheditor.controller.items.select;

import nl.rug.oop.grapheditor.controller.undoRedo.select.SelectNodeUndoable;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.VK_A;

public class NodesSelection extends JMenuItem {

    /**
     * Constructor for a menu item that allows the user to select all nodes
     *
     * @param graph the graph which the user is selecting from
     */
    public NodesSelection(GraphModel graph) {
        super("Select all");
        setAccelerator(KeyStroke.getKeyStroke(
                VK_A, InputEvent.CTRL_DOWN_MASK));

        addActionListener(e -> {
            SelectNodeUndoable selectNodes = new SelectNodeUndoable(graph);
            graph.addOperation(selectNodes);
        });

    }

}
