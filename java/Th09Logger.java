import java.util.List;
import java.util.ArrayList;

public class Th09Logger implements Runnable {

    private final List<String> events = new ArrayList<>();
  
    public void log(String event) {
      System.out.println("[PRODUCING] " + event);  
      synchronized (events) {
        events.add(event); //produce
        events.notify();
      }
    }  
  
    public void run() {      
        while (!Thread.currentThread().isInterrupted()) {          
            synchronized (events) {
                try {
                    while (events.isEmpty()) {
                        events.wait();
                    }
                    String line = (String) events.remove(events.size() - 1);
                    doLog(line);
                }
                catch (InterruptedException ex) {
                    System.out.println(ex);
                    //We restore the interrupted status
                    Thread.currentThread().interrupt();
                }
            }
        }    
        System.out.println("Logger thread interrupted, exiting");  
    }  
  
    private void doLog(String event) {
      System.out.println("[CONSUMING] " + event);
      System.out.println(event);
      try {
        Thread.sleep(100); // artificial consumption delay 
      }
      catch (InterruptedException ie) {}
    }
  
}
