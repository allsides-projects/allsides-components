package api.test.functional;

import br.com.allsides.functional.Performer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerformerTest {

    @Test
    void delegatesToPerform() {
        boolean[] delegates = new boolean[] {false};
        Runnable runnable = Performer.newRunnable(() -> {
            delegates[0] = true;
        });
        runnable.run();
        assertTrue(delegates[0]);
    }
    
    @Test
    void throwRuntimeException() {
        Runnable runnable = Performer.newRunnable(() -> {
            throw new IllegalArgumentException("an error");
        });
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> runnable.run());
        assertEquals("an error", exception.getMessage());
    }

    @Test
    void converExceptionToRuntimeException() {
        Runnable runnable = Performer.newRunnable(() -> {
            throw new Exception("an error");
        });
        RuntimeException exception = assertThrows(RuntimeException.class, () -> runnable.run());
        assertEquals("java.lang.Exception: an error", exception.getMessage());
    }

}
