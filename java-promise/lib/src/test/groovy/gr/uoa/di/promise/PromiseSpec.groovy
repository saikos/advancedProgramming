package gr.uoa.di.promise

import spock.lang.Specification
import spock.util.concurrent.BlockingVariable
import spock.util.concurrent.BlockingVariables

import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class PromiseSpec extends Specification {

    def "01-Promise constructor works correctly with then"() {
        given:
        BlockingVariable<String> result = new BlockingVariable<>()
        PromiseExecutor<String> delayedString = new DelayedValue<>("DONE", 100)

        when:
        new Promise(delayedString).then( (String str) -> {
            result.set(str)
        })

        then:
        result.get() == delayedString.value
    }

    def "02-Promise constructor works correctly with catchError"() {
        given:
        BlockingVariable<Throwable> result = new BlockingVariable<>()
        PromiseExecutor<Void> delayedError = new DelayedError(new RuntimeException("ERROR"), 100)

        when:
        new Promise(delayedError).catchError( (Throwable err) -> {
            result.set(err)
        })

        then:
        result.get() == delayedError.error
    }

    def "03-Promise constructor works correctly with andFinally on success"() {
        given:
        BlockingVariable<ValueOrError<String>> result = new BlockingVariable<>()
        String done = "DONE"
        Throwable error = new RuntimeException("ERROR")

        when:
        new Promise( (Consumer<String> resolve, Consumer<Throwable> reject) -> {
            resolve.accept(done)
            reject.accept(error)
        }).andFinally( (ValueOrError<String> valueOrError) -> {
            result.set(valueOrError)
        })

        then:
        result.get().value() == done && !result.get().hasError()
    }

    def "04-Promise constructor works correctly with andFinally on failure"() {
        given:
        BlockingVariable<ValueOrError<String>> result = new BlockingVariable<>()
        String done = "DONE"
        Throwable error = new RuntimeException("ERROR")

        when:
        new Promise( (Consumer<String> resolve, Consumer<Throwable> reject) -> {
            reject.accept(error)
            resolve.accept(done)
        }).andFinally( (ValueOrError<String> valueOrError) -> {
            result.set(valueOrError)
        })

        then:
        result.get().value() == null && result.get().hasError() && result.get().error() == error
    }

    def "05-Promise.resolve(value) works correctly with then"() {
        given:
        BlockingVariable<String> result = new BlockingVariable<>()

        when:
        Promise.resolve("test").then( (String str) -> {
            result.set(str)
        })

        then:
        result.get() == "test"
    }

    def "06-Promise.resolve(value) works correctly with andFinally"() {
        given:
        BlockingVariable<ValueOrError<String>> result = new BlockingVariable<>()

        when:
        Promise.resolve("test").andFinally( (ValueOrError<String> valueOrError) -> {
            result.set(valueOrError)
        })

        then:
        result.get().value() == "test" && !result.get().hasError()
    }

    def "07-Promise.reject(error) works correctly with catchError"() {
        given:
        BlockingVariable<Throwable> result = new BlockingVariable<>()
        Throwable t = new RuntimeException("Some kind of error")

        when:
        Promise.reject(t).catchError( (Throwable error) -> {
            result.set(error)
        })

        then:
        result.get() == t
    }

    def "08-Promise.reject(error) works correctly with andFinally"() {
        given:
        BlockingVariable<ValueOrError<Void>> result = new BlockingVariable<>()
        Throwable t = new RuntimeException("Some kind of error")

        when:
        Promise.reject(t).andFinally( (ValueOrError<Void> valueOrError) -> {
            result.set(valueOrError)
        })

        then:
        result.get().value() == null && result.get().hasError() && result.get().error() == t
    }

    def "09-Promise chaining works correctly"() {
        given:
        BlockingVariable<Integer> result = new BlockingVariable<>()
        PromiseExecutor<String> delayedString = new DelayedValue<>("DONE", 100)

        when:
        new Promise(delayedString)
            .then( (String str) -> str.length())
            .then( (int i) -> i * 2)
            .andFinally ( (ValueOrError<Integer> valueOrError) -> {
                result.set(valueOrError.value())
            })

        then:
        result.get() == delayedString.value.length() * 2
    }

    def "10-Multiple thens work correctly"() {
        given:
        BlockingVariables result = new BlockingVariables()
        PromiseExecutor<String> delayedString = new DelayedValue<>("DONE", 100)

        when:
        Promise<String> promise = new Promise<>(delayedString)
        promise.then( (String str) -> result.setProperty("first", str.toLowerCase()))
        promise.then( (String str) -> result.setProperty("second", str.length()))
        promise.then( (String str) -> result.setProperty("third", str))

        then:
        result.getProperty("first") == "done" &&
        result.getProperty("second") == 4     &&
        result.getProperty("third") == "DONE"
    }

    def "11-Promise.all() works correctly when one rejects"() {
        given:
        BlockingVariable<Throwable> result = new BlockingVariable<>()

        when:
        Promise.all([
                Promise.resolve("string"),
                Promise.reject(new RuntimeException("Failed")),
                Promise.resolve(2.0)
        ]).catchError( t -> result.set(t) )

        then:
        result.get().getMessage() == "Failed"
    }

    def "12-Promise.any() works correctly when a promise fulfills"() {
        given:
        BlockingVariable<?> result = new BlockingVariable<>()

        when:
        Promise.any([
            Promise.reject(new RuntimeException("Failed")),
            Promise.resolve(1),
        ]).then( val -> result.set(val) )

        then:
        result.get() == 1
    }

    def "13-Promise.all() works correctly when all fulfill"() {
        given:
        BlockingVariable<List<Object>> result = new BlockingVariable<>()

        when:
        Promise.all([
            Promise.resolve("string"),
            Promise.resolve(1),
            Promise.resolve(2.0)
        ]).then( list -> result.set(list) )

        then:
        result.get() == ["string", 1, 2.0]
    }

    def "14-Promise.allSettled() works correctly"() {
        given:
        BlockingVariable<List<ValueOrError<?>>> result = new BlockingVariable<>()

        when:
        Promise.allSettled([
            Promise.reject(new RuntimeException("Failed")),
            Promise.resolve(1),
        ]).then( list -> result.set(list) )

        then:
        List<ValueOrError<?>> list = result.get()
        list[0].error().getMessage() == "Failed" &&
        list[1].value() == 1
    }

    def "15-Promise.race() works correctly"() {
        given:
        BlockingVariable<ValueOrError<?>> result = new BlockingVariable<>()

        when:
        Promise.race([
            new Promise<Void>(new DelayedError(new RuntimeException("Failed"), 1000)),
            Promise.resolve(1),
        ]).then( valueOrError -> result.set(valueOrError) )

        then:
        !result.get().hasError() && result.get().value() == 1
    }

    def "16-Advanced scenario works correctly"() {
        given:
        BlockingVariable<List<?>> result = new BlockingVariable<>()
        PromiseExecutor<String> delayedString = new DelayedValue<>("DONE", 1000)

        when:
        Promise<String> promise = new Promise<>(delayedString)
        Promise.all([
            promise.then( str -> str.toLowerCase()),
            promise.then( str -> str.length()),
            promise.then( str -> str)
        ]).then( list -> result.set(list))

        then:
        result.get() == ["done", 4, "DONE"]
    }
}
