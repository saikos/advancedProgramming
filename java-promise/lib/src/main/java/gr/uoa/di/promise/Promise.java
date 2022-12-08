package gr.uoa.di.promise;

import java.util.function.Consumer;
import java.util.function.Function;

/*
 * > "What I cannot create, I do not understand"
 * > Richard Feynman
 * > https://en.wikipedia.org/wiki/Richard_Feynman
 * 
 * This is an incomplete implementation of the Javascript Promise machinery in Java.
 * You should expand and ultimately complete it according to the following:
 * 
 * (1) You should only use the low-level Java concurrency primitives (like 
 * java.lang.Thread/Runnable, wait/notify, synchronized, volatile, etc)
 * in your implementation. 
 * 
 * (2) The members of the java.util.concurrent package 
 * (such as Executor, Future, CompletableFuture, etc.) cannot be used.
 * 
 * (3) No other library should be used.
 * 
 * (4) Create as many threads as you think appropriate and don't worry about
 * recycling them or implementing a Thread Pool.
 * 
 * (5) I may have missed something from the spec, so please report any issues
 * in the course's e-class.
 * 
 * The Javascript Promise reference is here:
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise
 * 
 * A helpful guide to help you understand Promises is available here:
 * https://javascript.info/async
 */
public class Promise<V> {

    public static enum Status {
        PENDING,
        FULLFILLED,
        REJECTED
    }

    // No instance fields are defined, perhaps you should add some!
    
    public Promise(PromiseExecutor<V> executor) {
        // Constructors that throw exceptions is a bad thing
        throw new UnsupportedOperationException("IMPLEMENT ME");        
    }

    public <T> Promise<ValueOrError<T>> then(Function<V, T> onResolve, Consumer<Throwable> onReject) {
        throw new UnsupportedOperationException("IMPLEMENT ME");
    }

    public <T> Promise<T> then(Function<V, T> onResolve) {
        throw new UnsupportedOperationException("IMPLEMENT ME");                
    }

    // catch is a reserved word in Java.
    public Promise<Throwable> catchError(Consumer<Throwable> onReject) {
        throw new UnsupportedOperationException("IMPLEMENT ME");
    }

    // finally is a reserved word in Java.
    public <T> Promise<ValueOrError<T>> andFinally(Consumer<ValueOrError<T>> onSettle) {
        throw new UnsupportedOperationException("IMPLEMENT ME");
    }

    public static <T> Promise<T> resolve(T value) {
        throw new UnsupportedOperationException("IMPLEMENT ME");
    }

    public static Promise<Throwable> reject(Throwable error) {
        throw new UnsupportedOperationException("IMPLEMENT ME");
    }

    public static <T> Promise<T> race(Iterable<Promise<?>> promises) {
        throw new UnsupportedOperationException("IMPLEMENT ME");
    }

    public static <T> Promise<T> any(Iterable<Promise<?>> promises) {
        throw new UnsupportedOperationException("IMPLEMENT ME");
    }

    public static <T> Promise<T> all(Iterable<Promise<?>> promises) {
        throw new UnsupportedOperationException("IMPLEMENT ME");
    }

    public static <T> Promise<T> allSettled(Iterable<Promise<?>> promises) {
        throw new UnsupportedOperationException("IMPLEMENT ME");
    }

}