package com.company;

import java.util.Objects;

public class Node<V> implements Comparable<Node<V>> {
    Integer id;
    V value;
    int height = 1;
    Node<V> right, left, parent;

    public Node(Integer id, V value) {
        this.id = id;
        this.value = value;
    }

    public Node(Integer id, Node<V> parent) {
        this.id = id;
        this.parent = parent;
    }
    public Node(Integer id) {
        this.id = id;
    }

    public Node(Integer id, int height, Node<V> right, Node<V> left, Node<V> parent) {
        this.id = id;
        this.height = height;
        this.right = right;
        this.left = left;
        this.parent = parent;
    }
    public Node(Node<V> node, Node<V> parent){
        this.id = node.id;
        this.height = node.height;
        this.right = node.right;
        this.left = node.left;
        this.parent = parent;
    }
    public Node(Node<V> node){
        this.id = node.id;
        this.height = node.height;
        this.right = node.right;
        this.left = node.left;
        this.parent = node.parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<V> node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parent, left, right);
    }

    @Override
    public String toString() {
        return "" + id;
    }

    @Override
    public int compareTo(Node<V> o) {
        return this.id - o.id;
    }
}
