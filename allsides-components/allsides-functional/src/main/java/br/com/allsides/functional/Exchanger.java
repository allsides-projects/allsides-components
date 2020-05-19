package br.com.allsides.functional;

public interface Exchanger<T> extends Mapper<T, T> {

    T exchange(T t) throws Exception;

    @Override
    default T map(T t) throws Exception {
        return exchange(t);
    }

}
