package gr.uoa.di.promise;

import java.util.function.Consumer;

public class DelayedError implements PromiseExecutor<Void> {

    public final Throwable error;
    public final int delayMillis;

    public DelayedError(Throwable error, int delayMillis) {
        this.error = error;
        this.delayMillis = delayMillis;
    }

    public void execute(Consumer<Void> resolve, Consumer<Throwable> reject) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                System.out.println("Delayed error interrupted");
            }
            System.out.println("Delayed error rejects with " + error);
            reject.accept(error);
        }).start();
    }

}
