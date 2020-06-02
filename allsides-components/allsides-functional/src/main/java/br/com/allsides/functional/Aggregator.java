package br.com.allsides.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

import br.com.allsides.functional.chain.Chain;

public interface Aggregator<T, U, R> extends BiFunction<T, U, R> {

    R aggregate(T t, U u) throws Exception;

    @Override
    default R apply(T t, U u) {
        try {
            return this.aggregate(t, u);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    static <V, X, Z, W> Function<V, W> toFunction(Function<V, X> mapper, Aggregator<V, Chain<X>, Chain<W>> aggregator) {
        return arg -> {
            return aggregator.apply(arg, Chain.lazy(() -> mapper.apply(arg))).get();
        };
    }

    static <V, X, Z> Aggregator<V, X, Z> of(Aggregator<V, X, Z> tradutor) {
        return tradutor;
    }

    static <V, X, Z> BiFunction<V, X, Z> newBiFunction(Aggregator<V, X, Z> tradutor) {
        return of(tradutor);
    }

}
