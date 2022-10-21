import java.util.List;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class GCTestWithRefQ {    

    public static void main(String[] args) {
        ReferenceQueue<Integer> q = new ReferenceQueue<>();
        int count = readCountFromArgs(args);
        DataWithRefQ data = new DataWithRefQ(count, q);
        new Thread(data).start();
        int cleared = 0;
        while(true) {
            try {
                q.remove();                
                cleared++;                
                System.out.println("Integers cleared: " + cleared);                
                
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    private static int readCountFromArgs(String[] args) {
        if (args != null && args.length == 1) {
            return Integer.parseInt(args[0]);                        
        }
        else {
            return 0;
        }
    }
}

class DataWithRefQ implements Runnable {
    
    private final int count;
    private final ReferenceQueue<Integer> q;
    private final List<SoftReference<Integer>> integers = new ArrayList<>();

    DataWithRefQ(int count, ReferenceQueue<Integer> q) {
        this.count = count;
        this.q = q;
    }

    public void run() {
        try {
            if (count == 0) {
                allocateIndefinetely();
            }
            else {
                allocateUpToCount();
            }
        }
        catch(Throwable t) {
            System.out.println(t);            
        }        
    }

    void allocateUpToCount() {
        for (int i = 0; i < count; i++) {
            addInteger(i);
        }
    }

    void allocateIndefinetely() throws InterruptedException {
        int i = 0;
        while(true) {
            addInteger(i++);
            if (i % 1000 == 0) Thread.sleep(5);
        }
    }

    private void addInteger(int i) {
        Integer o = Integer.valueOf(i);
        integers.add(new SoftReference<Integer>(o, q));
        System.out.println(o);        
    }
}