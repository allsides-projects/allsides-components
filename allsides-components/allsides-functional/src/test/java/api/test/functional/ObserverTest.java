package api.test.functional;

import java.util.function.Consumer;

import br.com.allsides.functional.Observer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void throwRuntimeException() {
        Consumer<String> consumer = Observer.newConsumer(texto -> {
            throw new IllegalArgumentException(texto);
        });
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> consumer.accept("an error"));
        assertEquals("an error", exception.getMessage());
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
