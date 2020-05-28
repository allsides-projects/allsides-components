package br.com.allsides.functional;

import java.util.function.Consumer;

public interface Observer<T> extends Consumer<T> {

    void observe(T t) throws Exception;

    @Override
    default void accept(T t) {
        try {
            this.observe(t);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <V> Observer<V> of(Observer<V> consumidor) {
        return consumidor;
    }

    static <V> Consumer<V> newConsumer(Observer<V> consumidor) {
        return of(consumidor);
    }

}
