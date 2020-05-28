package api.test.functional;

import java.util.function.Supplier;

import br.com.allsides.functional.Provider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest {

    @Test
    void delegatesToProvide() {
        Supplier<String> supplier = Provider.newSupplier(() -> "anything");
        assertEquals("anything", supplier.get());
    }
    
    @Test
    void throwRuntimeException() {
        Supplier<String> supplier = Provider.newSupplier(() -> {
            throw new IllegalArgumentException("an error");
        });
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, supplier::get);
        assertEquals("an error", exception.getMessage());
    }

    @Test
    void converExceptionToRuntimeException() {
        Supplier<String> supplier = Provider.newSupplier(() -> {
            throw new Exception("an error");
        });
        RuntimeException exception = assertThrows(RuntimeException.class, supplier::get);
        assertEquals("java.lang.Exception: an error", exception.getMessage());
    }

}
