package nl.rug.oop.grapheditor.controller.items;

import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.VK_Y;

public class Redo extends JMenuItem {

    /**
     * Constructor for a menu item that allows the user to redo their actions
     *
     * @param graph the graph that will have edits redone on it
     */
    public Redo(GraphModel graph) {
        super("Redo");
        setAccelerator(KeyStroke.getKeyStroke(
                VK_Y, InputEvent.CTRL_DOWN_MASK));

        addActionListener(e -> graph.redo());

    }

}
