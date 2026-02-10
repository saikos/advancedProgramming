package gr.uoa.di.objectPool;

public interface Connection {
    public int getId();

    public void execute(String command);

    public void close();
}
