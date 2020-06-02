package br.com.allsides.functional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class SimpleFunctionalIterator<T> implements FunctionalIterator<T> {

    private final int index;
    private final List<T> list;

    public SimpleFunctionalIterator(Collection<T> collection) {
        this(0, collection);
    }
    
    public SimpleFunctionalIterator(int index, Collection<T> collection) {
        this.list = Collections.unmodifiableList(new ArrayList<>(collection));
        this.index = index;
    }

    @Override
    public T first() {
        return list.get(index);
    }

    @Override
    public FunctionalIterator<T> tail() {
        return new SimpleFunctionalIterator<>(index + 1, list);
    }

    @Override
    public boolean hasElements() {
        return list.size() > index;
    }

    @Override
    public boolean isEmpty() {
        return !hasElements();
    }

}