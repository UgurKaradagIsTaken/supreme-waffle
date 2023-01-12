/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp2112_project3_modified;

/**
 *
 * @author apple
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {
    private int[][] adjacencyMatrix;
    public HashTable ht = new HashTable(100);
    private List<List<Integer>> allPaths = new ArrayList<List<Integer>>(); //To store all valid paths

    // This method reads the graph data from a file and populates the adjacency matrix
    public void readGraphFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int numVertices = 0;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (ht.search(tokens[0]) == -1) {
                    ht.add(tokens[0]);
                    numVertices++;
                }
                if (ht.search(tokens[1]) == -1) {
                    ht.add(tokens[1]);
                    numVertices++;
                }
            }
            adjacencyMatrix = new int[numVertices][numVertices];
            br.close();

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                int i = ht.search(tokens[0]);
                int j = ht.search(tokens[1]);
                int weight = Integer.parseInt(tokens[2]);
                adjacencyMatrix[i][j] = weight;
                adjacencyMatrix[j][i] = weight;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method checks if a path exists between two vertices in the graph
    public boolean isThereAPath(String name1, String name2) {
        int vertex1 = ht.search(name1);
        int vertex2 = ht.search(name2);

        if (vertex1 == -1 || vertex2 == -1) {
            System.out.println("One or both of the specified vertices do not exist in the graph.");
            return false;
        }

        boolean[] visited = new boolean[adjacencyMatrix.length];
        return depthFirstSearch(vertex1, vertex2, visited);
    }

    // This is a helper method that performs a depth-first search on the graph
    private boolean depthFirstSearch(int currentVertex, int targetVertex, boolean[] visited) {
        visited[currentVertex] = true;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[currentVertex][i] > 0) {
                if (i == targetVertex) {
                    return true;
                }
                if (!visited[i]) {
                    if (depthFirstSearch(i, targetVertex, visited)) {
                        return true;
                    }
                }
            }
        }
    return false;
}
       public int ShortestPathLengthFromTo(String name1, String name2) {
    int start = ht.search(name1);
    int end = ht.search(name2);
    int[] distance = new int[adjacencyMatrix.length];
    boolean[] visited = new boolean[adjacencyMatrix.length];

    // Initialize all distances as infinite and visited as false
    for (int i = 0; i < distance.length; i++) {
        distance[i] = Integer.MAX_VALUE;
    }
    distance[start] = 0;

    // Using priority queue to get vertex with minimum distance
    PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
    queue.add(start);

    while (!queue.isEmpty()) {
        int currentVertex = queue.poll();
        visited[currentVertex] = true;

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[currentVertex][i] > 0 && !visited[i]) {
                int newDistance = distance[currentVertex] + adjacencyMatrix[currentVertex][i];
                if (newDistance < distance[i]) {
                    distance[i] = newDistance;
                }
                if (i == end) {
                    return distance[i];
                }
                queue.add(i);
            }
        }
    }
    System.out.println("infinity");
    return -1;
}
       


    


}


    




