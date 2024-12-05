package Structures.collections.trees;

import Structures.collections.lists.ArrayUnorderedList;
import Structures.collections.queues.LinkedQueue;
import Structures.collections.queues.QueueADT;
import Structures.exceptions.ElementNotFoundException;

import java.util.Iterator;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {
    /** Number of elements in the tree */
    protected  int count;
    /** Reference to the root of this tree */
    protected BinaryTreeNode<T> root;

    public LinkedBinaryTree() {
        count = 0;
        root = null;
    }

    public LinkedBinaryTree(T element) {
        count = 1;
        root = new BinaryTreeNode<T>(element);
    }


    @Override
    public T getRoot() {
        return root.element;
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
        BinaryTreeNode<T> found = findAgain(targetElement, root);

        return found != null;
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree.
     *
     * @param targetElement  the element being sought in this tree
     * @param next           the element to begin searching from
     */
    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }

        if (next.element.equals(targetElement)) {
            return next;
        }

        /** Procura no lado esquerdo da árvore */
        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);

        if (temp == null) {
            /** Procura no lado direito da árvore */
            temp = findAgain(targetElement, next.right);

        }

        return temp;
    }

    @Override
    public T find(T targetElement) {
        /** Check if the element is in the tree */
        BinaryTreeNode<T> current = findAgain( targetElement, root );

        /** Caso o elemento não seja encontrado */
        if( current == null ) {
            throw new ElementNotFoundException("binary tree");
        }

        return current.element;
    }

    protected void inorder (BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            inorder(node.left, tempList);
            tempList.addToRear(node.element);
            inorder(node.right, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inorder(root, tempList);

        return tempList.iterator();
    }

    protected void preOrder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.element);
            preOrder(node.left, tempList);
            preOrder(node.right, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        preOrder(root, tempList);

        return tempList.iterator();
    }

    protected void postOrder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if (node != null) {
            postOrder(node.left, tempList);
            postOrder(node.right, tempList);
            tempList.addToRear(node.element);
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        postOrder(root, tempList);

        return tempList.iterator();
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();

        return levelOrder(root, tempList);
    }

    protected Iterator<T> levelOrder(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList) {
        if(node != null) {
            // Create a new queue to store the nodes
            QueueADT<BinaryTreeNode<T>> nodes = new LinkedQueue<>();
            // Enqueue the root node
            nodes.enqueue(node);

            while (!nodes.isEmpty()) {
                BinaryTreeNode<T> current = nodes.dequeue();

                if (current != null) {
                    tempList.addToRear(current.element);
                    nodes.enqueue(current.left);
                    nodes.enqueue(current.right);
                }
            }
            return tempList.iterator();
        }
        return null;
    }
}
