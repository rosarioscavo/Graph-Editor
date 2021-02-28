package nl.rug.oop.grapheditor.model.io;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GraphSaver {
    private FileWriter graphFile;
    private final JFileChooser chooser;

    /**
     * Constructor for the graph saver, creates a file chooser with our custom settings
     */
    public GraphSaver() {
        chooser = new JFileChooser();
        chooserSetting();
    }

    /**
     * Settings for the file chooser, sets up .graph files as a filter
     */
    private void chooserSetting() {
        chooser.setCurrentDirectory(new File("."));


        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Graph Files", "graph");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);
    }

    /**
     * Get a save file using the file chooser
     *
     * @return the file chosen by the user
     */
    private File getSaveFile() {
        int returnVal = chooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            System.out.println("The user decided to not save the file");
        }
        return null;
    }


    /**
     * Method that saves all the nodes into a GraphFileFormat
     *
     * @param graph the graph file format object that is storing all the data
     */
    private void saveNodes(GraphFileFormat graph) {
        int numberNodes = graph.getNumberNodes();
        ArrayList<Point> coordinates = graph.getCoordinates();
        ArrayList<Dimension> dimensions = graph.getDimensions();
        ArrayList<String> names = graph.getNames();

        for (int i = 0; i < numberNodes; i++) {
            try {
                graphFile.write(coordinates.get(i).x + " ");
                graphFile.write(coordinates.get(i).y + " ");
                graphFile.write(dimensions.get(i).height + " ");
                graphFile.write(dimensions.get(i).width + " ");
                graphFile.write(names.get(i));
                graphFile.write("\n");
            } catch (IOException e) {
                System.out.println("It hasn't been possible to save the graph, try again");
            }
        }

    }

    /**
     * Method that saves all the edges into a GraphFileFormat
     *
     * @param graph the graph file format object that is storing all the data
     */
    private void saveEdges(GraphFileFormat graph) {
        int numberEdges = graph.getNumberEdges();
        ArrayList<Integer> index1Edge = graph.getIndex1Edge();
        ArrayList<Integer> index2Edge = graph.getIndex2Edge();

        for (int i = 0; i < numberEdges; i++) {
            try {
                graphFile.write(index1Edge.get(i) + " ");
                graphFile.write(index2Edge.get(i) + "\n");
            } catch (IOException e) {
                System.out.println("It hasn't been possible to save the graph, try again");
            }
        }

    }

    /**
     * Method that saves the number of edges and nodes into a GraphFileFormat
     *
     * @param graph the graph file format object that is storing all the data
     */
    private void saveSize(GraphFileFormat graph) {
        try {
            graphFile.write(graph.getNumberNodes() + " ");
            graphFile.write(graph.getNumberEdges() + "\n");
        } catch (IOException e) {
            System.out.println("It hasn't been possible to save the graph, try again");
        }
    }

    /**
     * Method to save the whole graph, writes all the data to a file
     *
     * @param graph the graph file format object that is storing all the data
     */
    public void saveGraph(GraphFileFormat graph) {
        File file = getSaveFile();

        if (file == null) {
            return;
        }

        try {
            graphFile = new FileWriter(file + ".graph");
            saveSize(graph);
            saveNodes(graph);
            saveEdges(graph);
            graphFile.close();
        } catch (IOException e) {
            System.out.println("It hasn't been possible to save the graph, try again");
        }
    }
}
