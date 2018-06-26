package dataStructures.classes;

public class DoublyLinkedList<T> {
    private DoublyLinkedListNode<T> first = null;
    private DoublyLinkedListNode<T> last = null;
    private boolean empty = true;

    public void pushFront(DoublyLinkedListNode<T> node) {
        if (empty) {
            first = node;
            first.setPrev(null);
            last = node;
            last.setNext(null);
            empty = false;
        }
        else {
            first.setPrev(node);
            node.setNext(first);
            first = node;
        }
    }

    public DoublyLinkedListNode<T> getFirst() {
        return first;
    }

    public DoublyLinkedListNode<T> getLast() {
        return last;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void popFront() {
        if (!empty) {
            first = first.getNext();
            if (first != null) {
                first.setPrev(null);
            }
            else {
                empty = true;
            }
        }
    }

    public void pushBack(DoublyLinkedListNode<T> node) {
        if (empty) {
            first = node;
            last = node;
        }
        else {
            last.setNext(node);
            node.setPrev(last);
            last = node;
        }
    }

    public void popBack() {
        if (!empty) {
            last = last.getPrev();
            if (last != null) {
                last.setNext(null);
            }
            else {
                empty = true;
            }
        }
    }

    public void erase(DoublyLinkedListNode<T> node) {
        if (node != null) {
            DoublyLinkedListNode<T> left = node.getPrev();
            DoublyLinkedListNode<T> right = node.getNext();
            if (left == null) {
                popFront();
            }
            else if (right == null) {
                popBack();
            }
            else {
                left.setNext(right);
                right.setPrev(left);
            }
        }
    }

    public void clear() {
        first = null;
        last = null;
        empty = true;
    }

    public DoublyLinkedListNode<T> findNode(T val) {
        DoublyLinkedListNode<T> aux = first;
        while (aux != null) {
            if (aux.isEqual(val)) {
                return aux;
            }
                else {
                aux = aux.getNext();
            }
        }
        return null;
    }

    public void printAll() {
        DoublyLinkedListNode aux = first;
        while (aux != null) {
            System.out.print(aux.getVal().toString() + " ");
            aux = aux.getNext();
        }
        System.out.println("\n");
    }
}
