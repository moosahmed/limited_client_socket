package com.newrelic.codingchallenge;

import java.io.*;

/**
 * Building a Binary Search Tree, to significantly decrease time required to identify duplicates
 */
public class BST<Key extends Comparable<Key>> {
    private Node root;             // root of BST
    private int duplicates = 0;
    private File file;

    public BST() {
        file = new File("./out/numbers.log");
        if (file.exists()) file.delete();
    }

    private class Node {
        private Key key;           // sorted by key
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Key key, int size) {
            this.key = key;
            this.size = size;
        }
    }

    public int getDuplicates() {
        return duplicates;
    }

    public void addToLog(String txt){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(txt + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Unable to read file " + file.toString());
        }
    }

    /**
     * Returns the number of keys in this Tree at root or Node.
     */
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * Inserts the keys into the Tree.
     * Adds new keys to the log file.
     * Keeps the count of duplicates that have come through.
     */
    public void put(Key key) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key);
    }

    private Node put(Node x, Key key) {
        if (x == null) {
            addToLog((String) key);
            return new Node(key, 1);
        }
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key);
        else if (cmp > 0) x.right = put(x.right, key);
        else     duplicates++;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }
}
