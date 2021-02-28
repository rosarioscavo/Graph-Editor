package nl.rug.oop.grapheditor.controller.undoRedo;

import nl.rug.oop.grapheditor.model.Node;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class RenameNodeUndoable extends AbstractUndoableEdit {
    private final Node node;
    private final String newName;
    private final String oldName;

    /**
     * Constructor that renames a node
     *
     * @param node    the node to be renamed
     * @param newName the new name of the node
     */
    public RenameNodeUndoable(Node node, String newName) {
        this.node = node;
        this.newName = newName;
        this.oldName = node.getName();

        node.setName(newName);
    }

    /**
     * Method to undo this action
     *
     * @throws CannotUndoException if the action cannot be undone
     */
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        node.setName(oldName);
    }

    /**
     * Method to redo this action
     *
     * @throws CannotRedoException if the action cannot be redone
     */
    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        node.setName(newName);
    }
}
