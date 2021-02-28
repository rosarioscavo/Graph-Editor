package nl.rug.oop.grapheditor.controller.items.select;

import nl.rug.oop.grapheditor.controller.undoRedo.select.DeselectNodeUndoable;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.VK_D;

public class NodesDeselection extends JMenuItem {

    /**
     * Constructor for a menu item that allows the user to deselect all nodes
     *
     * @param graph the graph which the user is selecting from
     */
    public NodesDeselection(GraphModel graph) {
        super("Deselect all");
        setAccelerator(KeyStroke.getKeyStroke(
                VK_D, InputEvent.CTRL_DOWN_MASK));

        addActionListener(e -> {
            DeselectNodeUndoable deselectNodes = new DeselectNodeUndoable(graph);
            graph.addOperation(deselectNodes);
        });

    }

}
