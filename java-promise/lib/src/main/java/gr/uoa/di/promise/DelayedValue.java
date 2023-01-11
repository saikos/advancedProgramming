package gr.uoa.di.promise;

import java.util.function.Consumer;

public class DelayedValue<V> implements PromiseExecutor<V> {

    public final V value;
    public final int delayMillis;

    public DelayedValue(V value, int delayMillis) {
        this.value = value;
        this.delayMillis = delayMillis;
    }

    public void execute(Consumer<V> resolve, Consumer<Throwable> reject) {

        new Thread(() -> {
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                System.out.println("Delayed value interrupted");
            }
            System.out.println("Delayed value resolves with " + value);
            resolve.accept(value);
        }).start();
    }

}
