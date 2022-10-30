public class SimpleCounter implements Decreasable, Increasable{

    private int counter;

    public void increase() {
        counter++;
    }

    public void decrease() {
        counter--;
    }

    public int get() {
        return counter;
    }
}