public class Th06Impatient {
    public static void main(String[] args) throws InterruptedException{
      Worker worker = new Worker();
      System.out.println("Starting the worker...");
      worker.start();
      System.out.println("Waiting for the worker to finish...");
      worker.join(3000);
      if (worker.isAlive()) {
        System.out.println("Worker has not finished -- call it to exit");
        worker.exit();
      }
    }
  
    private static final class Worker extends Thread {

      private volatile boolean exit;

      void exit() { 
        exit = true; 
        System.out.println("Exit is set to true");
      }
  
      public void run() {
        while(!exit) {
          try {
            System.out.println(this.getName() + ": Worker is working!");
            Thread.sleep(1000);
          }
          catch(InterruptedException ie) { return; }
        }
        System.out.println(this.getName() + ": Worker exits");
      }
    }
  }
  