package gr.uoa.di.promise

import spock.lang.Specification
import spock.util.concurrent.AsyncConditions

class PromiseSpec extends Specification {

    AsyncConditions conditions = new AsyncConditions()

    def "Promise.resolve(value) works correctly"() {
        when:
        Promise.resolve("test").then( (str) -> {
            conditions.evaluate { assert str == "test" }
        })

        then:
        conditions.await(3)
    }
}
