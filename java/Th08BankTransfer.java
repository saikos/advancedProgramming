public class Th08BankTransfer extends Thread {
    
    private Th08BankAccount src, dest;
    private double amount;
  
    public Th08BankTransfer(Th08BankAccount src, Th08BankAccount dest, double amount) {
      super(src + "-to-" + dest); 
      this.src = src; 
      this.dest = dest; 
      this.amount = amount;
    }
  
    public void run() {
      System.out.println("Transfering from " + src + " to " + dest);
  
      synchronized(src) {
        try { //delay to allow the deadlock to occur
          Thread.sleep(100); 
        } 
        catch(InterruptedException ie) {}
  
        synchronized(dest) {
  
          System.out.println("Withdrawing " + amount + " from " + src);
          src.withdraw(amount);
          System.out.println("Depositing " + amount + " into " + dest);
          dest.deposit(amount);
        }
      }
  
    }

    public static void main(String[] args) {
        Th08BankAccount acc1 = new Th08BankAccount(1, 1000.0d);
        Th08BankAccount acc2 = new Th08BankAccount(2, 500.0d);
    
        Th08BankTransfer t1 = new Th08BankTransfer(acc1, acc2, 100.0d);
        Th08BankTransfer t2 = new Th08BankTransfer(acc2, acc1, 200.0d);
    
        t1.start();
        t2.start();
      }
}
