package br.com.allsides.funcional;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest {

    @Test
    void delegatesToProvide() {
        Supplier<String> supplier = Provider.newSupplier(() -> "anything");
        assertEquals("anything", supplier.get());
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
