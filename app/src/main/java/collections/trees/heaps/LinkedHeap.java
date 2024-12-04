package collections.trees.heaps;

import collections.trees.LinkedBinaryTree;
import exceptions.EmptyCollectionException;

public class LinkedHeap<T extends Comparable<T>> extends LinkedBinaryTree<T> implements HeapADT<T> {
    private HeapNode<T> lastNode;
    private int size;

    public LinkedHeap() {
        super();
        size = 0;
    }

    /**
     * Adds the specified element to this heap in the appropriate position according to its key value.
     * Note that equal elements are added to the right.
     * @param obj the element to be added to this heap
     */
    public void addElement(T obj) {
        HeapNode<T> node = new HeapNode<>(obj);

        if (root == null) {
            root = node;
        } else {
            HeapNode<T> nextParent = getNextParentAdd();

            if (nextParent.left == null) {
                nextParent.left = node;
            } else {
                nextParent.right = node;
            }
            node.parent = nextParent;
        }
        lastNode = node;
        size++; // Atualiza size

        if (size > 1) {
            heapifyAdd();
        }
    }

    /**
     * Returns the node that will be the parent of the new node.
     *
     * @return the node that will be a parent of the new node
     */
    private HeapNode<T> getNextParentAdd() {
        HeapNode<T> result = lastNode;

        while ((result != root) && (result.parent.left != result)) {
            result = result.parent;
        }

        if (result != root) {
            if (result.parent.right == null) {
                result = result.parent;
            } else {
                result = (HeapNode<T>) result.parent.right;
                while (result.left != null) {
                    result = (HeapNode<T>) result.left;
                }
            }
        } else {
            while (result.left != null) {
                result = (HeapNode<T>) result.left;
            }
        }

        return result;
    }

    /**
     * Reorders this heap after adding a node.
     */
    private void heapifyAdd() {
        T temp = lastNode.element;
        HeapNode<T> next = lastNode;

        while ((next != root) && (temp.compareTo(next.parent.element) < 0)) {
            next.element = next.parent.element;
            next = next.parent;
        }

        next.element = temp;
    }

    /**
     * Removes the element with the lowest value in this heap and returns a reference to it.
     * Throws an EmptyCollectionException if the heap is empty.
     *
     * @return the element with the lowest value in this heap
     * @throws EmptyCollectionException if the heap is empty
     */
    public T removeMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }

        T minElement = root.element;

        if (size == 1) {
            root = null;
            lastNode = null;
        } else {
            HeapNode<T> nextLast = getNewLastNode();

            if (lastNode.parent.left == lastNode) {
                lastNode.parent.left = null;
            } else {
                lastNode.parent.right = null;
            }
            root.element = lastNode.element;
            lastNode = nextLast;
            heapifyRemove();
        }

        size--; // Atualiza size

        return minElement;
    }

    @Override
    public T findMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }
        return root.element;
    }

    /**
     * Returns the node that will be the new last node after a remove.
     *
     * @return the node that will be the new last node after a remove
     */
    private HeapNode<T> getNewLastNode() {
        HeapNode<T> result = lastNode;

        while ((result != root) && (result.parent.left == result)) {
            result = result.parent;
        }

        if (result != root) {
            result = (HeapNode<T>) result.parent.left;
        }

        while (result.right != null) {
            result = (HeapNode<T>) result.right;
        }

        return result;
    }

    /**
     * Reorders this heap after removing the root element.
     */
    private void heapifyRemove() {
        T temp = root.element;
        HeapNode<T> node = (HeapNode<T>) root;
        HeapNode<T> left = (HeapNode<T>) node.left;
        HeapNode<T> right = (HeapNode<T>) node.right;
        HeapNode<T> next;

        if ((left == null) && (right == null)) {
            next = null;
        } else if (left == null) {
            next = right;
        } else if (right == null) {
            next = left;
        } else if (left.element.compareTo(right.element) < 0) {
            next = left;
        } else {
            next = right;
        }

        while ((next != null) && (next.element.compareTo(temp) < 0)) {
            node.element = next.element;
            node = next;
            left = (HeapNode<T>) node.left;
            right = (HeapNode<T>) node.right;

            if ((left == null) && (right == null)) {
                next = null;
            } else if (left == null) {
                next = right;
            } else if (right == null) {
                next = left;
            } else if (left.element.compareTo(right.element) < 0) {
                next = left;
            } else {
                next = right;
            }
            node.element = temp;
        }
    }

    public void removeAllElements() {
        root = null;
        lastNode = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
