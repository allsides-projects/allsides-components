package br.com.allsides.functional;

import java.util.function.Function;

import br.com.allsides.functional.chain.Chain;

public interface Mapper<T, R> extends Function<T, R> {

    R map(T t) throws Exception;

    @Override
    default R apply(T t) {
        try {
            return this.map(t);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    default <Z, W> Mapper<T, W> aggregate(Aggregator<T, Chain<R>, Chain<W>> aggregator) {
        return arg -> {
            return aggregator.aggregate(arg, Chain.lazy(this.toProvider(arg))).get();
        };
    }

    static <V, Z> Mapper<V, Z> of(Mapper<V, Z> tradutor) {
        return tradutor;
    }

    static <V, Z> Function<V, Z> newFunction(Mapper<V, Z> tradutor) {
        return of(tradutor);
    }

    default Provider<R> toProvider(T t) {
        return () -> this.map(t);
    }

}
