package br.com.allsides.functional;

public abstract class TailCall<T> {
    
    private TailCall() {

    }
    
    public T result() {
        return value();
    }
    
    abstract T value();
    
    public static <V> TailCall<V> ret(V t) {
        return new ReturnTailCall<>(t);
    }

    public static <V> TailCall<V> sus(Provider<TailCall<V>> s) {
        return new SuspendTailCall<>(s);
    }

    private static final class ReturnTailCall<T> extends TailCall<T> {

        private final T value;

        ReturnTailCall(T value) {
            this.value = value;
        }

        @Override
        T value() {
            return value;
        }
                
    }
    
    private static final class SuspendTailCall<T> extends TailCall<T> {

        private final Provider<TailCall<T>> resume;

        SuspendTailCall(Provider<TailCall<T>> resume) {
            this.resume = resume;
        }

        @Override
        T value() {
            TailCall<T> tailCall = next();
            while (tailCall instanceof SuspendTailCall) {
                tailCall = ((SuspendTailCall<T>)tailCall).next();
            }
            return tailCall.value();
        }
      
        private TailCall<T> next() {
            return resume.get();
        }

    }

}