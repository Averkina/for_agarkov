package Task1;
 
import java.util.LinkedList;
 
public class MainCheck {
        static LinkedList<String> queueOne = new LinkedList<>();
        static LinkedList<String> queueTwo = new LinkedList<>();
 
        static class ThreadOne extends Thread {
                @Override
                public void run() {
                        while (true) {
                                synchronized (queueOne) {
                                        if (queueOne.isEmpty())
                                                try {
                                                        queueOne.wait();
                                                } catch (InterruptedException ignore) {
                                                }
                                        while (!queueOne.isEmpty()) {
                                                String m = queueOne.removeFirst();
                                                System.out.println("ThreadOne received: " + m);
                                                try {
                                                        Thread.sleep(1000);
                                                } catch (InterruptedException ignore) {
                                                }
                                                synchronized (queueTwo) {
                                                        queueTwo.add(createRandomMessage());
                                                        queueTwo.notify();
                                                }
                                        }
                                }
                        }
                }
        }
 
        static class ThreadTwo extends Thread {
                @Override
                public void run() {
                        while (true) {
                                synchronized (queueTwo) {
                                        if (queueTwo.isEmpty())
                                                try {
                                                        queueTwo.wait();
                                                } catch (InterruptedException ignore) {
                                                }
                                        while (!queueTwo.isEmpty()) {
                                                String m = queueTwo.removeFirst();
                                                System.out.println("ThreadTwo received: " + m);
                                                try {
                                                        Thread.sleep(1000);
                                                } catch (InterruptedException ignore) {
                                                }
                                                synchronized (queueOne) {
                                                        queueOne.add(createRandomMessage());
                                                        queueOne.notify();
                                                }
                                        }
                                }
                        }
                }
        }
 
        private static String createRandomMessage() {
                int len = (int) (10 + (Math.random() * 54));
                byte[] buf = new byte[len];
                for (int i = 0; i < len; i++) {
                        buf[i] = (byte) (0x41 + (Math.random() * 25));
                }
                return new String(buf);
        }
 
        public static void main(String[] args) throws InterruptedException {
                Thread t1 = new ThreadOne();
                Thread t2 = new ThreadTwo();
                t1.start();
                t2.start();
                queueOne.add("First message");
                synchronized (queueOne) {
                        queueOne.notify();
                }
                t1.join(); // wait for
                t2.join();
        }
}