package nl.rug.oop.grapheditor.controller.items.remove;

import nl.rug.oop.grapheditor.controller.undoRedo.remove.RemoveNodeUndoable;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;

import static java.awt.event.KeyEvent.VK_DELETE;

public class RemoveNode extends JMenuItem {

    /**
     * Constructor for a button that allows the user to remove a node
     *
     * @param graph the graph the user is editing
     */
    public RemoveNode(GraphModel graph) {
        super("Remove Node");
        setAccelerator(KeyStroke.getKeyStroke(
                VK_DELETE, 0));

        addActionListener(e -> {
            RemoveNodeUndoable removeNode = new RemoveNodeUndoable(graph);
            graph.addOperation(removeNode);
        });
    }
}
