package nl.rug.oop.grapheditor.model;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Node Class encapsulates a String that is a name. The node has its shape that currently, it's a rectangle.
 * It contains also all the edge from itself or from other nodes(undirected graphs).
 */
public class Node {
    private String name;
    private Rectangle shape;
    private boolean isSelected;
    private Color backgroundColor;
    private final PropertyChangeSupport propertyChangeSupport;

    /**
     * Node constructor. Set a name, the shape with its position and dimension.
     * Initialize also the edges array.
     *
     * @param name      Name of the node
     * @param position  It is a Point object, it represents a position (x,y)
     * @param dimension It is a Dimension object, it encapsulates width and height
     */
    public Node(String name, Point position, Dimension dimension) {
        this.name = name;
        shape = new Rectangle(position, dimension);
        isSelected = false;
        propertyChangeSupport = new PropertyChangeSupport(this);
        backgroundColor = new Color(0x536271);
    }

    /**
     * Secondary constructor that creates a new node with a basic name at a basic point with a set dimension
     */
    public Node() {
        this("none", new Point(200, 200), new Dimension(150, 70));
    }

    /**
     * It changes the state of the node. If the state was true it will be false and vice versa
     */
    public void setSelected() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "setSelected", isSelected, !isSelected);
        isSelected = !isSelected;
        propertyChangeSupport.firePropertyChange(event);
    }


    /**
     * Function to set the shape of the node, primarily used to change the position of the node
     *
     * @param rect the new shape of the node as a rectangle
     */
    public void setShape(Rectangle rect) {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "setShape", shape, rect);
        shape = rect;
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * Setter for the name of the node
     *
     * @param name The new name of the node
     */
    public void setName(String name) {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "nameChanged", this.name, name);
        this.name = name;
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * Function to get whether the node has been selected by the user
     *
     * @return isSelected, a boolean value showing whether the node is selected or not
     */
    public boolean getSelected() {
        return isSelected;
    }

    public String getName() {
        return name;
    }

    /**
     * Getter for the shape and size of the node
     *
     * @return a rectangle with the dimensions of the node
     */
    public Rectangle getShape() {
        return shape;
    }

    /**
     * Getter for the background color of the node
     *
     * @return background color of the node
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Setter for the background color of the node
     *
     * @param color the new background of the node
     */
    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }


    /**
     * Moves the center of the node to the x and y coordinates
     */
    public void move(int x, int y) {
        setShape(new Rectangle(x + shape.x - shape.width / 2, y + shape.y - shape.height / 2, shape.width, shape.height));
    }

    /**
     * Allows adding property change support listeners to nodes
     *
     * @param listener the panel we want to listen
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
