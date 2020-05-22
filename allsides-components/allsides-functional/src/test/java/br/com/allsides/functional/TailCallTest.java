package br.com.allsides.functional;

import org.junit.jupiter.api.Test;

import static br.com.allsides.functional.TailCall.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TailCallTest {

    @Test
    void simpleTest() {
        int result = fibonacci(47);
        assertEquals(1836311903, result);
    }
    
    @Test
    void stackSafeTest() {
        int result = add(2, 10000000);
        assertEquals(10000002, result);
    }

    private static int add(int x, int y) {
        return addTail(x, y).result();
    }

    private static TailCall<Integer> addTail(int x, int y) {
        return y == 0 
            ? ret(x) 
            : sus(() -> addTail(x + 1, y - 1));
    }
    
    private static int fibonacci(int x) {
        return fibonacciTail(0,  1, x).result();
    }
   
    private static TailCall<Integer> fibonacciTail(int a, int b, int x) {
        return x <= 3 
            ? ret(a + b)
            : sus(() -> fibonacciTail(b, a + b, x - 1));
    }

}
