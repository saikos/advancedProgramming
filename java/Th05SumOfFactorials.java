public class Th05SumOfFactorials implements Runnable {

    private static final long factorial(long l) {
      if (l == 0) return 1L;
      else return l * factorial(l-1);
    }
  
    public void run() {
      System.out.println("Calculating the sum of factorials...");
      long sumOfFactorials = 0L;
      int counter = 0;
      while (true) {
        sumOfFactorials = sumOfFactorials + factorial(counter);
        counter++;
        if ((counter % 10 == 0) && Thread.interrupted()) {
          System.out.printf("The sum of the first %d factorials is: %d\n", counter, sumOfFactorials);
          return;
        }
      }
    }
  
  
    public static void main(String[] args) throws InterruptedException {
  
      Thread calculator = new Thread(new Th05SumOfFactorials());
      calculator.start();
  
      Thread.sleep(1000);
      calculator.interrupt();
    }
}
  