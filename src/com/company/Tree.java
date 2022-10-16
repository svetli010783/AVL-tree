package com.company;

import java.io.FileNotFoundException;

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
        } else
            add(top, top, node);
        return node;
    }

    private Node add(Node node, Node parent, Node addedElement) {

        if (node == null) {
            addedElement.parent = parent;
            return addedElement;
        }

        if (node.value < addedElement.value) {
            node.right = add(node.right, node, addedElement);
        } else if (node.value > addedElement.value) {
            node.left = add(node.left, node, addedElement);
        }

        node.height = Math.max(height(node.right), height(node.left)) + 1;

        if (height(node) > 2)
            node = balancing(node);

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

    private Integer search(Node node, int value) {
        if (node != null) {
            if (node.value == value)
                return node.value;
            else if (node.value < value) {
                return search(node.right, value);
            } else {
                return search(node.left, value);
            }
        }

//        throw new NotFoundException();
        return null;
    }

    public boolean isBalanced() {
        return false;
    }

    private Node balancing(Node node) {
        if (height(node.right) - height(node.left) == 2) {
            if (height(node.right.left) <= height(node.right.right))
                return shortLeftTurn(node);
            else
                return longLeftTurn(node);
        } else if (height(node.left) - height(node.right) == 2) {
            if (height(node.left.right) <= height(node.right.right))
                return shortRightTurn(node);
            else
                return longRightTurn(node);
        }
        return node;

    }


    private Node shortLeftTurn(Node node) {
        Node leftPart = node;
        node = node.right;
        node.parent = null;
        leftPart.right = node.left;
        leftPart.right.parent = leftPart;
        node.left = leftPart;
        leftPart.parent = node;
        leftPart.height = node.height - 1;
        return node;
    }

    public Node longLeftTurn(Node node) {
        return node;

    }

    public Node shortRightTurn(Node node) {
        return node;

    }

    public Node longRightTurn(Node node) {
        return node;

    }

    public static class NotFoundException extends Exception {

    }
}
