package Structures.collections.trees;

import Structures.exceptions.ElementNotFoundException;

public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T> {

    /**
     * Creates an empty binary search tree.
     */
    public LinkedBinarySearchTree() {
        super();
    }

    /**
     * Creates a binary search with the specified element as its root.
     *
     * @param element  the element that will be the root of the new
     * binary search tree
     */
    public LinkedBinarySearchTree (T element) {
        super (element);
    }

    @Override
    public void addElement(T element) {
        BinaryTreeNode<T> newNode = new BinaryTreeNode<T>(element);
        Comparable<T> comparableElement = (Comparable<T>) element;

        if (isEmpty()) {
            root = newNode;
        } else {
            BinaryTreeNode<T> current = root;
            boolean added = false;

            // Percorre a árvore até encontrar uma folha
            while (!added) {
                if (comparableElement.compareTo(current.element) < 0) {
                    if (current.left == null) {
                        current.left = newNode;  // Adiciona o novo nó à esquerda
                        added = true;            // A minha flag passa a true
                    } else { // Não encontrou uma folha à esquerda
                        current = current.left;
                    } // Continua a percorrer a árvore à esquerda
                } else {
                    if (current.right == null) {
                        current.right = newNode;
                        added = true;
                    } else {
                        current = current.right;
                    }
                }
            }
        }
        count++;
    }

    /**
     * Removes the first element that matches the specified target element
     * from the binary search tree and returns a reference to it. Throws a ElementNotFoundException if the
     * specified target element is not found in the binary search tree.
     *
     * @param targetElement  the element being sought in the binary search tree
     *
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        if (isEmpty()) {
            throw new ElementNotFoundException("Element not found in this tree");
        }

        T result = null;

        if(((Comparable)targetElement).equals(root.element)) { // Se o elemento alvo é a raiz
            result = root.element;
            root = replacement(root);
            count--;
        } else {                                          // Se o elemento alvo não é a raiz
            BinaryTreeNode<T> current, parent = root;
            boolean found = false;

            if (((Comparable)targetElement).compareTo(root.element) < 0) {
                current = root.left;
            } else {
                current = root.right;
            }

            while (current != null && !found) {
                if (targetElement.equals(current.element)) {
                    found = true;
                    count--;
                    result = current.element;

                    if (current == parent.left) {
                        parent.left = replacement(current);
                    } else {
                        parent.right = replacement(current);
                    }
                } else {
                    parent = current;

                    if (((Comparable)targetElement).compareTo(current.element) < 0) {
                        current = current.left;
                    } else {
                        current = current.right;
                    }
                }
            }
            if (!found) {
                throw new ElementNotFoundException("Element not found in this tree");
            }
        }

        return result;
    }

    /**
     * Returns a reference to a node that will replace the one specified for removal.
     * In the case where the removed node has two children, the inorder successor is used as its replacement.
     *
     * @param node  the node to be removed
     *
     * @return      a reference to the replacing node
     */
    private BinaryTreeNode<T> replacement(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> result = null;

        if((node.left == null) && (node.right == null)) {
            result = null;
        } else if((node.left != null) && (node.right == null)) {
            result = node.left;
        } else if((node.left == null) && (node.right != null)) {
            result = node.right;
        } else { // senão caso ambos os filhos sejam != null
            BinaryTreeNode<T> current = node.right;
            BinaryTreeNode<T> parent = node;

            while(current.left != null) {
                parent = current;
                current = current.left;
            }

            if(node.right == current) {
                current.left = node.left;
            } else {
                parent.left = current.right;
                current.right = node.right;
                current.left = node.left;
            }
            result = current;
        }
        return result;
    }

    /**
     * Removes all occurrences of the specified element from this tree.
     *
     * @param targetElement  the element that the list will have all instances of it removed
     *
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public void removeAllOccurrences(T targetElement) throws ElementNotFoundException {
        removeElement(targetElement);

        while(true) {
            try {
                removeElement(targetElement);
            } catch (ElementNotFoundException e) {
                break;
            }
        }
    }

    @Override
    public T removeMin() {
        return removeElement(findMin());
    }

    @Override
    public T removeMax() {
        return removeElement(findMax());
    }

    @Override
    public T findMin() {
        BinaryTreeNode<T> current = root;

        // Percorre a árvore até encontrar o nó mais à esquerda
        while (current.left != null) {
            current = current.left;
        }

        return current.element;
    }

    @Override
    public T findMax() {
        BinaryTreeNode<T> current = root;

        // Percorre a árvore até encontrar o nó mais à direita
        while (current.right != null) {
            current = current.right;
        }

        return current.element;
    }
}
