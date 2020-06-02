package api.test.functional;

import java.util.function.Function;

import br.com.allsides.functional.Mapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    @Test
    void delegatesToApply() {
        Function<Integer, String> function = Mapper.newFunction(numero -> "anything: " + numero);
        assertEquals("anything: 30", function.apply(30));
    }
    
    @Test
    void throwRuntimeException() {
        Function<Integer, String> function = Mapper.newFunction(numero -> {
            throw new IllegalArgumentException("an error");
        });
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> function.apply(21));
        assertEquals("an error", exception.getMessage());
    }
    
    @Test
    void aggregate() throws Exception {
        Mapper<Integer, Integer> power = x -> x * x;
        Mapper<Integer, String> powerAggregate = power.aggregate((arg, returnedValue) -> returnedValue.map(powered -> arg + ":" + powered));
        String result = powerAggregate.map(3);
        assertEquals("3:9", result);
    }

    @Test
    void converExceptionToRuntimeException() {
        Function<Integer, String> function = Mapper.newFunction(numero -> {
            throw new Exception("an error");
        });
        RuntimeException exception = assertThrows(RuntimeException.class, () -> function.apply(21));
        assertEquals("java.lang.Exception: an error", exception.getMessage());
    }

}
