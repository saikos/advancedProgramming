package gr.uoa.di.objectPool

import java.lang.reflect.Method

class Helper {

    private static final int DEFAULT_TIMEOUT = 3

    static SimplePool<Connection> newBrokenConnectionPool() {
        ConnectionFactory factory = new ConnectionFactory()
        Method invalidMethod = String.class.getMethod("toString", null)
        return SimplePool.newInstance(factory, List.of(invalidMethod), new Pool.Settings(1, 0))
    }

    static SimplePool<Connection> newConnectionPool(int poolSize, int queueSize, int timeout=DEFAULT_TIMEOUT) {
        return newConnectionPool(new Pool.Settings(poolSize, queueSize, timeout))
    }

    static SimplePool<Connection> newConnectionPool(Pool.Settings settings) {
        ConnectionFactory factory = new ConnectionFactory()
        Method closeMethod = Connection.class.getMethod("close", null)
        return SimplePool.newInstance(factory, List.of(closeMethod), settings)
    }
}
