package api.test.functional;

import br.com.allsides.functional.chain.Chain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unused")
class ChainTest {

    @Test
    void map() {
        String result = Chain.lazy(() -> "test")
        .map(s -> s + s)
        .get();
        Assertions.assertEquals("testtest", result);
    }
    
    @Test
    void checkNullValue() {
        String value = null;
        NullPointerException exception = assertThrows(NullPointerException.class, () -> 
            Chain.lazy(() -> value).get());
        assertEquals("supplier retornou objeto nulo", exception.getMessage());
    }
    
    @Test
    void errorMap() {
        String result = Chain.lazy(() -> "test")
        .errorMap(ex -> "ignore")
        .map(s -> {
            if (false) { 
                return "anything"; 
            }
            throw new RuntimeException("an error");
        })
        .errorMap(ex -> ex.getMessage())
        .get();
        Assertions.assertEquals("an error", result);
    }
    
    @Test
    void onSuccess() {
        String[] result = new String[] {""};
        Chain.lazy(() -> "test")
                .map(s -> s + s)
                .onSuccess(s -> result[0] = s)
                .map(s -> {
                    if (false) {
                        return "anything"; 
                    }
                    throw new Exception("an error");
                })
                .map(s -> "ignore")
                .onSuccess(s -> result[0] = s)
                .errorMap(Exception::getMessage)
                .get();
        Assertions.assertEquals("testtest", result[0]);
    }
    
    @Test
    void onError() {
        String[] result = new String[] {""};
        Chain.lazy(() -> "test")
                .map(s -> s + s)
                .onError(ex -> result[0] = ex.getMessage())
                .map(s -> {
                    if (false) {
                        return "anything"; 
                    }
                    throw new Exception("an error");
                })
                .onError(ex -> result[0] = ex.getMessage())
                .errorMap(Exception::getMessage)
                .get();
        Assertions.assertEquals("an error", result[0]);
    }
    
    @Test
    void onComplete() {
        String[] result = new String[] {""};
        Chain.lazy(() -> "test")
                .onComplete(() -> result[0] = "fineshed")
                .map(s -> s + s)
                .onError(ex -> result[0] = ex.getMessage())
                .map(s -> {
                    if (false) {
                        return "anything"; 
                    }
                    throw new Exception("an error");
                })
                .onError(ex -> result[0] = ex.getMessage())
                .errorMap(Exception::getMessage)
                .onComplete(() -> result[0] = result[0] + "fineshed")
                .get();
        Assertions.assertEquals("fineshedfineshed", result[0]);
    }
    
    @Test
    void converToRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            Chain.lazy(() -> "test")
                .map(s -> {
                    if (false) {
                        return "anything"; 
                    }
                    throw new Exception("an error");
                })
                .get());
        assertEquals("java.lang.Exception: an error", exception.getMessage());
    }

}
