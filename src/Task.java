import java.util.function.Consumer;
import java.util.function.Predicate;

public class Task{
    static class LuckyThread extends Thread{
        private static Integer count = 0;
        private static Integer x = 0;
        private static final Object lock = new Object();


        @Override
        public void run(){
            while (true) {
                synchronized (lock) {
                    if ( x >= 999999 )
                        return;
                    x++;
                    if ( ((x % 10) + (x / 10) % 10 + (x / 100) % 10 == (x / 1000)
                            % 10 + (x / 10000) % 10 + (x / 100000) % 10) ) {
                        System.out.println(x);
                        count++;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{

        Thread t2 = new LuckyThread();
        Thread t1 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + LuckyThread.count);
    }
}