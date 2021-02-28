package nl.rug.oop.grapheditor.model;

import nl.rug.oop.grapheditor.model.io.GraphFileFormat;
import nl.rug.oop.grapheditor.model.io.GraphLoader;
import nl.rug.oop.grapheditor.model.io.GraphSaver;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * GraphModel contains all the information about the current graph and all the methods in order
 * to add more nodes or edit the current state. Edges are added in order to be a directed graph.
 */
public class GraphModel {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private int numNodesSelected;
    private Node nodeA;
    private boolean isAddingNewEdge;
    private UndoManager undoManager;
    private final PropertyChangeSupport propertyChangeSupport;

    /**
     * The constructor simply initialize the nodes array.
     */
    public GraphModel() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        numNodesSelected = 0;
        isAddingNewEdge = false;
        undoManager = new UndoManager();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Constructor used when loading a graph from a file
     *
     * @param path the file path of the graph
     */
    public GraphModel(String path) {
        this();
        loadFromFile(path);
    }

    /**
     * It resets the current graph to load properly a new graph
     */
    public void resetGraph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        numNodesSelected = 0;
        isAddingNewEdge = false;
        undoManager = new UndoManager();
        PropertyChangeEvent event = new PropertyChangeEvent(this, "resetGraph", null, null);
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * Adds an undoable edit to the undoManager
     *
     * @param operation the undoable edit that is being added
     */
    public void addOperation(UndoableEdit operation) {
        undoManager.addEdit(operation);
        PropertyChangeEvent event = new PropertyChangeEvent(this, "addOperation", null, numNodesSelected);
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * Method to check if exist an action that can be undone at the moment
     *
     * @return boolean if it can be undone
     */
    public boolean isUndoable() {
        return undoManager.canUndo() && !isAddingNewEdge;
    }

    /**
     * Method to check if exist an action that can be redone at the moment
     *
     * @return boolean if it can be redone
     */
    public boolean canRedo() {
        return undoManager.canRedo() && !isAddingNewEdge;
    }

    /**
     * Will undo the last action added to the undo manager if it is undoable
     */
    public void undo() {
        deselectNodes();

        PropertyChangeEvent event = new PropertyChangeEvent(this, "undo", null, numNodesSelected);
        propertyChangeSupport.firePropertyChange(event);

        if (isUndoable()) {
            undoManager.undo();
        }
    }

    /**
     * Will redo the last action that was undone by the undo command
     */
    public void redo() {

        if (canRedo()) {
            undoManager.redo();
        }

        PropertyChangeEvent event = new PropertyChangeEvent(this, "redo", null, numNodesSelected);
        propertyChangeSupport.firePropertyChange(event);
    }


    /**
     * Method to add a new node to the graph.
     *
     * @param position  it represents a position
     * @param dimension it encapsulates height and width
     * @param name      Name of the node
     */
    public void addNode(Point position, Dimension dimension, String name) {
        nodes.add(new Node(name, position, dimension));
    }

    /**
     * Method to add a node that also fires a property change event
     *
     * @param node node to be added
     */
    public void addNewNode(Node node) {
        nodes.add(node);
        PropertyChangeEvent event = new PropertyChangeEvent(this, "addNode", null, node);
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * Method to add a new edge to the graph from a file.
     *
     * @param index1 Index of the start node of the edge.
     *               It indicate the position inside the nodes array.
     * @param index2 Index of the end node of the edge.
     *               It indicate the position inside the nodes array.
     */
    public void addEdge(int index1, int index2) {
        Edge newEdge = new Edge(nodes.get(index1), nodes.get(index2));
        edges.add(newEdge);
    }

    /**
     * It adds the edge to the graph
     *
     * @param edge edge that needs to be added to the graph
     */
    public void addEdge(Edge edge) {
        if (edges.contains(edge))
            return;

        edges.add(edge);
        PropertyChangeEvent event = new PropertyChangeEvent(this, "addEdge", null, edge);
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * Method to add an edge to the graph that isn't being loaded from a file
     *
     * @param edge edge to be added
     */
    public void addNewEdge(Edge edge) {
        addEdge(edge);

        deselectNodes();
        isAddingNewEdge = false;
        nodeA = null;
    }

    /**
     * It sets the graph in the condition where the user is going to add a new edge
     */
    public void setAddingNewEdge() {
        nodeA = getNodeA();
        isAddingNewEdge = true;
        PropertyChangeEvent event = new PropertyChangeEvent(this, "newEdgeCreation", null, null);
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * This methods is called to find the endpoint of an edge between two selected nodes.
     *
     * @return Node that is the endpoint of a edge
     */
    public Node getEndPointEdge() {
        for (Node node : nodes) {
            if (node.getSelected() && node != nodeA)
                return node;
        }
        return null;
    }

    /**
     * This methods finds the first node selected
     *
     * @return Node that is the startPoint of an edge
     */
    public Node getNodeA() {
        for (Node node : nodes) {
            if (node.getSelected())
                return node;
        }
        return null;
    }

    /**
     * Returns the starting node of an edge while the edge is being created
     * Used to draw the edge to the cursor when adding new edges
     *
     * @return the node which the user started adding an edge from
     */
    public Node getStartPointEdge() {
        return nodeA;
    }

    /**
     * Method that gets all the locations(Shapes) of the selected nodes
     *
     * @return an array or rectangles that represent the locations of each selected node
     */
    public ArrayList<Rectangle> getSelectedNodesShape() {
        return nodes.stream().filter(Node::getSelected).map(Node::getShape).
                collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns if the graph is in the process of adding a new edge
     *
     * @return if it is adding a new edge
     */
    public boolean isAddingNewEdge() {
        return isAddingNewEdge;
    }

    /**
     * It checks if exists an edge from nodeA to nodeB
     *
     * @param nodeA starting Node
     * @param nodeB ending Node
     * @return True if the edge from nodeA to nodeB exists, false otherwise
     */
    private boolean isEdge(Node nodeA, Node nodeB) {
        if (nodeA == null)
            return false;
        return edges.contains(new Edge(nodeA, nodeB));
    }

    /**
     * Method to deselect all the nodes that are currently selected
     */
    public void deselectNodes() {
        Predicate<Node> isSelected = Node::getSelected;
        nodes.stream().filter(isSelected).forEach(this::selectedNode);
        numNodesSelected = 0;
        nodeA = null;
    }

    /**
     * It makes all the nodes selected
     */
    public void selectAllNodes() {
        Predicate<Node> isNotSelected = x -> !x.getSelected();
        nodes.stream().filter(isNotSelected).forEach(this::selectedNode);
        numNodesSelected = nodes.size();
        nodeA = null;
    }

    /**
     * Removes a node and fires a property change event
     *
     * @param node node to be removed
     */
    public void removeNode(Node node) {
        removeNodeEdges(node);
        nodes.remove(node);
        PropertyChangeEvent event = new PropertyChangeEvent(this, "removeNode", null, null);
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * It finds all the edges of the nodes that are selected
     *
     * @return all the edges of the nodes that are selected
     */
    public ArrayList<Edge> edgesSelectedNodes() {
        ArrayList<Edge> edgesSelectedNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getSelected())
                for (Edge edge : edges) {
                    if (edge.getEndpoint1() == node || edge.getEndpoint2() == node)
                        edgesSelectedNodes.add(edge);
                }
        }
        return edgesSelectedNodes;
    }

    /**
     * Remove the nodes that are selected from the graph.
     * Before removing the nodes, it deletes all the edges related to them.
     */
    public void removeSelectedNodes(ArrayList<Node> selectedNodes) {
        Predicate<Node> isSelected = Node::getSelected;
        //remove all the edges of the nodes
        selectedNodes.forEach(this::removeNodeEdges);
        //remove the nodes
        nodes.removeIf(isSelected);

        numNodesSelected = 0;

        PropertyChangeEvent event = new PropertyChangeEvent(this, "removeNode", null, null);
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * It removes all the edges of the node
     *
     * @param node node that is the starting or the ending point
     */
    private void removeNodeEdges(Node node) {
        Predicate<Edge> isEdgeFromNode = x -> x.getEndpoint1() == node || x.getEndpoint2() == node;
        edges.removeIf(isEdgeFromNode);
    }

    /**
     * It removes the edge from the graph
     *
     * @param edge edge that needs to be removed
     */
    public void removeEdge(Edge edge) {
        edges.remove(edge);
        deselectNodes();
        numNodesSelected = 0;
        PropertyChangeEvent event = new PropertyChangeEvent(this, "removeEdge", null, null);
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * It drags all the nodes that are selected
     *
     * @param x New Horizontal x position of the nodes
     * @param y New Vertical y position of the nodes
     */
    public void moveSelectedNodes(ArrayList<Node> selectedNodes, int x, int y) {
        selectedNodes.forEach(n -> n.move(x, y));
    }

    /**
     * It moves any node that is inputted
     *
     * @param x New Horizontal x position of the node
     * @param y New Vertical y position of the node
     */
    public void moveNode(Node node, int x, int y) {
        node.move(x, y);
    }

    /**
     * It reset the shape of the selectedNodes with the original shape.
     * It looks the same node of selectedNodes inside the current list of nodes.
     * That is necessary to avoid bugs due to the fact during the execution many nodes
     * could have been removed and the order is incorrect
     *
     * @param selectedNodes nodes to be modified
     * @param shape         Original shape to be restore
     */
    public void resetShapeSelectedNodes(ArrayList<Node> selectedNodes, ArrayList<Rectangle> shape) {
        for (int i = 0; i < selectedNodes.size(); i++) {
            int index = nodes.indexOf(selectedNodes.get(i));
            if (index == -1)
                continue;
            if (i < shape.size())
                nodes.get(index).setShape(shape.get(i));
        }
    }

    /**
     * Method to get all the nodes that the user has selected
     *
     * @return arraylist of all nodes that are selected
     */
    public ArrayList<Node> getSelectedNodes() {
        return nodes.stream().filter(Node::getSelected).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Getter for the size of the nodes arraylist
     *
     * @return number of nodes in the graph
     */
    public int getNumNodes() {
        return nodes.size();
    }

    /**
     * It asks the user to choose a ".graph" file.
     * That file will be transformed into a GraphFileFormat object.
     * The GraphFileFormat is read and the respective graph will be created
     *
     * @param path path to the graph file, null otherwise
     */
    private void loadFromFile(String path) {
        GraphLoader loader = new GraphLoader();
        GraphFileFormat graph = loader.loadGraph(path);

        if (graph == null) {
            //Probably the user decided to not choose any file or the file doesn't exist anymore
            return;
        }

        resetGraph();

        loadNodes(graph);
        loadEdges(graph);

        PropertyChangeEvent event = new PropertyChangeEvent(this, "loadGraph", null, null);
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * It's makes the node selected if it is deselected and vice versa.
     *
     * @param node Node selected
     */
    public void selectedNode(Node node) {
        if (node.getSelected())
            numNodesSelected--;
        else
            numNodesSelected++;

        node.setSelected();

        if (numNodesSelected == 1) {
            nodeA = getNodeA();
        }

        PropertyChangeEvent event;
        if (numNodesSelected == 2 && isEdge(nodeA, getEndPointEdge()))
            event = new PropertyChangeEvent(this, "edgePresent", null, numNodesSelected);
        else
            event = new PropertyChangeEvent(this, "nodeSelected", null, numNodesSelected);

        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * Changes the colors of all the selected nodes passed to the method
     *
     * @param selectedNodes a list of the current selected nodes that will have their colors changed
     * @param color         the new color for the nodes
     */
    public void changeSelectedNodesColor(ArrayList<Node> selectedNodes, Color color) {
        for (Node node : selectedNodes) {
            node.setBackgroundColor(color);
        }
        PropertyChangeEvent event = new PropertyChangeEvent(this, "changeNodesColor", null, color);
        propertyChangeSupport.firePropertyChange(event);
    }

    /**
     * It adds a Point object to the GraphFileFormat file
     *
     * @param graph GraphFileFormat file
     * @param shape shape of the node
     */
    private void addPointGraphFile(GraphFileFormat graph, Rectangle shape) {
        Point point;
        point = new Point(shape.x, shape.y);
        graph.addPoint(point);
    }

    /**
     * It adds a Dimension object to the GraphFileFormat file
     *
     * @param graph GraphFileFormat file
     * @param shape shape of the node
     */
    private void addDimensionGraphFile(GraphFileFormat graph, Rectangle shape) {
        Dimension dimension;
        dimension = new Dimension(shape.width, shape.height);
        graph.addDimension(dimension);
    }

    /**
     * It adds the name of the node to the GraphFileFormat file
     *
     * @param graph GraphFileFormat file
     * @param node  The node from which the name should be saved
     */
    private void addNameGraphFile(GraphFileFormat graph, Node node) {
        graph.addName(node.getName());
    }

    /**
     * It adds the edges of graph to the GraphFileFormat file
     *
     * @param graph GraphFileFormat file
     */
    private void addEdgesGraphFile(GraphFileFormat graph) {

        for (Edge edge : edges) {
            graph.addIndex1Edge(nodes.indexOf(edge.getEndpoint1()));
            graph.addIndex2Edge(nodes.indexOf(edge.getEndpoint2()));
        }
    }

    /**
     * It creates a GraphFileFormat which will be filled with the data of the graph.
     * The data of the graph include the number of the nodes, the number of the edges,
     * the nodes and the edges.
     *
     * @return GraphFileFormat file with all
     * the information of the current graph
     */
    private GraphFileFormat createGraphFileFormat() {
        GraphFileFormat graph = new GraphFileFormat();

        Rectangle shape;
        graph.setNumberNodes(nodes.size());
        graph.setNumberEdges(edges.size());

        for (Node node : nodes) {
            shape = node.getShape();

            addPointGraphFile(graph, shape);
            addDimensionGraphFile(graph, shape);
            addNameGraphFile(graph, node);
        }
        addEdgesGraphFile(graph);

        return graph;
    }

    /**
     * It allows to save the current graph.
     */
    public void saveGraph() {
        GraphFileFormat graph;
        GraphSaver saver = new GraphSaver();

        graph = createGraphFileFormat();
        saver.saveGraph(graph);
    }

    /**
     * Overloading of the method loadFromFile(path), the path is not specified
     * so loadFromFile is called by passing null.
     */
    public void loadFromFile() {
        loadFromFile(null);
    }

    /**
     * This method reads a GraphFileFormat object and will create all the edges
     * specified inside that object.
     *
     * @param graph GraphFileFormat to be read.
     *              It contains all the nodes and the edges of the graph
     */
    private void loadEdges(GraphFileFormat graph) {
        ArrayList<Integer> index1Edge = graph.getIndex1Edge();
        ArrayList<Integer> index2Edge = graph.getIndex2Edge();
        for (int i = 0; i < graph.getNumberEdges(); i++) {
            addEdge(index1Edge.get(i), index2Edge.get(i));
        }
    }

    /**
     * This method reads a GraphFileFormat object and will create all the nodes
     * specified inside that object.
     *
     * @param graph GraphFileFormat to be read.
     *              It contains all the nodes and the edges of the graph
     */
    private void loadNodes(GraphFileFormat graph) {
        ArrayList<Point> points = graph.getCoordinates();
        ArrayList<Dimension> dimensions = graph.getDimensions();
        ArrayList<String> names = graph.getNames();
        for (int i = 0; i < graph.getNumberNodes(); i++) {
            addNode(points.get(i), dimensions.get(i), names.get(i));
        }
    }


    /**
     * Getter command for the nodes of the graph model
     *
     * @return the nodes list of this model
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * Getter command for the edges of the graph model
     *
     * @return the edges list of this model
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * Checks all nodes in the model to see if they are selected
     *
     * @return total number of selected nodes
     */
    public int getNumSelectedNodes() {
        return numNodesSelected;
    }

    /**
     * Setter number of nodes selected
     *
     * @param numNodesSelected number of nodes selected
     */
    public void setNumNodesSelected(int numNodesSelected) {
        this.numNodesSelected = numNodesSelected;

        PropertyChangeEvent event = new PropertyChangeEvent(this, "nodeSelected", null, numNodesSelected);
        propertyChangeSupport.firePropertyChange(event);
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
