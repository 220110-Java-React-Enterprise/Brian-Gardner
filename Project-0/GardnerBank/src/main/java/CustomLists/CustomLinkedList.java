package CustomLists;

import java.util.Iterator;

public class CustomLinkedList<E> implements CustomListInterface<E>, Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    //Adds an object to the end of the linked list
    public void add(E e) {
        Node<E> newNode = new Node<E>(e);
        if (head == null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    //Adds an object at the specified index in the linked list
    //and pushes all further nodes down list
    public void add(E e, int index) {
        //Return from method if given index is out of bounds of this.size and notify user object was not added
        if (index >= this.size || index < 0) {
            System.out.println("Index out of bounds. " + e + " was not added to list.");
            return;
        }

        Node<E> newNode = new Node<E>(e);

        //Check if list is empty and set both head and tail to new node
        if (head == null) {
            head = newNode;
            tail = newNode;
        }

        //Check if index is head, add links to old and new head node
        //and set new node as head node
        else if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }

        //Check if index is tail, add links to old and new tail node
        //and set new node as tail node
        /*else if (index == this.size - 1) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }*/

        else {
            Node<E> indexNode = this.head;

            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }

            newNode.prev = indexNode;
            newNode.next = indexNode.next;

            indexNode.prev.next = newNode;
            indexNode.next.prev = newNode;
        }

        //Increments size after adding an element to linked list
        size++;
    }

    //Gets the object found at provided index position
    public E get(int index) {
        if (index >= this.size || index < 0) {
            System.out.println("Index out of bounds");
            throw new IndexOutOfBoundsException();
        }

        Node<E> tmpNode = this.head;

        for (int i = 0; i < index; i++) {
            tmpNode = tmpNode.next;
        }

        return tmpNode.obj;
    }

    //Clears the linked list by setting head and tail to null
    public void clear() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    //Searches linked list for an object using Object.equals() to compare
    //Returns the index of the first matching object found or -1 if not found
    public int contains(E e) {
        Node<E> tmpNode = this.head;

        for (int i = 0; i < this.size; i++) {
            if (tmpNode.obj.equals(e)) {
                return i;
            }
            tmpNode = tmpNode.next;
        }

        return -1;
    }

    //Removes an object from linked list and splices the two resulting separate lists
    public void remove(int index) {
        //Checks if linked list is empty and prints such to console if so
        if (this.head == null) {
            System.out.println("Linked list has no elements to remove");
            return;
        }

        //Checks if given index is out of range from 0 to size - 1
        if (index >= this.size || index < 0) {
            System.out.println("Index out of bounds. Failed to remove object from linked list");
            return;
        }

        //Checks if element to be removed is last in linked list and clears list if so
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        }

        //Checks if given index is 0 and head node must be changed
        else if (index == 0) {
            this.head.next.prev = null;
            this.head = this.head.next;
        }

        //Checks if given index is size - 1 and tail node must be changed
        else if (index == this.size - 1) {
            this.tail.prev.next = null;
            this.tail = this.tail.prev;
        }

        //Runs if given index is valid and not head or tail node
        else {
            Node<E> indexNode = this.head;

            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }

            indexNode.prev.next = indexNode.next;
            indexNode.next.prev = indexNode.prev;
        }

        //Decrements size after removing one element from linked list
        this.size--;
    }

    //Returns the size of the linked list
    public int size() {
        return this.size;
    }

    //Iterator implementation
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> cursor = head;

            //Checks if the linked list has another node, testing if the cursor
            //points to a node or if it is null
            public boolean hasNext() {
                if (cursor == null) {
                    return false;
                }
                return true;
            }

            public E next() {
                E e = cursor.obj;
                cursor = cursor.next;
                return e;
            }
        };
    }

    //Private node class contains a reference to object of list type,
    //a reference to the next node, and to the previous node
    private class Node<E> {
        Node<E> next;
        Node<E> prev;
        E obj;

        //Empty constructor creates an empty node
        Node() { }

        //Creates a node and stores an object by reference
        Node(E e) {
            obj = e;
        }

        //Creates a node which stores an object by reference and has a reference to another node
        Node(E e, Node<E> next) {
            this(e);
            this.next = next;
        }

        //Creates a node which stores an object by reference and has references to two nodes
        //The previous and next in the list
        Node(E e, Node<E> next, Node<E> prev) {
            this(e, next);
            this.prev = prev;
        }
    }

    //Converts all items in linked list into String and concatenates them
    public String toString() {
        String resultString = "";
        if (this.head != null)  {
            Node<E> IndexNode = this.head;

            for (int i = 0; i < this.size; i++) {
                resultString += IndexNode.obj.toString() + " ";
                IndexNode = IndexNode.next;
            }
        }
        return resultString;
    }
}
