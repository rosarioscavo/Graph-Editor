package nl.rug.oop.grapheditor.view;

import nl.rug.oop.grapheditor.controller.SelectionController;
import nl.rug.oop.grapheditor.model.Edge;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.Node;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import static java.lang.Math.*;

public class GraphPanel extends JPanel implements PropertyChangeListener {

    private final Color BACKGROUND_COLOR = new Color(0x3E4C5E);
    private ArrayList<NodeLabel> nodes;
    private final GraphModel graph;

    /**
     * Default Panel Constructor for the graph editor,
     *
     * @param graph Current graph
     */
    public GraphPanel(GraphModel graph) {
        super();
        graph.addPropertyChangeListener(this);
        this.graph = graph;
        SelectionController.setGraph(graph);
        initPanel();

        panelSettings();
    }

    /**
     * Contains the basic settings of the graph panel and is called by the constructor to apply the settings
     */
    private void panelSettings() {
        setBackground(BACKGROUND_COLOR);
        setLayout(null);
        setVisible(true);
        setOpaque(true);
    }

    /**
     * Method to add a Node Label to the panel
     *
     * @param node the node to create the node label
     */
    private void addNodePanel(Node node) {
        node.addPropertyChangeListener(this);
        nodes.add(new NodeLabel(node));
    }

    /**
     * Initialises the node labels in the panel by resetting the nodes arraylist
     */
    private void initPanel() {
        nodes = new ArrayList<>();
        for (Node node : graph.getNodes()) {
            addNodePanel(node);
        }
    }

    /**
     * Method to draw Edges that have an arrow showing direction
     *
     * @param g graphics needed to paint components
     */
    private void drawEdges(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(new Color(0x2B2B2B));

        for (Edge edge : graph.getEdges()) {
            int nodeX = edge.getEndpoint1().getShape().x + edge.getEndpoint1().getShape().width / 2;
            int nodeY = edge.getEndpoint1().getShape().y + edge.getEndpoint1().getShape().height / 2;
            int endNodeX = edge.getEndpoint2().getShape().x + edge.getEndpoint2().getShape().width / 2;
            int endNodeY = edge.getEndpoint2().getShape().y + edge.getEndpoint2().getShape().height / 2;
            g2d.drawLine(nodeX, nodeY, endNodeX, endNodeY);
            drawArrow(g2d, nodeX, nodeY, endNodeX, endNodeY);
        }
    }

    /**
     * Draws an edge to the cursor from a starting node and repaints until you end the node
     *
     * @param g a set of graphics to use to draw
     */
    private void drawNewEdge(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(new Color(0x2B2B2B));

        Node startingNode = graph.getStartPointEdge();
        Point cursor = MouseInfo.getPointerInfo().getLocation();

        int nodeX = startingNode.getShape().x + startingNode.getShape().width / 2;
        int nodeY = startingNode.getShape().y + startingNode.getShape().height / 2;
        int endX = cursor.x - this.getLocationOnScreen().x;
        int endY = cursor.y - this.getLocationOnScreen().y;
        g2d.drawLine(nodeX, nodeY, endX, endY);
        drawArrow(g2d, nodeX, nodeY, endX, endY);
        repaint();
    }

    /**
     * Method to add an arrow to an edge to show direction. Arrow does not work yet
     */
    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(new Color(0x0A1621));
        float dx = x2 - x1;
        float dy = y2 - y1;
        int midX = (int) (dx / 2) + x1;
        int midY = (int) (dy / 2) + y1;
        if (dx < 0) {
            angleLine(g2d, midX, midY, (float) atan(dy / dx) + (float) PI / 6, 15);
            angleLine(g2d, midX, midY, (float) atan(dy / dx) - (float) PI / 6, 15);
        } else {
            angleLine(g2d, midX, midY, (float) (atan(dy / dx) + (float) PI / 6), -15);
            angleLine(g2d, midX, midY, (float) (atan(dy / dx) - (float) PI / 6), -15);
        }
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(new Color(0x2B2B2B));
    }

    /**
     * Method to draw a line at an angle
     *
     * @param g2d   2d Graphics used to draw the line
     * @param x     initial x coordinate of the line
     * @param y     initial y coordinate of the line
     * @param angle the degree of the line in radians at which the line is drawn
     * @param len   the length of the line
     */
    private void angleLine(Graphics2D g2d, int x, int y, float angle, float len) {
        float dx = xChange(angle, len);
        float dy = yChange(angle, len);
        g2d.drawLine(x, y, (int) (x + dx), (int) (y + dy));
    }

    /**
     * Method to get the x coordinate of the end of an angled line
     *
     * @param angle the angle the line is at
     * @param len   how long it should be
     * @return an int that specifies the change in the x value of a line going at that angle with that length
     */
    private int xChange(float angle, float len) {
        return (int) (cos(angle) * len);
    }

    /**
     * Method to get the y coordinate of the end of an angled line
     *
     * @param angle the angle the line is at
     * @param len   how long it should be
     * @return an int that specifies the change in the y value of a line going at that angle with that length
     */
    private int yChange(float angle, float len) {
        return (int) (sin(angle) * len);
    }

    /**
     * Creates a NodeLabel for each node in the graph and then adds them with a null layout.
     */
    private void drawNodes() {
        for (NodeLabel node : nodes) {
            node.updateLabel();
            add(node);
        }
    }

    /**
     * Overridden paint component command that paints the panel then draws edges then nodes
     *
     * @param g graphics set to use to draw
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graph.isAddingNewEdge()) {
            drawNewEdge(g);
        }
        drawEdges(g);
        drawNodes();
    }

    /**
     * This method gets called when a new event in the graph has happened is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "changeNodesColor":
            case "addNode":
            case "removeNode":
            case "resetGraph":
            case "loadGraph":
                initPanel();
                break;
        }
        removeAll();
        revalidate();
        repaint();
    }
}
