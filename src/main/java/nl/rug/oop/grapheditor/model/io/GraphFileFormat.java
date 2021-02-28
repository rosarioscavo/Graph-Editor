package nl.rug.oop.grapheditor.model.io;

import java.awt.*;
import java.util.ArrayList;

public class GraphFileFormat {
    private final ArrayList<Point> coordinates;
    private final ArrayList<Dimension> dimensions;
    private final ArrayList<String> names;
    private final ArrayList<Integer> index1Edge;
    private final ArrayList<Integer> index2Edge;
    private int numberNodes, numberEdges;

    /**
     * Basic Constructor initialises all the data to empty values which are filled in by saving or loading
     */
    public GraphFileFormat() {
        coordinates = new ArrayList<>();
        dimensions = new ArrayList<>();
        names = new ArrayList<>();
        index1Edge = new ArrayList<>();
        index2Edge = new ArrayList<>();
        numberNodes = 0;
        numberEdges = 0;
    }

    /**
     * Setter method for the number of nodes in the graph file format
     *
     * @param numberNodes new number of nodes in the graph file format
     */
    public void setNumberNodes(int numberNodes) {
        this.numberNodes = numberNodes;
    }

    /**
     * Setter method for the number of edges in the graph file format
     *
     * @param numberEdges number of edges in the graph file format
     */
    public void setNumberEdges(int numberEdges) {
        this.numberEdges = numberEdges;
    }

    /**
     * Adds a point to the points arraylist in graph file format
     *
     * @param point point to be added
     */
    public void addPoint(Point point) {
        coordinates.add(point);
    }

    /**
     * Adds a dimension to the dimensions arraylist in the graph file format
     *
     * @param dimension to be added
     */
    public void addDimension(Dimension dimension) {
        dimensions.add(dimension);
    }

    /**
     * Adds the first index of an edge to the graph file format
     *
     * @param index first index of an edge
     */
    public void addIndex1Edge(int index) {
        index1Edge.add(index);
    }

    /**
     * Adds a name of a node to the graph file format
     *
     * @param name name of the node
     */
    public void addName(String name) {
        names.add(name);
    }

    /**
     * Adds the second index of an edge to the graph file format
     *
     * @param index second index of an edge
     */
    public void addIndex2Edge(int index) {
        index2Edge.add(index);
    }

    /**
     * Getter fot the coordinates of the nodes from the graph file format
     *
     * @return the coordinates of all the nodes stored as points
     */
    public ArrayList<Point> getCoordinates() {
        return coordinates;
    }

    /**
     * Getter for the dimensions of the nodes from the graph file format
     *
     * @return the dimensions of all the nodes
     */
    public ArrayList<Dimension> getDimensions() {
        return dimensions;
    }

    /**
     * Getter for the names of all the nodes stored in the the graph file format
     *
     * @return the names of all the nodes in the graph file format
     */
    public ArrayList<String> getNames() {
        return names;
    }

    /**
     * Getter for the first indexes of all the edges from the graph file format
     *
     * @return the index of the first node in an edge stored in the graph file format
     */
    public ArrayList<Integer> getIndex1Edge() {
        return index1Edge;
    }

    /**
     * Getter for the second indexes of all the edges from the graph file format
     *
     * @return the index of the second node in an edge stored in the graph file format
     */
    public ArrayList<Integer> getIndex2Edge() {
        return index2Edge;
    }

    /**
     * Getter for the number of nodes in the graph file format
     *
     * @return number of nodes stored
     */
    public int getNumberNodes() {
        return numberNodes;
    }

    /**
     * Getter for the number of edges in the graph file format
     *
     * @return number of edges stored
     */
    public int getNumberEdges() {
        return numberEdges;
    }
}
