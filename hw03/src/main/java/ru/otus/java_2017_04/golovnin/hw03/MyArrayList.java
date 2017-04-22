package ru.otus.java_2017_04.golovnin.hw03;

import java.util.*;

public class MyArrayList<T> implements List<T>{

    private static final int DEFAULT_CAPACITY = 16;

    private int size;
    private Object[] array;

    public MyArrayList(){
        this(DEFAULT_CAPACITY);
    }

    public MyArrayList(int capacity){
        super();
        if(capacity < 0) throw new IllegalArgumentException("Capacity argument is less than zero.");

        this.array = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        boolean result = false;
        for(Object obj : this)
        {
            if(obj.equals(o)){
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new InnerListIterator(0);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size());
    }

    @Override
    public boolean add(T o) {
        add(size(), o);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(size(), c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        boolean result = false;
        if((long)this.size() + (long)c.size() < Integer.MAX_VALUE) {
            Object[] arrayToAdd = c.toArray();
            if (arrayToAdd.length > (array.length - size())) {
                Object[] grownArray = new Object[size() + c.size()];
                System.arraycopy(array, 0, grownArray, 0, index);
                System.arraycopy(arrayToAdd, 0, grownArray, index, arrayToAdd.length);
                System.arraycopy(array, index, grownArray, index + arrayToAdd.length, this.size() - index);
                array = grownArray;
            }
            else {
                System.arraycopy(array, index, array, index + arrayToAdd.length, this.size() - index);
                System.arraycopy(arrayToAdd, 0, array, index, arrayToAdd.length);
            }
            size += arrayToAdd.length;
            result = true;
        }
        return result;
    }

    @Override
    public void clear() {
        this.forEach(x -> x = null);
        size = 0;
    }


    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T)array[index];
    }


    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size());
        }
        T previousElement = (T)array[index];
        array[index] = element;
        return previousElement;
    }


    private static final int MAX_SIZE = Integer.MAX_VALUE - 1;

    @Override
    public void add(int index, T element) {
        if(index < 0 || index > size()) {
            throw  new IndexOutOfBoundsException("Index value:" + index + "\nList size: " + size());
        }

        if(size() == array.length){

            if(size() == MAX_SIZE) throw new IllegalStateException();
            Object[] newArray = new Object[array.length * 2 + 1];
            System.arraycopy(array, 0, newArray, 0, index);
            newArray[index] = element;
            System.arraycopy(array, index, newArray, index + 1, size() - index);
            array = newArray;

        }
        else {
            System.arraycopy(array, index, array, index + 1, size() - index);
            array[index] = element;
        }
        size++;
    }


    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if(index < 0 || index > size()) {
            throw  new IndexOutOfBoundsException("Index value:" + index + "\nList size: " + size());
        }
        T element = (T)array[index];
        System.arraycopy(array, index + 1, array, index, size() - index - 1);
        size--;

        return element;
    }

    @Override
    public int indexOf(Object o) {
        int result = -1;
        ListIterator iterator = listIterator();
        while (iterator.hasNext()){
            if(iterator.next().equals(o)){
                result = iterator.previousIndex();
                break;
            }
        }
        return result;
    }

    @Override
    public int lastIndexOf(Object o) {
        int result = -1;
        ListIterator iterator = listIterator(size());
        while (iterator.hasPrevious()){
            if(iterator.previous().equals(o)){
                result = iterator.nextIndex();
            }
        }
        return result;
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new InnerListIterator(index);
    }

    private class InnerListIterator implements ListIterator<T>{

        private int cursor = 0;
        private int lastIterated = -1;

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public void remove() {
            if(lastIterated == -1) throw new IllegalStateException();
            else {
                MyArrayList.this.remove(lastIterated);
                lastIterated = -1;
                cursor--;
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor < size()) {
                T element = (T)array[cursor];
                lastIterated = cursor;
                cursor++;
                return element;
            }
            else throw new NoSuchElementException();
        }

        public InnerListIterator(int index){
            super();
            if(index < 0 || index > size()) {
                throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size());
            }
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor - 1 >= 0 ;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T previous() {
            if (cursor - 1 >= 0) {
                cursor--;
                lastIterated = cursor;
                return (T)array[cursor];
            }
            else throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T t) {
            if(lastIterated == -1) throw new IllegalStateException();
            else MyArrayList.this.set(lastIterated, t);
        }

        @Override
        public void add(T t) {
            if(cursor == size()) MyArrayList.this.add(t);
            else MyArrayList.this.add(cursor, t);
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean result = true;
        if(this.isEmpty()) result = false;
        else {
            for (Object cElement : c) {
                if(cElement.getClass() != this.get(0).getClass()){
                    throw new ClassCastException();
                }
                else {
                    if(!this.contains(cElement)) {
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        throw new UnsupportedOperationException();
    }

    public String toString(){
        StringBuilder builder = new StringBuilder(3*size() + 2);
        builder.append("[");
        if(size() > 0) {
            for (int index = 0; index < size() - 1; index++) {
                builder.append(array[index]).append(", ");
            }
            builder.append(array[size() - 1]);
        }
        builder.append("]");
        return builder.toString();
    }
}
