package nl.rug.oop.grapheditor.controller.items;

import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.VK_Z;

public class Undo extends JMenuItem {

    /**
     * Constructor for a menu item that allows the user to undo their actions
     *
     * @param graph the graph that will have edits undone on it
     */
    public Undo(GraphModel graph) {
        super("Undo");
        setAccelerator(KeyStroke.getKeyStroke(
                VK_Z, InputEvent.CTRL_DOWN_MASK));

        addActionListener(e -> graph.undo());

    }

}
