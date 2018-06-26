package dataStructures.classes;

public final class DoublyLinkedListNode<T> {
    private DoublyLinkedListNode next = null;
    private DoublyLinkedListNode prev = null;
    private T val;
    public DoublyLinkedListNode(T x) {
        this.val = x;
    }

    public void setNext(DoublyLinkedListNode next) {
        this.next = next;
    }

    public void setPrev(DoublyLinkedListNode prev) {
        this.prev = prev;
    }

    public void setVal(T x) {
        val = x;
    }

    public DoublyLinkedListNode getNext() {
        return next;
    }

    public DoublyLinkedListNode getPrev() {
        return prev;
    }

    public boolean isEqual(T x) {
        if (x == val) return true;
            else return false;
    }

    public T getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }


}
