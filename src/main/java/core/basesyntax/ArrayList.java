package core.basesyntax;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayList<T> implements List<T> {
    private static final String OUT_OF_BOUNDS = "Index [%d] is out of bounds for list of size [%d]";
    private static final String NO_SUCH_ELEMENT = "No such element [%h] in this list";
    private static final String ILLEGAL_CAPACITY = "Illegal capacity: %d";
    private static final int DEFAULT_CAPACITY = 10;
    private T[] elements;
    private int size;

    public ArrayList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(String.format(ILLEGAL_CAPACITY, initialCapacity));
        }
        this.elements = (T[]) new Object[initialCapacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public void add(T value) {
        growIfArrayFull();
        elements[size++] = value;
    }

    @Override
    public void add(T value, int index) {
        checkingIndex(index, size + 1);
        growIfArrayFull();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = value;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkingIndex(index, size);
        return (T) elements[index];
    }

    @Override
    public void set(T value, int index) {
        checkingIndex(index, size);
        elements[index] = value;
    }

    @Override
    public T remove(int index) {
        checkingIndex(index, size);
        T removedElement = (T) elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
        return removedElement;
    }

    @Override
    public T remove(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == element || Objects.equals(elements[i], element)) {
                return remove(i);
            }
        }
        throw new NoSuchElementException(String.format(NO_SUCH_ELEMENT, element));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkingIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new ArrayListIndexOutOfBoundsException(String.format(OUT_OF_BOUNDS, index, size));
        }
    }

    private void growIfArrayFull() {
        if (size >= elements.length) {
            int newElementsLength = elements.length + Math.round((float) elements.length / 2) + 1;
            elements = Arrays.copyOf(elements, newElementsLength);
        }
    }
}
