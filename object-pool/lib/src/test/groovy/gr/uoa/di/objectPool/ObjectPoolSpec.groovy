package gr.uoa.di.objectPool

import org.spockframework.runtime.SpockTimeoutError
import spock.lang.Specification
import spock.util.concurrent.BlockingVariable

class ObjectPoolSpec extends Specification {

    def "01 - Cannot create a new Connection Pool with invalid putBack method(s)"() {
        when:
        Helper.newBrokenConnectionPool()

        then:
        thrown(RuntimeException.class)
    }

    def "02 - Cannot create a new Connection Pool with invalid settings"() {
        setup:
        Helper.newConnectionPool(poolSize, queueSize)

        expect:
        thrown(RuntimeException.class)

        where:
        poolSize | queueSize
        -1       | 0    //negative pool size
        0        | 0    //zero pool size
        1        | -1   //negative queue size

    }

    def "03 - A connection pool reuses connection objects"() {
        given:
        SimplePool<Connection> pool = Helper.newConnectionPool(1, 0)
        Connection con1 = pool.borrow()
        int id = con1.getId()

        when:
        con1.close()

        then:
        pool.borrow().getId() == id
    }

    def "04 - An exhausted pool (without a queue) throws exception on borrow"() {
        given:
        SimplePool<Connection> pool = Helper.newConnectionPool(1, 0)
        pool.borrow()

        when:
        pool.borrow()

        then:
        thrown(RuntimeException.class)
    }

    def "05 - An exhausted pool (with a queue) blocks on borrow"() {

        given:
        BlockingVariable<Connection> blockingVariable = new BlockingVariable<>()
        SimplePool<Connection> pool = Helper.newConnectionPool(1, 1)
        pool.borrow()

        when:
        Connection secondCon = pool.borrow()
        blockingVariable.set(secondCon)

        then:
        thrown(SpockTimeoutError.class)
    }
}
