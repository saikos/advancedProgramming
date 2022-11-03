public class Th09LogEvents {

    private static final int APP_THREADS = 3;
    private static final int MESSAGES_PER_THREAD = 5;

    public static void main(String[] args) {
        Th09Logger logger = new Th09Logger();
        Thread loggerThread = new Thread(logger);
        loggerThread.setDaemon(true);
        loggerThread.start();

        Thread[] appThreads = new Thread[APP_THREADS];
        for (int i = 0; i < APP_THREADS; i++) {
            Thread t = new Thread(new AppAction(logger, i));
            t.start();
            appThreads[i] = t;
        } 
    
        try {
          for (int i = 0, l = appThreads.length; i < l; i++) {
            appThreads[i].join();
          }          
          System.out.println("Production finished");
          Thread.sleep(1000);
          System.out.println("Waited enough - interrupt the consumer");
          loggerThread.interrupt();
          System.exit(0);
       }
        catch(InterruptedException ie) {}        
      }

    private static final class AppAction implements Runnable {

        private final Th09Logger logger;
        private final int index;

        AppAction(Th09Logger logger, int index) {
            this.logger = logger;
            this.index = index;
        }

        public void run() {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < MESSAGES_PER_THREAD; i++) {
                logger.log("App thread " + name + " message #" + (i + 1));
                try {
                    Thread.sleep(sleepAmount(index, i));
                }
                catch(InterruptedException ie) {}
            }
        }        
    }  

    private static final int sleepAmount(int index, int msg) {
        return ((index) * 10) + ((msg) * 10);
    }
}
