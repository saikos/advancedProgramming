package gr.uoa.di.objectPool;

import java.lang.reflect.Method;
import java.util.List;

public class SimplePool<T> implements Pool<T> {

    private final Factory<T> factory;
    private final List<Method> putBackMethods;
    private final Settings settings;

    private SimplePool(Factory<T> factory, List<Method> putBackMethods, Settings settings) {
        this.factory = factory;
        this.putBackMethods = putBackMethods;
        this.settings = settings;
    }

    public static <T> SimplePool<T> newInstance(Factory<T> factory, List<Method> putBackMethods, Settings settings) {
        throw new RuntimeException("Implement me");
    }

    @Override
    public T borrow() {
        throw new RuntimeException("Implement me");
    }

    @Override
    public void putBack(T t) {
        throw new RuntimeException("Implement me");
    }
}