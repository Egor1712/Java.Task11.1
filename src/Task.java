public class Task{
    static class LuckyThread extends Thread{
        private final ConcurrentInteger count;
        private final ConcurrentInteger x;

        public LuckyThread(ConcurrentInteger count, ConcurrentInteger x){
            this.count = count;
            this.x = x;
        }

        @Override
        public void run(){
            while (true) {
                if ( !x.tryPrintIfValid() )
                    return;
                count.increment();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ConcurrentInteger count = new ConcurrentInteger(0);
        ConcurrentInteger value = new ConcurrentInteger(0);
        Thread t2 = new LuckyThread(count, value);
        Thread t1 = new LuckyThread(count, value);
        Thread t3 = new LuckyThread(count, value);
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count.getValue());
    }
}

class ConcurrentInteger{
    private int value;

    public ConcurrentInteger(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public synchronized void increment(){
        value++;
    }

    public synchronized boolean tryPrintIfValid(){
        if ( value >= 999999 )
            return false;
        increment();
        if ( (value % 10) + (value / 10) % 10 + (value / 100) % 10 == (value / 1000)
                % 10 + (value / 10000) % 10 + (value / 100000) % 10 )
            System.out.println(value);
        return true;
    }
}