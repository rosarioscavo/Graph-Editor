package nl.rug.oop.grapheditor;

import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.view.GraphFrame;

public class GraphEditor {

    public static void main(String[] args) {

        GraphModel graph;
        //It's important to quote the path on the CLI,
        // especially if the path contains white space
        if (args.length > 0) {
            graph = new GraphModel(args[0]);
        } else {
            graph = new GraphModel();
        }
        new GraphFrame(graph);
    }
}