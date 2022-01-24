package CustomLists;

public class CustomArrayList<E> implements CustomListInterface<E> {
    private Object[] array;
    private int size;
    private int maxSize;

    //Default constructor
    //Makes empty contained array with max size of 2
    public CustomArrayList() {
        this.maxSize = 2;
        this.size = 0;
        this.array = new Object[maxSize];
    }

    //Size constructor
    public CustomArrayList(int size) {
        this.maxSize = size;
        this.size = 0;
        this.array = new Object[size];
    }

    //Element list constructor
    public CustomArrayList(E ...e) {
        this.maxSize = size = e.length;
        array = new Object[size];

        for (int i = 0; i < size; ++i) {
            array[i] = e[i];
        }
    }

    //Adds an element to the end of the contained array
    public void add(Object o) {
        if (this.size == this.maxSize) {
            this.growArray();
        }

        this.array[this.size] = o;
        this.size++;
    }

    //Adds an object to the contained array at the specified index
    //Moves object currently at index and subsequent indices to current
    //index plus one
    public void add(E e, int index) throws IndexOutOfBoundsException {
        if (this.size == this.maxSize) {
            this.growArray();
        }

        Object tmpObjectA;
        Object tmpObjectB = e;

        for (int i = index; i < this.size; i++) {
            tmpObjectA = this.array[i];
            this.array[i] = tmpObjectB;
            tmpObjectB = tmpObjectA;
        }
        this.size++;
    }

    //Returns object located at given index
    public E get(int index) throws IndexOutOfBoundsException {
        return (E)this.array[index];
    }

    //Empties contained array by setting its private reference to null
    //and allowing the old array to be garbage collected
    public void clear() {
        this.maxSize = 2;
        this.size = 0;
        this.array = null;
    }

    //Check if object o is found within contained array, using Object.equals() method
    public int contains(Object o) {
        int index = -1;

        for (int i = 0; i < this.size; i++) {
            if (o.equals(this.array[i])) {
                index = i;
                break;
            }
        }

        return index;
    }

    //Removes object at specified index from underlying array, we will then
    //need to shift the remaining elements up in the index order to fill in the gap
    public void remove(int index) {
        for (int i = index; i < this.size - 1; i++) {
            this.array[i] = this.array[i + 1];
        }

        this.size--;
    }

    //Returns size of array - the one greater than the last index, not maxSize
    public int size() {
        return this.size;
    }

    //Doubles the size of the contained array by creating a new array and copying
    //contents of old array into it
    private void growArray() {
        this.maxSize = this.maxSize * 2;
        Object[] tempArray = this.array;
        this.array = new Object[this.maxSize];

        //Copy old array elements to new array
        for (int i = 0; i < this.size; i++) {
            this.array[i] = tempArray[i];
        }
    }
}
