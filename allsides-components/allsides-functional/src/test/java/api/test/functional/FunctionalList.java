package api.test.functional;

public interface FunctionalList<T> {

    T first();

    T last();

    FunctionalList<T> head();

    FunctionalList<T> tail();
    
    FunctionalList<T> head(int limit);

    FunctionalList<T> tail(int limit);
    
    FunctionalList<T> concat(FunctionalList<T> otherList);
   
}
