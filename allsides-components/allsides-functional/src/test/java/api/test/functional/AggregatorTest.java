package api.test.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

import br.com.allsides.functional.Aggregator;
import br.com.allsides.functional.Mapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AggregatorTest {

    @Test
    void delegatesToApply() {
        BiFunction<Integer, Integer,  String> function = Aggregator.newBiFunction((x, y) -> "anything: " + x + y);
        assertEquals("anything: 3030", function.apply(30, 30));
    }
    
    @Test
    void toMapper() throws Exception {
        Function<Integer, Integer> power = x -> x * x;
        Function<Integer, String> aggregateUpperCaseArgReturn = Aggregator
                .toFunction(power, (x, y) -> y.map(s -> x + ":" + s));
        String result = aggregateUpperCaseArgReturn.apply(3);
        assertEquals("3:9", result);
    }
    
    @Test
    void throwRuntimeException() {
        BiFunction<Integer, Integer,  String> function = Aggregator.newBiFunction((x, y) -> {
            throw new IllegalArgumentException("an error");
        });
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> function.apply(21, 21));
        assertEquals("an error", exception.getMessage());
    }

    @Test
    void converExceptionToRuntimeException() {
        BiFunction<Integer, Integer,  String> function = Aggregator.newBiFunction((x, y) -> {
            throw new Exception("an error");
        });
        RuntimeException exception = assertThrows(RuntimeException.class, () -> function.apply(21, 21));
        assertEquals("java.lang.Exception: an error", exception.getMessage());
    }

}
