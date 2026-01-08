package gr.uoa.di.objectPool;

public interface Pool<T> {

    record Settings(int poolSize, int queueSize, int queueBlockingTimeoutInSeconds) {}

    T borrow();
    void putBack(T t);
}
