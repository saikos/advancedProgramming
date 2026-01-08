package gr.uoa.di.objectPool

class ConnectionFactory implements Factory<Connection> {

    private volatile int counter = 0;

    @Override
    synchronized Connection create() {
        counter++;
        return new Connection(counter);
    }
}
