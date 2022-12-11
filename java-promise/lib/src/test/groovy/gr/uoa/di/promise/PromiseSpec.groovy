package gr.uoa.di.promise

import spock.lang.Specification
import spock.util.concurrent.AsyncConditions

import java.util.function.Consumer

class PromiseSpec extends Specification {

    AsyncConditions conditions = new AsyncConditions()

    def "Promise constructor works correctly (with then)"() {
        given:
        PromiseExecutor<String> delayedString = PromiseExecutors.delayedResolve("DONE", 1000)

        when:
        new Promise(delayedString).then( (str) -> {
            conditions.evaluate { assert str == delayedString.value }
        })

        then:
        conditions.await(3)
    }

    def "Promise constructor works correctly (with catchError)"() {
        given:
        PromiseExecutor<Void> delayedError = PromiseExecutors.delayedReject(new RuntimeException("ERROR"), 1000)

        when:
        new Promise(delayedString).catchError( (err) -> {
            conditions.evaluate { assert err == delayedError.error }
        })

        then:
        conditions.await(3)
    }

    def "Promise constructor works correctly (with finally on success)"() {        
        given:
        String done = "DONE"
        Throwable error = new RuntimeException("ERROR")

        when:
        new Promise( (Consumer<String> resolve, Consumer<Throwable> reject) -> {
            resolve.accept(done)
            reject.accept(error)
        }).finally( (valueOrError) -> {
            conditions.evaluate { 
                assert valueOrError.value() == done  
                assert valueOrError.error() == null
            }
        })

        then:
        conditions.await(3)
    }

    def "Promise constructor works correctly (with finally on failure)"() {        
        given:
        String done = "DONE"
        Throwable error = new RuntimeException("ERROR")

        when:
        new Promise( (Consumer<String> resolve, Consumer<Throwable> reject) -> {
            reject.accept(error)
            resolve.accept(done)            
        }).finally( (valueOrError) -> {
            conditions.evaluate { 
                assert valueOrError.value() == null  
                assert valueOrError.error() == error
            }
        })

        then:
        conditions.await(3)
    }

    def "Promise.resolve(value) works correctly (with then)"() {
        when:
        Promise.resolve("test").then( (str) -> {
            conditions.evaluate { assert str == "test" }
        })

        then:
        conditions.await(3)
    }

    def "Promise.resolve(value) works correctly (with andFinally)"() {
        when:
        Promise.resolve("test").andFinally( (valueOrError) -> {
            conditions.evaluate { 
                assert valueOrError.value() == "test"  
                assert valueOrError.error() == null
            }
        })

        then:
        conditions.await(3)
    }

    def "Promise.reject(error) works correctly (with catchError)"() {
        given:
        Throwable t = new RuntimeException("Some kind of error")
        
        when: 
        Promise.reject(t).catchError( (error) -> {
            conditions.evaluate { assert error == t }
        })

        then:
        conditions.await(3)
    }

    def "Promise.reject(error) works correctly (with andFinally)"() {
        given:
        Throwable t = new RuntimeException("Some kind of error")
        
        when: 
        Promise.reject(t).andFinally( (valueOrError) -> {
            conditions.evaluate { 
                assert valueOrError.value() == null
                assert valueOrError.error() == t
            }
        })

        then:
        conditions.await(3)
    }
}
