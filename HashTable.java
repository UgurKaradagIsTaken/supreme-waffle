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
import java.util.Arrays;


public class HashTable {
    public String[] array;
    private int size;

    public HashTable(int size) {
        this.size = size;
        array = new String[size];
        Arrays.fill(array, null);
    }

    public int hashCode(String name) {
        int hash = 7;
        for (int i = 0; i < name.length(); i++) {
            hash = hash*31 + name.charAt(i);
        }
        return hash % size;
    }

    public void add(String name) {
        int index = hashCode(name);
        if (array[index] != null) {
            int originalIndex = index;
            index = (index + 1) % size;
            while (index != originalIndex && array[index] != null) {
                index = (index + 1) % size;
            }
        }
        array[index] = name;
        this.size++;
        if (isFull()) {
            resize();
        }
    }

    private void resize() {
        int newSize = size*2 + 1;
        HashTable newTable = new HashTable(newSize);
        for (String name : array) {
            if (name != null) {
                newTable.add(name);
            }
        }
        this.size = newTable.size;
        this.array = newTable.array;
    }

    private boolean isFull() {
        for (String name : array) {
            if (name == null) {
                return false;
            }
        }
        return true;
    }

    public int search(String name) {
        int index = hashCode(name);
        if (array[index] != null && array[index].equals(name)) {
            return index;
        }
        int originalIndex = index;
        index = (index + 1) % size;
        while (index != originalIndex && array[index] != null) {
            if (array[index].equals(name)) {
                return index;
            }
            index = (index + 1) % size;
        }
        return -1;
    }
        public String[] getNames() {
        int count = 0;
        for (String name : array) {
            if (name != null) {
                count++;
            }
        }
        String[] names = new String[count];
        int i = 0;
        for (String name : array) {
            if (name != null) {
                names[i] = name;
                i++;
            }
        }
        return names;
    }

}


