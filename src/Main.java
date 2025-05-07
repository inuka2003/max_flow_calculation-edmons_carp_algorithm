/*
Student Name: Inuka Nimsara Wickramasinghe
Student ID: w2052208
*/

import java.io.File;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintStream;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) {
        String filename = "bridge_1.txt"; // Input file

        try {
            PrintStream out = new PrintStream(new FileOutputStream("output.txt"));//output file
            System.setOut(out);// The output is printed in the seperate text file called output.txt

            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            int numberOfNodes = Integer.parseInt(scanner.nextLine().trim());
            Graph graph = new Graph(numberOfNodes);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(" ");
                    int from = Integer.parseInt(parts[0]);
                    int to = Integer.parseInt(parts[1]);
                    int capacity = Integer.parseInt(parts[2]);
                    graph.addEdge(from, to, capacity);
                }
            }
            scanner.close();

            long startTime = System.currentTimeMillis();

            int maximumFlow = edmondsKarp(graph, 0, numberOfNodes - 1);

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            // print final flows
            System.out.println();
            System.out.println("======= Final Flow Values =======");
            List<Edge>[] adjacencyList = graph.getAdjacencyList();
            for (int i = 0; i < graph.getNumberOfNodes(); i++) {
                for (Edge edge : adjacencyList[i]) {
                    if (edge.capacity > 0) {
                        System.out.println(edge.from + " -> " + edge.to + " : " + edge.flow + " / " + edge.capacity);
                    }
                }
            }

            // Final output summary
            System.out.println("=========================================");
            System.out.println("File tested: " + filename);
            System.out.println("Maximum Flow: " + maximumFlow);
            System.out.println("Execution Time: " + totalTime + " milliseconds");
            System.out.println("=========================================");

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public static int edmondsKarp(Graph graph, int source, int sink) {
        int totalFlow = 0;
        List<Edge>[] adjacencyList = graph.getAdjacencyList();

        while (true) {
            Edge[] parent = new Edge[graph.getNumberOfNodes()];
            Queue<Integer> queue = new LinkedList<Integer>();
            queue.add(source);

            // Breadth First Search to find augmenting path
            while (!queue.isEmpty() && parent[sink] == null) {
                int currentNode = queue.poll();
                List<Edge> edges = adjacencyList[currentNode];

                for (int i = 0; i < edges.size(); i++) {
                    Edge edge = edges.get(i);
                    if (parent[edge.to] == null && edge.to != source && edge.getResidualCapacity() > 0) {
                        parent[edge.to] = edge;
                        queue.add(edge.to);
                    }
                }
            }

            if (parent[sink] == null) {
                break;
            }

            // Find bottleneck which is minimum capacity in path
            int bottleneck = Integer.MAX_VALUE;
            Edge currentEdge = parent[sink];
            while (currentEdge != null) {
                int residualCapacity = currentEdge.getResidualCapacity();
                if (residualCapacity < bottleneck) {
                    bottleneck = residualCapacity;
                }
                currentEdge = parent[currentEdge.from];
            }


            List<Integer> path = new ArrayList<Integer>();
            int currentNode = sink;
            while (currentNode != source) {
                path.add(0, currentNode); // Insert at start
                currentNode = parent[currentNode].from;
            }
            path.add(0, source);

            // Print path
            System.out.print("Augmenting path : [");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i != path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println("]");
            System.out.println("Bottleneck capacity: " + bottleneck);
            System.out.println();

            currentEdge = parent[sink];
            while (currentEdge != null) {
                currentEdge.addFlow(bottleneck);
                currentEdge = parent[currentEdge.from];
            }

            totalFlow += bottleneck;
        }

        return totalFlow;
    }
}
