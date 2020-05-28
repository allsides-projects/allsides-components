package api.test.functional;

import br.com.allsides.functional.Exchanger;
import br.com.allsides.functional.Mapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExchangerTest {

    @Test
    void delegatesMap() throws Exception {
        Exchanger<String> exchanger = text -> "exchange: " + text;
        Mapper<String, String> mapper = exchanger;
        String resultado = mapper.map("anything");
        assertEquals("exchange: anything", resultado);
    }

}
