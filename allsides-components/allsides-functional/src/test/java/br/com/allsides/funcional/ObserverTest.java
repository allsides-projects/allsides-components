package br.com.allsides.funcional;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObserverTest {

    @Test
    void delegatesToObserve() {
        boolean[] delegates = new boolean[] {false};
        Consumer<String> consumer = Observer.newConsumer(text -> {
            delegates[0] = true;
        });
        consumer.accept("anything");
        assertTrue(delegates[0]);
    }

    @Test
    void converExceptionToRuntimeException() {
        Consumer<String> consumer = Observer.newConsumer(texto -> {
            throw new Exception(texto);
        });
        RuntimeException exception = assertThrows(RuntimeException.class, () -> consumer.accept("an error"));
        assertEquals("java.lang.Exception: an error", exception.getMessage());
    }

}
