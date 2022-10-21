import java.util.List;
import java.util.ArrayList;

public class GCTest {

    public static void main(String[] args) {
        int count = readCountFromArgs(args);
        Data data = new Data(count);
        data.run();
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

class Data implements Runnable {
    
    private final int count;    
    private final List<Integer> integers = new ArrayList<>();

    Data(int count) {
        this.count = count;        
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
        }
    }

    private void addInteger(int i) {
        Integer o = Integer.valueOf(i);
        integers.add(o);
        System.out.println(o);
    }
}