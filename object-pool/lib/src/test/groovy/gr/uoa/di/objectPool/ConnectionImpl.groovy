package gr.uoa.di.objectPool

class ConnectionImpl implements Connection {

    private final int id;

    ConnectionImpl(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }

    void execute(String command) {
        Thread.sleep(1000) //Artificial delay to simulate the execution of a command
        System.out.println("CONNECTION " + id + " executed " + command)
    }

    void close() {
        System.out.println("CONNECTION " + id + " closed")
    }
}
