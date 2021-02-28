package nl.rug.oop.grapheditor.model;

import java.util.Objects;

/**
 * Edge contains the information necessary to represent an edge, that is two nodes.
 * endpoints is the name of the vertices(nodes) of an edge, from the node endpoint1 to endpoint2
 */
public class Edge {
    private final Node endpoint1;
    private final Node endpoint2;

    /**
     * The constructor initializes the two nodes
     *
     * @param endpoint1 start node of the edge
     * @param endpoint2 end node of the edge
     */
    public Edge(Node endpoint1, Node endpoint2) {
        this.endpoint1 = endpoint1;
        this.endpoint2 = endpoint2;
    }

    /**
     * Getter for the first endpoint of the edge
     *
     * @return endpoint 1
     */
    public Node getEndpoint1() {
        return endpoint1;
    }

    /**
     * Getter for the second endpoint of the edge
     *
     * @return endpoint 2
     */
    public Node getEndpoint2() {
        return endpoint2;
    }

    /**
     * To string for an edge
     * Edge{endpoint1=X,endpoint2=Y}
     *
     * @return a string in our custom format for reading the edge
     */
    @Override
    public String toString() {
        return "Edge{" +
                "endpoint1=" + endpoint1.getName() +
                ", endpoint2=" + endpoint2.getName() +
                '}';
    }

    /**
     * Method to check if 2 edges equal each other
     *
     * @param o an object to compare against the edge
     * @return if object o is equal to the edge
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(endpoint1, edge.endpoint1) &&
                Objects.equals(endpoint2, edge.endpoint2);
    }

    /**
     * Method to generate a unique hashcode for each edge based on their endpoints
     *
     * @return hash code representation of the edge
     */
    @Override
    public int hashCode() {
        return Objects.hash(endpoint1, endpoint2);
    }
}
