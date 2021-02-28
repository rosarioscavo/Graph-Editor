package nl.rug.oop.grapheditor.controller.items.add;

import nl.rug.oop.grapheditor.controller.undoRedo.add.AddNodeUndoable;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.VK_N;

public class AddNode extends JMenuItem {

    /**
     * Constructor for a button that allows the user to add a node
     *
     * @param graph the graph the user is using
     */
    public AddNode(GraphModel graph) {
        super("Add Node");
        setAccelerator(KeyStroke.getKeyStroke(
                VK_N, InputEvent.CTRL_DOWN_MASK));

        addActionListener(e -> {
            AddNodeUndoable addNode = new AddNodeUndoable(graph);
            graph.addOperation(addNode);
        });

    }
}
