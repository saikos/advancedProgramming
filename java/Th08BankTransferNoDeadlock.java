public class Th08BankTransferNoDeadlock extends Thread {
    private Th08BankAccount src, dest;
    private double amount;
  
    public Th08BankTransferNoDeadlock(Th08BankAccount src, Th08BankAccount dest, double amount) {
      super(src + "-to-" + dest); 
      this.src = src; 
      this.dest = dest; 
      this.amount = amount;
    }
  
    public void run() {
      System.out.println("Transfering from " + src + " to " + dest);
  
      //All bank transfers acquire locks based on the bank account's
      //id, in order to achieve an ordered locking
      Th08BankAccount first, second;
      if (src.id < dest.id) {
        first = src;
        second = dest;
      }
      else {
        first = dest;
        second = src;
      }
  
      synchronized(first) {
        try { //delay to allow the deadlock to occur
          Thread.sleep(100); 
        } 
        catch(InterruptedException ie) {}
  
        synchronized(second) {
  
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
  
      Th08BankTransferNoDeadlock t1 = new Th08BankTransferNoDeadlock(acc1, acc2, 100.0d);
      Th08BankTransferNoDeadlock t2 = new Th08BankTransferNoDeadlock(acc2, acc1, 200.0d);
  
      t1.start();
      t2.start();
    }    
}
