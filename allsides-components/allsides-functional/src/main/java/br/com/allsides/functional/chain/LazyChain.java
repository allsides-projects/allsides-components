package br.com.allsides.functional.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import br.com.allsides.functional.Mapper;
import br.com.allsides.functional.Observer;
import br.com.allsides.functional.Provider;

@SuppressWarnings("all")
class LazyChain<T> extends GenericChain<T> {

    private Chain<?> observable;
    private final List<Mapper<Chain<?>, Chain<?>>> mappers;

    public LazyChain(Provider<T> supplier) {
        this(supplier, new ArrayList<>());
    }

    public LazyChain(Provider<T> supplier, List<Mapper<Chain<?>, Chain<?>>> mappers) {
        this(newOfSupplier(supplier), mappers);
    }

    public LazyChain(Chain<?> observable, List<Mapper<Chain<?>, Chain<?>>> mappers) {
        this.observable = observable;
        this.mappers = mappers;
    }

    public static <U> Chain<U> of(Provider<U> supplier) {
        return new LazyChain(supplier);
    }

    @Override
    public <U> Chain<U> flatMap(Mapper<? super T, Chain<U>> action) {
        return (Chain<U>) add(observable -> observable.flatMap((Mapper) action));
    }

    private LazyChain<T> add(Mapper<Chain<?>, Chain<?>> mapper) {
        List<Mapper<Chain<?>, Chain<?>>> newMappers = new ArrayList<>();
        newMappers.addAll(mappers);
        newMappers.add(mapper);
        return new LazyChain<>(observable, newMappers).addOnComplete(this.onCompletes);
    }

    @Override
    public Chain<T> errorFlatMap(Mapper<Exception, Chain<T>> action) {
        return (Chain<T>) add(observable -> observable.errorFlatMap((Mapper) action));
    }

    @Override
    public Chain<T> onError(Observer<Exception> action) {
        return (Chain<T>) add(observable -> observable.onError((Observer) action));
    }

    @Override
    public Chain<T> onSuccess(Observer<T> action) {
        return (Chain<T>) add(observable -> observable.onSuccess((Observer) action));
    }

    @Override
    public T getValue() throws Exception {

        for (Mapper<Chain<?>, Chain<?>> mapper : mappers) {
            observable = mapper.map(observable);
        }
        return (T) observable.get();
    }

}
