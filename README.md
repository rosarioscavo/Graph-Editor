# Graph-Editor

Graph-Editor, as the name suggests, is a directed unweighted graph editor in which you can:
1. Add/remove a node
2. Redominate a node
3. Change the color of a node
4. Insert/Delete a directed edge, specifying the direction
5. Save/Load a graph
6. Undo/Redo the operations performed

> In mathematics, and more specifically in graph theory, a directed graph is a graph that is made up of a set of vertices connected by edges, where the edges have a direction associated with them.

Source: [Wikipedia](https://en.wikipedia.org/wiki/Directed_graph "Directed graph")

> Model–view–controller is a software design pattern commonly used for developing user interfaces that divides the related program logic into three interconnected elements. This is done to separate internal representations of information from the ways information is presented to and accepted from the user.

Source: [Wikipedia](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller "Model–view–controller")

Graph-Editor has strong adhesion to the MVC (Model–view–controller) Pattern, with 37 classes in total. It uses Java Swing's components.

The architecture is pointed out in the figure below, obtained using the Graph-Editor itself.
<img src="https://drive.google.com/uc?export=view&id=1bF7-UBDazNSHQ3RaCjNgl87gpH18FHKx" />

The development of this project has been given as an assignment at the University Of Groningen.
It's also present a [report](https://github.com/rosarioscavo/Graph-Editor/blob/main/report.pdf) in which a more detailed explanation of the project is given.

## Table of contents

- [How to run](#how-to-run)
- [Developed by](#developed-by)

## How to run
[Download](https://github.com/rosarioscavo/Graph-Editor/releases/download/v1.0/grapheditor.jar) `grapheditor.jar` and then run:

```bash
$ java -jar grapheditor.jar
```

To load a graph during the start of the program:

```bash
$ java -jar grapheditor.jar "path/to/<filename>.graph"
```

## Developed by

- [Rosario Scavo](https://github.com/rosarioscavo)
- [Brody Hartman](https://github.com/B-hartman)

