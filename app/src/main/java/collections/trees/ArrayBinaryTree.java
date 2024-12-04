package collections.trees;

import collections.lists.ArrayUnorderedList;
import collections.queues.LinkedQueue;
import collections.queues.QueueADT;
import exceptions.ElementNotFoundException;

import java.util.Iterator;

public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {
    private static final int CAPACITY = 100;
    /** Number of elements in the tree */
    protected int count;
    /** Array of elements */
    protected T[] tree;

    /**
     * Creates an empty binary tree.
     */
    public ArrayBinaryTree() {
        this.count = 0;
        this.tree = (T[]) new Object[CAPACITY];
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the new tree
     */
    public ArrayBinaryTree(T element) {
        this.count = 1;
        this.tree = (T[]) new Object[CAPACITY];
        this.tree[0] = element;
    }

    protected void expandCapacity() {
        T[] newArray = (T[]) new Object[tree.length * 2];

        System.arraycopy(tree, 0, newArray, 0, tree.length);

        tree = newArray;
    }

    @Override
    public T getRoot() {
        return tree[0];
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean contains(T targetElement) {
        try {
            return this.find(targetElement) != null;
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    @Override
    public T find(T targetElement) {
        boolean found = false;
        T result = null;

        for (int i = 0; i < this.size() && !found; i++) {
            if (targetElement.equals(tree[i])) {
                found = true;
                result = this.tree[i];
            }
        }

        if (!found) {
            throw new ElementNotFoundException("Elemento não encontrado");
        }

        return result;
    }

    protected void inorder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length && tree[node] != null) {
            inorder(node * 2 + 1, tempList);
            tempList.addToRear(tree[node]);
            inorder((node + 1) * 2, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        this.inorder(0, tempList);

        return tempList.iterator();
    }

    public void preorder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length && tree[node] != null) {
            tempList.addToRear(tree[node]);
            preorder(node * 2 + 1, tempList);
            preorder((node + 1) * 2, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        this.preorder(0, tempList);

        return tempList.iterator();
    }

    public void postorder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length && tree[node] != null) {
            postorder(node * 2 + 1, tempList);
            postorder((node + 1) * 2, tempList);
            tempList.addToRear(tree[node]);
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        this.postorder(0, tempList);

        return tempList.iterator();
    }

    protected void levelorder(int node, ArrayUnorderedList<T> tempList) {
        if (node < tree.length && tree[node] != null) {
            QueueADT<Integer> nodes = new LinkedQueue<>();

            nodes.enqueue(node);

            while (!nodes.isEmpty()) {
                int current = nodes.dequeue();

                if (current < tree.length && tree[current] != null) {

                    tempList.addToRear(tree[current]);
                    int left = current * 2 + 1;
                    int right = (current + 1) * 2;

                    if (tree[left] != null) {
                        nodes.enqueue(left);
                    }

                    if (tree[right] != null) {
                        nodes.enqueue(right);
                    }
                }
            }
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        this.levelorder(0, tempList);

        return tempList.iterator();
    }
}
