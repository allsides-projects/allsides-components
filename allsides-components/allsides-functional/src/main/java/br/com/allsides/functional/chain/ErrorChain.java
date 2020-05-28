package br.com.allsides.functional.chain;

import br.com.allsides.functional.Mapper;
import br.com.allsides.functional.Observer;

class ErrorChain<T> extends GenericChain<T> {

    private final Exception exception;

    ErrorChain(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Chain<T> onSuccess(Observer<T> action) {
        return this;
    }

    @Override
    public <U> Chain<U> flatMap(Mapper<? super T, Chain<U>> f) {
        return new ErrorChain<>(exception);
    }

    @Override
    public Chain<T> errorFlatMap(Mapper<Exception, Chain<T>> action) {
        return ofChain(() -> action.map(exception));
    }

    @Override
    public Chain<T> onError(Observer<Exception> action) {
        action.accept(exception);
        return this;
    }

    @Override
    protected T getValue() throws Exception {
        throw exception;
    }

}
