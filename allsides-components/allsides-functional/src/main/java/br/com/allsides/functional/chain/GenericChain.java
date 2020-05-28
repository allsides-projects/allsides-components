package br.com.allsides.functional.chain;

import java.util.ArrayList;
import java.util.List;

import br.com.allsides.functional.Mapper;
import br.com.allsides.functional.Performer;
import br.com.allsides.functional.Provider;

abstract class GenericChain<T> implements Chain<T> {
    
    List<Performer> onCompletes;
    
    GenericChain() {
        this(new ArrayList<>());
    }

    GenericChain(List<Performer> onCompletes) {
        this.onCompletes = new ArrayList<>(onCompletes);
    }

    @Override
    public <U> Chain<U> map(Mapper<? super T, U> action) {
        return flatMap(x -> ofSupplier(() -> action.map(x)));
    }

    @Override
    public Chain<T> errorMap(Mapper<Exception, T> action) {
        return errorFlatMap(x -> ofSupplier(() -> action.map(x)));
    }
    
    @Override
    public Chain<T> onComplete(Performer action) {
        onCompletes.add(action);
        return this;
    }
    
    @SuppressWarnings("unchecked")
    <U extends T, A extends Chain<U>> A addOnComplete(List<Performer> onCompletes) {
        this.onCompletes = onCompletes;
        return (A) this;
    }

    @Override
    public final T get() {
        try {
            return getValue();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            for (Performer performer : onCompletes) {
                performer.run();
            }
        }
    }
    
    <U> Chain<U> ofSupplier(Provider<U> supplier) {
        return newOfSupplier(supplier, onCompletes);
    }

    <U> Chain<U> ofChain(Provider<Chain<U>> supplier) {
        return newOfChain(supplier, onCompletes);
    }
    
    static <U> Chain<U> newOfSupplier(Provider<U> supplier) {
        return newOfSupplier(supplier, new ArrayList<>());
    }
    
    static <U> Chain<U> newOfSupplier(Provider<U> supplier, List<Performer> onCompletes) {
        return newOfChain(() -> {
            U result = supplier.provide();
            if (result == null) {
                throw new NullPointerException("supplier retornou objeto nulo");
            }
            return new SuccessChain<>(result).addOnComplete(onCompletes);
        }, onCompletes);
    }
    
    static <U> Chain<U> newOfChain(Provider<Chain<U>> supplier, List<Performer> onCompletes) {
        try {
            return supplier.provide();
        } catch (Exception e) {
            return new ErrorChain<>(e).addOnComplete(onCompletes);
        }
    }
    
    protected abstract T getValue() throws Exception;

}
