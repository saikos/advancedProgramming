package gr.uoa.di.promise;

import java.util.function.Consumer;

@FunctionalInterface
public interface PromiseExecutor<V> {
    void execute(Consumer<V> resolve, Consumer<Throwable> reject);
}
