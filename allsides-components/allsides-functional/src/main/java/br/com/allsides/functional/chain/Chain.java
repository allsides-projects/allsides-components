package br.com.allsides.functional.chain;

import br.com.allsides.functional.Mapper;
import br.com.allsides.functional.Observer;
import br.com.allsides.functional.Performer;
import br.com.allsides.functional.Provider;

public interface Chain<T> {

    Chain<T> onSuccess(Observer<T> action);

    Chain<T> onError(Observer<Exception> action);
    
    Chain<T> onComplete(Performer action);

    <U> Chain<U> map(Mapper<? super T, U> action);

    <U> Chain<U> flatMap(Mapper<? super T, Chain<U>> action);

    Chain<T> errorMap(Mapper<Exception, T> action);

    Chain<T> errorFlatMap(Mapper<Exception, Chain<T>> classError);

    T get();
    
    static <U> Chain<U> lazy(Provider<U> supplier) {
        return LazyChain.of(supplier);
    }

}
