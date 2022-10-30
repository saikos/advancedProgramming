public class Th07Counter implements Decreasable, Increasable{

    private int counter;
  
    public synchronized void increase() {
      counter++;
    }
  
    public synchronized void decrease() {
      counter--;
    }
  
    public synchronized int get() {
      return counter;
    }
  }
  