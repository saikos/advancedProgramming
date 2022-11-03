public class Th08BankAccount {
  
  private double balance;
  public final int id;

  public Th08BankAccount(int id, double initialBalance) {
    this.id = id;
    this.balance = initialBalance;
  }

  public synchronized void deposit(double amount) {      
    balance = balance + amount;    
  }

  public synchronized void withdraw(double amount) {    
    balance = balance - amount;    
  }

  public String toString() { return "Account" + id; }

}
