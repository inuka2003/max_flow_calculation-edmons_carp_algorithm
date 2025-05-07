/*
Student Name: Inuka Nimsara Wickramasinghe
Student ID: w2052208
*/

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Edge>[] adjacencyList;
    int numberOfNodes;

    @SuppressWarnings("unchecked")
    public Graph(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        adjacencyList = new ArrayList[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            adjacencyList[i] = new ArrayList<Edge>();
        }
    }

    public void addEdge(int from, int to, int capacity) {
        Edge forwardEdge = new Edge(from, to, capacity);
        Edge backwardEdge = new Edge(to, from, 0);

        forwardEdge.reverse = backwardEdge;
        backwardEdge.reverse = forwardEdge;

        adjacencyList[from].add(forwardEdge);
        adjacencyList[to].add(backwardEdge);
    }

    public List<Edge>[] getAdjacencyList() {
        return adjacencyList;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }
}
