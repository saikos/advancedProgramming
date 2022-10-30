public class Th02Thread extends Thread {
    
  private final String message;
  
  public Th02Thread(String message) {this.message = message;}
  
  public void run() {
    while(true) {
      System.out.println(message + " from " + Thread.currentThread());
      try {Thread.sleep(1000);}
      catch(InterruptedException ie) {System.out.println("Interrupted");}
    }
  }
  public static void main(String[] args) {
    if (args!=null && args.length==1) {
      new Th02Thread(args[0]).start();
    }
    else {
      System.out.println("Please provide a message");
    }
  }
}
