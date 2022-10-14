package com.company;

public class Tree {

    private Node top = null;

    public int height(Node node) {
        if (node == null)
            return 0;
        return node.height;

    }

    public Node add(int value) {
        Node node = new Node(value);
        if (top == null) {
            top = node;
        }
        add(top, top, value);
        return node;
    }

    private Node add(Node node, Node parent, int value) {
        if (node == null)
            return (new Node(value, parent));

        if (node.value < value) {
            node.right = add(node.right, node, value);
        } else if (node.value > value) {
            node.left = add(node.left, node, value);
        }

        node.height = Math.max(height(node.right), height(node.left)) + 1;

        turnIsNeed(isBalanced());

        return node;
    }


    public Node getTop() {
        return top;
    }

    public void setTop(Node top) {
        this.top = top;
    }


    public int search(int value) {
        return search(top, value);
    }

    private int search(Node node, int value) {
        if (node != null) {
            if (node.value == value)
                return node.value;

            if (node.value < value) {
                search(node.right, value);
            }
            if (node.value > value) {
                search(node.left, value);
            }
        }
        return -1;

    }


    public boolean isBalanced() {
        return false;
    }

    private void turnIsNeed(boolean isBalanced, Node node) {
        if (node.height - node.left.height == 2) {
            shortLeftTurn(node);
        }
    }


    private void shortLeftTurn(Node node) {
        Node copy = node;
        node = copy.right;

        copy.left.right = copy.right.left;
        copy.left.left  =copy.left;

        node.right = node.right.right;


    }

    public void longLeftTurn() {

    }

    public void shortRightTurn() {

    }

    public void longRightTurn() {

    }

}
