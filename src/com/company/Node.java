package com.company;

import java.util.Objects;

public class Node {
    int value;
    int height = 1;
    Node right, left, parent;


    public Node(int value) {
        this.value = value;
    }

    public Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
    }

    public Node(int value, int height, Node right, Node left, Node parent) {
        this.value = value;
        this.height = height;
        this.right = right;
        this.left = left;
        this.parent = parent;
    }
    public Node(Node node, Node parent){
        this.value = node.value;
        this.height = node.height;
        this.right = node.right;
        this.left = node.left;
        this.parent = parent;
    }
    public Node(Node node){
        this.value = node.value;
        this.height = node.height;
        this.right = node.right;
        this.left = node.left;
        this.parent = node.parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return value == node.value && Objects.equals(parent, node.parent)
                && Objects.equals(left, node.left) && Objects.equals(right, node.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, parent, left, right);
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +

                '}';
    }

}
