public class Th03ThreadAnonInnerClass {
    public static void main(final String[] args) {
        if (args!=null && args.length==1) {
          Thread t = new Thread() {
            public void run() {
              while(true) {
                System.out.println(args[0] + " from " + Thread.currentThread());
                try { Thread.sleep(1000); }
                catch(InterruptedException ie) {System.out.println("Interrupted");}
              }
            }
          };
          t.start();
        }
        else {
          System.out.println("Please provide a message");
        }
      }
}
