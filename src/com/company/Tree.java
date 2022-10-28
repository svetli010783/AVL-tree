package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.util.Stack;

public class Tree {

    private Node top = null;

    private BalanceAction balanceAction;


    public void setBalanceAction(BalanceAction balanceAction) {
        this.balanceAction = balanceAction;
    }

    private int height(Node node) {
        if (node == null) return 0;
        return node.height;

    }

    public void add(int value) {
        Node node = new Node(value);
        if (top == null) {
            top = node;
        } else add(top, top, node);

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

        if (height(node) > 2) node = balancing(node);

        return node;
    }

    public void remove(int value) {
        Node node = new Node(value);
        if (top == null) {
            top = node;
        } else remove(top, node);

    }

    public Node getTop() {
        return top;
    }

    private Node remove(Node node, Node removedElement) {

        if (removedElement.equals(top)) {
            Node tempNode = node;
            top = findMin(top.right);
            top.left = tempNode.left;
            top.right = tempNode.right;
            return top;
        }
        if (removedElement.equals(node)) {
            if (node.right != null) {
                Node parent = node.parent;
                Node left = node.left;
                Node right = node.right;

                Node min = findMin(node.right);
                min.parent = parent;
                min.left = left;
                min.right = right;

                Node current = findMin(min.right);
                return min;
            }
            node = node.left;
            return node;
        }
        if (node.value > removedElement.value)
            node.left = remove(node.left, removedElement);
        else if (node.value < removedElement.value)
            node.right = remove(node.right, removedElement);

        node.height = Math.max(height(node.right), height(node.left)) + 1;


        return node;
    }

    private Node findMin(Node node) {
        if (node.left == null) {
            return node;
        }
        Node min = findMin(node.left);
        return min;
    }


    public boolean search(int value) {
        return search(top, value) != null;
    }

    private Node search(Node node, int value) {
        if (node != null) {
            if (node.value == value) return node;
            else if (node.value < value) {
                return search(node.right, value);
            } else {
                return search(node.left, value);
            }
        }

//        throw new NotFoundException();
        return null;
    }

    public Integer getParentOf(int value) {
        if (search(value))
            if (search(top, value).parent != null)
                return search(top, value).parent.value;
        return null;
    }


    private Node balancing(Node node) {
        if (height(node.right) - height(node.left) >= 2) {
            if (height(node.right.left) <= height(node.right.right)) return shortLeftTurn(node);
            else return longLeftTurn(node);
        } else if (height(node.left) - height(node.right) >= 2) {
            if (height(node.left.right) <= height(node.left.left)) return shortRightTurn(node);
            else return longRightTurn(node);
        }
        return node;

    }


    private Node shortLeftTurn(Node node) {
        Node leftPart = node;
        if (node.parent != null) node.right.parent = node.parent;
        else node.right.parent = null;
        node = node.right;
        leftPart.right = node.left;
        if (leftPart.right != null) leftPart.right.parent = leftPart;
        node.left = leftPart;
        leftPart.parent = node;
        leftPart.height = node.height - 1;
        if (node.parent != null) if (node.parent.value < node.value) node.parent.right = node;
        else node.parent.left = node;
        else top = node;
        balanceAction.balanced();
        return node;
    }

    private Node longLeftTurn(Node node) {
        Node leftPart = node;
        Node rightPart = node.right;
        node = rightPart.left;

        if (leftPart.parent != null) node.parent = leftPart.parent;
        else node.parent = null;

        if (node.right != null) {
            rightPart.left = node.right;
            rightPart.left.parent = rightPart;
        } else rightPart.left = null;

        if (node.left != null) {
            leftPart.right = node.left;
            leftPart.right.parent = leftPart;
        } else leftPart.right = null;

        node.right = rightPart;
        node.right.height = node.height - 1;
        node.right.parent = node;
        node.left = leftPart;
        node.left.height = node.height - 2;
        node.left.parent = node;
        if (node.parent != null) if (node.parent.value < node.value) node.parent.right = node;
        else node.parent.left = node;
        else top = node;
        balanceAction.balanced();
        return node;
    }

    private Node shortRightTurn(Node node) {
        Node rightPart = node;
        if (node.parent != null) node.left.parent = node.parent;
        else node.left.parent = null;

        node = node.left;
        rightPart.left = node.right;
        if (rightPart.left != null)
            rightPart.left.parent = rightPart;
        node.right = rightPart;
        rightPart.parent = node;
        rightPart.height = node.height - 1;
        if (node.parent != null) if (node.parent.value < node.value) node.parent.right = node;
        else node.parent.left = node;
        else top = node;
        balanceAction.balanced();
        return node;
    }

    private Node longRightTurn(Node node) {
        Node rightPart = node;
        Node leftPart = node.left;
        node = leftPart.right;

        if (rightPart.parent != null) node.parent = rightPart.parent;
        else node.parent = null;

        if (node.left != null) {
            leftPart.right = node.left;
            leftPart.right.parent = leftPart;
        } else leftPart.right = null;

        if (node.right != null) {
            rightPart.left = node.right;
            rightPart.left.parent = rightPart;
        } else rightPart.left = null;

        node.left = leftPart;
        node.left.height = node.height - 1;
        node.left.parent = node;
        node.right = rightPart;
        node.right.height = node.height - 2;
        node.right.parent = node;
        if (node.parent != null) if (node.parent.value < node.value) node.parent.right = node;
        else node.parent.left = node;
        else top = node;
        balanceAction.balanced();
        return node;
    }

    public Stack<Node> enumeration() {
        Stack<Node> stack = new Stack<>();
        enumeration(stack, top);
        return stack;
    }

    private void enumeration(Stack<Node> emptyStack, Node node) {
        if (node == null)
            return;


        enumeration(emptyStack, node.left);

        enumeration(emptyStack, node.right);

        emptyStack.add(node);
    }


}
