package br.com.allsides.funcional;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MapperTest {

    @Test
    void delegatesToApply() {
        Function<Integer, String> function = Mapper.newFunction(numero -> "anything: " + numero);
        assertEquals("anything: 30", function.apply(30));
    }

    @Test
    void converToRuntimeException() {
        Function<Integer, String> function = Mapper.newFunction(numero -> {
            throw new Exception("an error");
        });
        RuntimeException exception = assertThrows(RuntimeException.class, () -> function.apply(21));
        assertEquals("java.lang.Exception: an error", exception.getMessage());
    }

}
