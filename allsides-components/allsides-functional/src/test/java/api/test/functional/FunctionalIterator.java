package api.test.functional;

public interface FunctionalIterator<T> {

    T first();

    FunctionalIterator<T> head();

    boolean hasNext();
    
}
