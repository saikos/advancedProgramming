package gr.uoa.di.objectPool;

public interface Pool<T> {

    record Settings(int poolSize, int queueSize) {}

    T borrow();
    void putBack(T t);
}
