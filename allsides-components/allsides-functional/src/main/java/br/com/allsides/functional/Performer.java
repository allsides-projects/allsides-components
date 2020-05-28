package br.com.allsides.functional;

public interface Performer extends Runnable {

    void perform() throws Exception;

    @Override
    default void run() {
        try {
            this.perform();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Performer of(Performer excecutante) {
        return excecutante;
    }

    static Runnable newRunnable(Performer executante) {
        return of(executante);
    }

}
