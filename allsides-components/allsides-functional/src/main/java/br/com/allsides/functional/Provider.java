package br.com.allsides.functional;

import java.util.function.Supplier;

public interface Provider<T> extends Supplier<T> {

    T provide() throws Exception;

    @Override
    default T get() {
        try {
            return this.provide();
        } catch (RuntimeException e) {
            throw e;
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <V> Provider<V> of(Provider<V> produtor) {
        return produtor;
    }

    static <V> Supplier<V> newSupplier(Provider<V> produtor) {
        return of(produtor);
    }

}
