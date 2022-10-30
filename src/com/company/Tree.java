package com.company;

import java.util.*;

public class Tree<V> implements SortedMap<Integer, V> {

    int size = 0;

    private Node<V> top = null;

    private BalanceAction balanceAction;


    public void setBalanceAction(BalanceAction balanceAction) {
        this.balanceAction = balanceAction;
    }


    private int height(Node<V> node) {

        if (node == null) return 0;
        return node.height;

    }

    public Node<V> add(int id, V value) {
        Node<V> node = new Node<V>(id, value);
        if (top == null) {
            top = node;
        } else add(top, top, node);
        return node;

    }

    private Node<V> add(Node<V> node, Node<V> parent, Node<V> addedElement) {

        if (node == null) {
            addedElement.parent = parent;
            return addedElement;
        }

        if (node.id < addedElement.id) {
            node.right = add(node.right, node, addedElement);
        } else if (node.id > addedElement.id) {
            node.left = add(node.left, node, addedElement);
        }

        node.height = Math.max(height(node.right), height(node.left)) + 1;

        if (height(node) > 2) node = balancing(node);

        return node;
    }

    public Node<V> remove(Integer id) {
        Node<V> node = new Node<V>(id);
        if (top == null) {
            top = node;
        } else remove(top, node);
        return node;
    }

    private Node<V> getTop() {
        return top;
    }

    private Node<V> remove(Node<V> node, Node<V> removedElement) {

        if (removedElement.equals(top)) {
            Node<V> tempNode = node;
            top = findMin(top.right);
            top.left = tempNode.left;
            top.right = tempNode.right;
            return top;
        }
        if (removedElement.equals(node)) {
            if (node.right != null) {
                Node<V> parent = node.parent;
                Node<V> left = node.left;
                Node<V> right = node.right;

                Node<V> min = findMin(node.right);
                min.parent = parent;
                min.left = left;
                min.right = right;

                Node<V> current = findMin(min.right);
                return min;
            }
            node = node.left;
            return node;
        }
        if (node.id > removedElement.id)
            node.left = remove(node.left, removedElement);
        else if (node.id < removedElement.id)
            node.right = remove(node.right, removedElement);

        node.height = Math.max(height(node.right), height(node.left)) + 1;


        return node;
    }

    private Node<V> findMin(Node<V> node) {
        if (node.left == null) {
            return node;
        }
        Node<V> min = findMin(node.left);
        return min;
    }


    private boolean search(Integer id) {
        return search(top, id) != null;
    }

    private Node<V> search(Node<V> node, Integer id) {
        if (node != null) {
            if (node.id == id) return node;
            else if (node.id < id) {
                return search(node.right, id);
            } else {
                return search(node.left, id);
            }
        }

//        throw new NotFoundException();
        return null;
    }

    private Node<V> search(Node<V> node, V value) {
        if (node != null) {
            if (node.value.equals(value))
                return node;
            search(node.left, value);

            search(node.right, value);
        }

//        throw new NotFoundException();
        return null;
    }

    public Integer getParentOf(int id) {
        if (search(id))
            if (search(top, id).parent != null)
                return search(top, id).parent.id;
        return null;
    }


    private Node<V> balancing(Node<V> node) {
        if (height(node.right) - height(node.left) >= 2) {
            if (height(node.right.left) <= height(node.right.right)) return shortLeftTurn(node);
            else return longLeftTurn(node);
        } else if (height(node.left) - height(node.right) >= 2) {
            if (height(node.left.right) <= height(node.left.left)) return shortRightTurn(node);
            else return longRightTurn(node);
        }
        return node;

    }

    //turns
    private Node<V> shortLeftTurn(Node<V> node) {
        Node<V> leftPart = node;
        if (node.parent != null) node.right.parent = node.parent;
        else node.right.parent = null;
        node = node.right;
        leftPart.right = node.left;
        if (leftPart.right != null) leftPart.right.parent = leftPart;
        node.left = leftPart;
        leftPart.parent = node;
        leftPart.height = node.height - 1;
        if (node.parent != null) if (node.parent.id < node.id) node.parent.right = node;
        else node.parent.left = node;
        else top = node;
        balanceAction.balanced();
        return node;
    }

    private Node<V> longLeftTurn(Node<V> node) {
        Node<V> leftPart = node;
        Node<V> rightPart = node.right;
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
        if (node.parent != null) if (node.parent.id < node.id) node.parent.right = node;
        else node.parent.left = node;
        else top = node;
        balanceAction.balanced();
        return node;
    }

    private Node<V> shortRightTurn(Node<V> node) {
        Node<V> rightPart = node;
        if (node.parent != null) node.left.parent = node.parent;
        else node.left.parent = null;

        node = node.left;
        rightPart.left = node.right;
        if (rightPart.left != null)
            rightPart.left.parent = rightPart;
        node.right = rightPart;
        rightPart.parent = node;
        rightPart.height = node.height - 1;
        if (node.parent != null) if (node.parent.id < node.id) node.parent.right = node;
        else node.parent.left = node;
        else top = node;
        balanceAction.balanced();
        return node;
    }

    private Node<V> longRightTurn(Node<V> node) {
        Node<V> rightPart = node;
        Node<V> leftPart = node.left;
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
        if (node.parent != null) if (node.parent.id < node.id) node.parent.right = node;
        else node.parent.left = node;
        else top = node;
        balanceAction.balanced();
        return node;
    }

    public Stack<Node<V>> enumeration() {
        Stack<Node<V>> stack = new Stack<>();
        enumeration(stack, top);
        return stack;
    }

    private void enumeration(Stack<Node<V>> emptyStack, Node<V> node) {
        if (node == null)
            return;


        enumeration(emptyStack, node.left);

        enumeration(emptyStack, node.right);

        emptyStack.add(node);
    }

    //sort methods
    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, V> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    @Override
    public SortedMap<Integer, V> headMap(Integer toKey) {
        return null;
    }

    @Override
    public SortedMap<Integer, V> tailMap(Integer fromKey) {
        return null;
    }

    @Override
    public Integer firstKey() {
        return null;
    }

    @Override
    public Integer lastKey() {
        return null;
    }
//map methods
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return search((Integer) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return search(top, (V) value) != null;
    }

    @Override
    public V get(Object key) {
        return search(top, (Integer) key).value;
    }

    @Override
    public V put(Integer key, V value) {
        return add(key, value).value;
    }

    @Override
    public V remove(Object key) {
        return remove((Integer) key).value;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        top = null;
    }

    @Override
    public Set<Integer> keySet() {
        Stack<Node<V>> stack = enumeration();
        Set<Integer> set = new HashSet<>();
        while (!stack.isEmpty())
            set.add(stack.pop().id);
        return set;
    }

    @Override
    public Collection<V> values() {
        Stack<Node<V>> stack = enumeration();
        List<V> list = new ArrayList<>();
        while (!stack.isEmpty())
            list.add(stack.pop().value);
        return list;
    }

    @Override
    public Set<Entry<Integer, V>> entrySet() {
        Stack<Node<V>> stack = enumeration();
        Set<Entry<Integer, V>> set = new HashSet<>();
        while (!stack.isEmpty()) {
            TreeEntry<V> entry = new TreeEntry<>(stack.peek().id, stack.pop().value);
            set.add(entry);
        }
        return set;
    }



    private static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o2.id - o1.id;
        }
    }

    private static class TreeEntry<V> implements Entry<Integer, V> {

        V value;
        Integer id;

        public TreeEntry(Integer id, V value) {
            this.value = value;
            this.id = id;
        }

        @Override
        public Integer getKey() {
            return id;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }
    }


}