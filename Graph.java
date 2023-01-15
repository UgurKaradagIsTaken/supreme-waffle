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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Graph {
    private int[][] adjacencyMatrix;
    public HashTable ht = new HashTable(400);
    
    public void readGraphFromFile(String fileName) {
    try {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        int numVertices = 0;

        while (sc.hasNext()) {
            String[] tokens = sc.nextLine().split(",");
            if (ht.search(tokens[0]) == -1) {
                ht.add(tokens[0]);
                numVertices++;
            }
            if (ht.search(tokens[1]) == -1) {
                ht.add(tokens[1]);
                numVertices++;
            }
        }
        
        adjacencyMatrix = new int[1000][1000];
        sc.close();

        sc = new Scanner(file);
        while (sc.hasNext()) {
    String[] parts = sc.nextLine().split(",");
    int i = ht.search(parts[0]);
    int j = ht.search(parts[1]);
    int weight = Integer.parseInt(parts[2]);
    if(i < adjacencyMatrix.length && j < adjacencyMatrix.length){
        adjacencyMatrix[i][j] = weight;
        adjacencyMatrix[j][i] = weight;
    }
}
        sc.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

 /*
    public void readGraphFromFile(String fileName) {
        try (BufferedReader breader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int numVertices = 0;
            while ((line = breader.readLine()) != null) {
                String[] tokens = line.split(",");
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
*/
    
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
public static void main(String[] args) {
    Graph graph = new Graph();
    graph.readGraphFromFile("/Users/apple/Desktop/text.txt");

    Scanner scanner = new Scanner(System.in);
    int choice;
    do {
        System.out.println("Write your choice:");
        System.out.println("1. Check if there is a path between two nodes");
        System.out.println("2. Breadth-first search from one node to another");
        System.out.println("3. Depth-first search from one node to another");
        System.out.println("4. Number of vertices in the component that starts with a node");
        System.out.println("5. Number of paths from one to another");
        System.out.println("6. ShortestPathLengthFromTo");
        System.out.println("7. Exit");
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Enter the name of the first node: ");
                String name1 = scanner.next();
                System.out.println("Enter the name of the second node: ");
                String name2 = scanner.next();
                if (graph.isThereAPath(name1, name2)) {
                    System.out.println("There is a path between " + name1 + " and " + name2);
                } else {
                    System.out.println("There is no path between " + name1 + " and " + name2);
                }
                break;
            case 2:
                System.out.println("Enter the name of the starting node: ");
                String start = scanner.next();
                System.out.println("Enter the name of the ending node: ");
                String end = scanner.next();
                graph.BFSfromTo(start, end);
                break;
            case 3:
                System.out.println("Enter the name of the starting node: ");
                String startNode = scanner.next();
                System.out.println("Enter the name of the ending node: ");
                String endNode = scanner.next();
                graph.DFSfromTo(startNode, endNode);
                break;
            case 4:
                System.out.print("Enter the name of the starting node: ");
                name1 = scanner.nextLine();
                graph.NoOfVerticesInComponent(name1);
                break;
            case 5:
                System.out.print("Enter the name of the starting node: ");
                name1 = scanner.nextLine();
                System.out.print("Enter the name of the ending node: ");
                name2 = scanner.nextLine();
                int paths = graph.NoOfPathsFromTo(name1, name2);
                if (paths == -1) {
                    System.out.println("Failed.");
                } else {
                    System.out.println("Number of paths from " + name1 + " to " + name2 + ": " + paths);
                }
            case 6:
                System.out.print("Enter start vertex name: ");
                String start1 = scanner.next();
                System.out.print("Enter end vertex name: ");
                String end1 = scanner.next();
                System.out.println("Shortest path length from " + start1 + " to " + end1 + ": " + graph.ShortestPathLengthFromTo(start1, end1));
                break;
            case 7:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                break;
        }
    } while (choice != 7);
    scanner.close();
}

}


