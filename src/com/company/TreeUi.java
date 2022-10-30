package com.company;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TreeUi<V> extends JFrame {
    private final Graph graph = new SingleGraph("Test");
    private final JButton addButton = new JButton("ADD");
    private final JButton addManyButton = new JButton("ADD MANY");

    private final JButton searchButton = new JButton("SEARCH");
    private final JButton removeButton = new JButton("REMOVE");
    private final JButton showButton = new JButton("SHOW");
    private final JTextField textField = new JTextField();
    private final JTextArea textArea = new JTextArea();
    private final Tree avlTree = new Tree();
    private final LinLog hl = new LinLog();

    public TreeUi() {
        super("TreeUi");
        this.setBounds(100, 100, 250, 100);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4, 8, 2, 2));
        container.add(addButton);
        container.add(addManyButton);
        container.add(searchButton);
        container.add(removeButton);
        container.add(showButton);
        container.add(textField);
        container.add(textArea);


        addButton.addActionListener(e -> {
            if (!Objects.equals(textField.getText(), "")) {
                var number = Integer.parseInt(textField.getText());
                add(number);
            }
            textField.setText("");
        });

        addManyButton.addActionListener(e -> {
            if (!Objects.equals(textField.getText(), "")) {
                var number = Integer.parseInt(textField.getText());
                generateUniqueNumbers(number).forEach(this::add);
            }
        });

        searchButton.addActionListener(e -> {
            if (!Objects.equals(textField.getText(), ""))
                if (avlTree.search(Integer.parseInt(textField.getText())))
                    textArea.append("TRUE");
                else textArea.append("FALSE");
            textField.setText("");
        });
        removeButton.addActionListener(e -> {
            if (!Objects.equals(textField.getText(), ""))
                avlTree.remove(Integer.parseInt(textField.getText()));
            textField.setText("");
        });
        showButton.addActionListener(e -> {
            Viewer viewer = graph.display();
            viewer.enableAutoLayout(hl);
        });
        avlTree.setBalanceAction(new BalanceAction() {
            final AtomicInteger i = new AtomicInteger(0);

            @Override
            public void balanced() {
                graph.clear();
                textArea.setText("сбалансировано " + i.getAndIncrement());
                Stack<Node> stack = avlTree.enumeration();
                while (!stack.isEmpty()) {
                    var nodeId1 = stack.pop().toString();
                    graph.addNode(nodeId1).setAttribute("ui.class","Node");
                    if (avlTree.getParentOf(Integer.parseInt(nodeId1)) != null) {
                        var nodeId2 = avlTree.getParentOf(Integer.parseInt(nodeId1)).toString();
                        graph.addEdge(String.format("%s%s", nodeId1, nodeId2), nodeId2, nodeId1);
                    }
                }
            }
        });


    }

    private void add(int number) {
        avlTree.add(number);
        var nodeId = String.valueOf(number);
        if (graph.getNode(nodeId) == null) {
            graph.addNode(nodeId).setAttribute("ui.class","Node");
            if (avlTree.getParentOf(Integer.parseInt(nodeId)) != null) {
                var nodeId2 = avlTree.getParentOf(Integer.parseInt(nodeId)).toString();
                graph.addEdge(String.format("%s%s", nodeId, nodeId2), nodeId2, nodeId);
            }
        } else {
            System.out.println(nodeId + " already in tree");
        }
    }

    private Set<Integer> generateUniqueNumbers(int count) {
        var rnd = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();
        while (uniqueNumbers.size() < count) {
            uniqueNumbers.add(rnd.nextInt());
        }
        return uniqueNumbers;
    }

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        SwingUtilities.invokeLater(() -> {
            var treeUi = new TreeUi();
            treeUi.setVisible(true);
        });
    }

}
