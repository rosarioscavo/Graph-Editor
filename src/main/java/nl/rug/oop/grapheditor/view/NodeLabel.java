package nl.rug.oop.grapheditor.view;

import nl.rug.oop.grapheditor.controller.SelectionController;
import nl.rug.oop.grapheditor.model.Node;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class NodeLabel extends JLabel implements PropertyChangeListener {

    private final Node node;
    private Color background;

    /**
     * Constructor for node labels. It initialize the style parameters and the location of the node
     *
     * @param node the node to use for the label
     */
    public NodeLabel(Node node) {
        super();
        this.node = node;
        this.node.addPropertyChangeListener(this);
        setBorder(BorderFactory.createLineBorder(new Color(0x0A1621), 2));
        setBounds(node.getShape());
        setText(node.getName());

        nodeLabelSettings();

        SelectionController selectionController = new SelectionController(node);
        addMouseListener(selectionController);
        addMouseMotionListener(selectionController);
    }

    /**
     * Contains the basic settings of a NodeLabel and is called by the constructor to apply them to each new label
     */
    private void nodeLabelSettings() {
        setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        background = node.getBackgroundColor();
        setBackground(background);
        setVerticalAlignment(CENTER);
        setHorizontalAlignment(CENTER);
        setOpaque(true);
        setVisible(true);
    }

    /**
     * Method to update the shape of a Node Label which includes its position
     */
    private void updateShape() {
        setBounds(node.getShape());
    }

    /**
     * Method to update whether this Node Label is selected or not
     */
    private void updateSelected() {
        if (node.getSelected()) {
            setBackground(new Color(0xFFFF00));
        } else {
            setBackground(background);
        }
    }

    /**
     * It updates the visual information of the NodeLabel
     */
    public void updateLabel() {
        updateShape();
        updateSelected();
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("nameChanged".equals(evt.getPropertyName())) {
            setText((String) evt.getNewValue());
        }
    }

}
