package br.com.allsides.functional;

public interface FunctionalIterator<T> {
    
    T first();
    
    FunctionalIterator<T> tail();
    
    boolean hasElements();
    
    boolean isEmpty();
   
}
