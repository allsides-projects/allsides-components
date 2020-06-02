package br.com.allsides.functional;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static br.com.allsides.functional.TailCall.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleFunctionalIteratorTest {

    @Test
    void test() {
        SimpleFunctionalIterator<Integer> itr = new SimpleFunctionalIterator<>(Arrays.asList(1, 1, 2, 3, 5, 8));
        assertEquals(20, add(itr));
    }
    
    private static int add(FunctionalIterator<Integer> iterator) {
        return addTail(0, iterator).result();
    }

    private static TailCall<Integer> addTail(int sum, FunctionalIterator<Integer> iterator) {
        return iterator.isEmpty() 
            ? ret(sum) 
            : sus(() -> addTail(sum + iterator.first(), iterator.tail()));
    }

}
