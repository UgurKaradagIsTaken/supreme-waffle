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


import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    private int[][] adjacencyMatrix;
    public HashTable ht = new HashTable(100);
 

    
    public void readGraphFromFile(String fileName) {
        try (BufferedReader breader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int numVertices = 0;
            while ((line = breader.readLine()) != null) {
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
            breader.close();

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int i = ht.search(parts[0]);
                int j = ht.search(parts[1]);
                int weight = Integer.parseInt(parts[2]);
                adjacencyMatrix[i][j] = weight;
                adjacencyMatrix[j][i] = weight;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public boolean isThereAPath(String name1, String name2) {
        int v1 = ht.search(name1);
        int v2 = ht.search(name2);

        if (v1 == -1 || v2 == -1) {
            System.out.println("Failed");
            return false;
        }

        boolean[] visited = new boolean[adjacencyMatrix.length];
        return depthFirstSearch(v1, v2, visited);
    }

    
    private boolean depthFirstSearch(int currentVertex, int endVertex, boolean[] visited) {
        visited[currentVertex] = true;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[currentVertex][i] > 0) {
                if (i == endVertex) {
                    return true;
                }
                if (!visited[i]) {
                    if (depthFirstSearch(i, endVertex, visited)) {
                        return true;
                    }
                }
            }
        }
    return false;
}
       
  public void BFSfromTo(String name1, String name2) {
    int start = ht.search(name1);
    int end = ht.search(name2);

    if (start == -1 || end == -1) {
        System.out.println("Failed.");
        return;
    }

    boolean[] visited = new boolean[adjacencyMatrix.length];
    Queue<Integer> queue = new LinkedList<>();
    queue.add(start);
    visited[start] = true;
    while (!queue.isEmpty()) {
        int current = queue.poll();
        System.out.print(ht.getValue(current) + " ");
        if (current == end) {
            System.out.println("There is a Path  from " + name1 + " to " + name2);
            return;
        }
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[current][i] > 0 && !visited[i]) {
                queue.add(i);
                visited[i] = true;
            }
        }
    }
    System.out.println("There is no path  from " + name1 + " to " + name2);
}
 //Check this code
  public void DFSfromTo(String name1, String name2) {
    int start = ht.search(name1);
    int end = ht.search(name2);

    if (start == -1 || end == -1) {
        System.out.println("Failed.");
        return;
    }

    boolean[] visited = new boolean[adjacencyMatrix.length];
    Stack<Integer> stack = new Stack<>();
    stack.push(start);
    visited[start] = true;
    while (!stack.isEmpty()) {
        int current = stack.pop();
        System.out.print(ht.getValue(current) + " ");
        if (current == end) {
            System.out.println("There is a Path  from " + name1 + " to " + name2);
            return;
        }
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[current][i] > 0 && !visited[i]) {
                stack.push(i);
                visited[i] = true;
            }
        }
    }
    System.out.println("There is no path  from " + name1 + " to " + name2);
}
// also check this method
  
  public void NoOfVerticesInComponent(String name1) {
    int start = ht.search(name1);

    if (start == -1) {
        System.out.println("Failed.");
        return;
    }

    boolean[] visited = new boolean[adjacencyMatrix.length];
    int count = 0;
    Stack<Integer> stack = new Stack<>();
    stack.push(start);
    visited[start] = true;
    count++;
    while (!stack.isEmpty()) {
        int current = stack.pop();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[current][i] > 0 && !visited[i]) {
                stack.push(i);
                visited[i] = true;
                count++;
            }
        }
    }
    System.out.println("Number of vertices in component the that starts with  " + name1 + "is : " + count);
}
  
public int NoOfPathsFromTo(String name1, String name2) {
    int start = ht.search(name1);
    int end = ht.search(name2);

    if (start == -1 || end == -1) {
        System.out.println("Failed.");
        return -1;
    }

    boolean[] visited = new boolean[adjacencyMatrix.length];
    int[] count = {0};
    dfs(start, end, visited, count);
    return count[0];
}

private void dfs(int current, int end, boolean[] visited, int[] count) {
    if (current == end) {
        count[0]++;
        return;
    }
    visited[current] = true;
    for (int i = 0; i < adjacencyMatrix.length; i++) {
        if (adjacencyMatrix[current][i] > 0 && !visited[i]) {
            dfs(i, end, visited, count);
        }
    }
    visited[current] = false;
}
public int ShortestPathLengthFromTo(String name1, String name2) {
    int start = ht.search(name1);
    int end = ht.search(name2);

    if (start == -1 || end == -1) {
        System.out.println("Failed.");
        return -1;
    }

    int[] distance = new int[adjacencyMatrix.length];
    boolean[] visited = new boolean[adjacencyMatrix.length];

    for (int i = 0; i < distance.length; i++) {
        distance[i] = Integer.MAX_VALUE;
    }
    distance[start] = 0;

    Queue<Integer> queue = new LinkedList<>();
    queue.add(start);

    while (!queue.isEmpty()) {
        int current = queue.poll();
        visited[current] = true;

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[current][i] > 0) {
                int newDistance = distance[current] + adjacencyMatrix[current][i];
                if (newDistance < distance[i]) {
                    distance[i] = newDistance;
                    if (!visited[i]) {
                        queue.add(i);
                        visited[i] = true;
                    }
                }
            }
        }
    }
    if (distance[end] == Integer.MAX_VALUE) {
        System.out.println("infinity");
        return -1;
    } else {
        return distance[end];
    }
}









    


}


    




