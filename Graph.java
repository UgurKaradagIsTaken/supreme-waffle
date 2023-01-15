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
        if(hash < 0) { hash = -hash;
        
        }
        return hash % size;
    }

    public void add(String key) {
    int index = hashCode(key);
    if (index < 0 || index >= array.length) {
        
        System.out.println("Out of bounds error when trying to add element to HashTable.");
        return;
    }
    if (array[index] == null) {
        array[index] = key;
    } else {
        int i = 1;
        while (array[(index + i) % array.length] != null) {
            i++;
        }
        array[(index + i) % array.length] = key;
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

    public int search(String key) {
    int index = hashCode(key);
    if (index < 0 || index >= array.length) {
        return -1;
    }
    if (array[index] == null) {
        return -1;
    }
    if (array[index].equals(key)) {
        return index;
    }
    int i = 1;
    while (array[(index + i) % array.length] != null) {
        if (array[(index + i) % array.length].equals(key)) {
            return (index + i) % array.length;
        }
        i++;
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
         public String getValue(int index) {
        return array[index];
    }
}



