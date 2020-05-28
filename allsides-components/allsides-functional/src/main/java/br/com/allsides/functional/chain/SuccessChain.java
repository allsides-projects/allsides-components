package br.com.allsides.functional.chain;

import br.com.allsides.functional.Mapper;
import br.com.allsides.functional.Observer;

class SuccessChain<T> extends GenericChain<T> {

    private final T result;

    SuccessChain(T result) {
        this.result = result;
    }

    @Override
    public Chain<T> onSuccess(Observer<T> action) {
        return ofChain(() -> {
            action.observe(result);
            return this;
        });
    }

    @Override
    public <U> Chain<U> flatMap(Mapper<? super T, Chain<U>> action) {
        return ofChain(() -> action.map(result));
    }

    @Override
    public Chain<T> errorFlatMap(Mapper<Exception, Chain<T>> f) {
        return this;
    }

    @Override
    public Chain<T> onError(Observer<Exception> action) {
        return this;
    }

    @Override
    public T getValue() {
        return result;
    }

}
