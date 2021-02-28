package nl.rug.oop.grapheditor.model.io;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * GraphLoader is responsible to allow to choose a .graph file to be loaded. It contains all the necessary
 * to allow the user to choose a file by a GUI(chooser) and read the file(scanner)
 */
public class GraphLoader {
    private Scanner scanner;
    private final JFileChooser chooser;

    /**
     * Constructor to initialize and configure the file chooser and
     */
    public GraphLoader() {
        chooser = new JFileChooser();
        chooserSetting();
    }

    /**
     * This method is responsible to set the setting of the file chooser.
     * A filter is created in order to accept and show only files with ".graph" extension.
     * The option "See all" is disabled
     */
    private void chooserSetting() {
        chooser.setCurrentDirectory(new File("."));

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Graph Files", "graph");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(filter);
    }

    /**
     * It asks to choose a file by a JFileChooser object.
     * The file must respect the filter set.
     *
     * @return It returns a File instance, that is the chosen file.
     */
    private File takeGraphFile() {
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    /**
     * Check if the path to the file has the .graph extension
     *
     * @param path path of the graph that needs to be checked
     * @return True if the path is for a file with extension .graph
     * otherwise False
     */
    private boolean isAGraphFile(String path) {
        int indexDot;

        String extension = "";
        indexDot = path.lastIndexOf(".");
        if (indexDot != -1) {
            extension = path.substring(path.lastIndexOf("."));
        }

        return indexDot != -1 && extension.equals(".graph");
    }

    /**
     * This method, after checking the path is for a .graph file and that the file exist,
     * returns that file.
     *
     * @param path path to the file
     * @return It returns a File instance, that is the file from the specified path
     */
    private File takeGraphFile(String path) {

        if (!isAGraphFile(path)) {
            System.out.println("You can read only .graph file, " +
                    "please try again with a different file." +
                    "\nDon't forget to quote the path on the CLI.");
            return null;
        }

        return new File(path);
    }

    /**
     * It reads the file by the scanner and fills the graph with the edges
     *
     * @param graph       GraphFileFormat to be set
     * @param numberEdges Numbers of edges
     */
    private void loadEdges(GraphFileFormat graph, int numberEdges) {
        for (int i = 0; i < numberEdges; i++) {
            graph.addIndex1Edge(scanner.nextInt());
            graph.addIndex2Edge(scanner.nextInt());
        }
    }

    /**
     * It reads the file by the scanner and fills the graph with the nodes
     *
     * @param graph       GraphFileFormat to be set
     * @param numberNodes Numbers of nodes
     */
    private void loadNodes(GraphFileFormat graph, int numberNodes) {
        int height, width;
        for (int i = 0; i < numberNodes; i++) {
            Point point = new Point(scanner.nextInt(), scanner.nextInt());
            graph.addPoint(point);
            height = scanner.nextInt();
            width = scanner.nextInt();
            //notice that the file specify the height before the width but the constructor of Dimension has the opposite order
            Dimension dimension = new Dimension(width, height);
            graph.addDimension(dimension);
            graph.addName(scanner.next());
        }
    }

    /**
     * loadGraph is responsible to allow to choose a ".graph" file.
     * The file will be translated into a GraphFileFormat object.
     *
     * @return It returns a GraphFileFormat object
     */
    public GraphFileFormat loadGraph(String path) {
        File file;
        GraphFileFormat graph;
        //if the path of the file is not defined, use the JFileChooser
        if (path == null) {
            file = takeGraphFile();
        } else {
            file = takeGraphFile(path);
        }

        if (file == null) {
            return null;
        }

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("The selected file doesn't exist anymore, try another file or try later ");
            return null;
        }

        graph = new GraphFileFormat();

        //reading the first line
        int numberNodes = scanner.nextInt();
        int numberEdges = scanner.nextInt();

        graph.setNumberNodes(numberNodes);
        graph.setNumberEdges(numberEdges);

        loadNodes(graph, numberNodes);
        loadEdges(graph, numberEdges);

        return graph;
    }

}
