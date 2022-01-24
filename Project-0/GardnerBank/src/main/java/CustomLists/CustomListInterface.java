package CustomLists;

public interface CustomListInterface<E> {
    //Returns the size of the collection
    int size();

    //Adds an item to the end of the collection
    void add(E e);

    //Adds an item to some index, and shifts those items at/after the index for room
    void add(E e, int index);

    //Returns the element at the specified index
    E get(int index);

    //Removes element at specified index, and shift remaining items to close gap
    void remove(int index);

    //Removes all elements from the collection
    void clear();

    //Checks if collection contains this item and returns its index
    //or -1 if not found
    int contains(E e);
}
