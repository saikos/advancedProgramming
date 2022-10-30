public class Th01Runnable implements Runnable {

    private final String message;
    
    public Th01Runnable(String message) {this.message = message;}
    
    public void run() {
      while(true) {
        System.out.println(message + " from " + Thread.currentThread());
        try { Thread.sleep(1000);} 
        catch(InterruptedException ie) {System.out.println("Interrupted");}
      }
    }
    public static void main(String[] args) {
      if (args!=null && args.length==1) {
        new Thread(new Th01Runnable(args[0])).start();
      }
      else {
        System.out.println("Please provide a message");
      }
    }
}
