package Structures.collections.trees.heaps;

import Structures.collections.trees.BinaryTreeNode;

public class HeapNode<T> extends BinaryTreeNode<T> {
    protected HeapNode<T> parent;

    public HeapNode(T obj) {
        super(obj);
        parent = null;
    }

    public HeapNode<T> getParent() {
        return parent;
    }

    public void setParent(HeapNode<T> node) {
        parent = node;
    }
}
