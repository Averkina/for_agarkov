package Task1;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FirstThread implements Runnable {

    volatile boolean pleaseStop;

    public static SenderMessage message = new SenderMessage();
    public static Timer timer = new Timer();

    static public String randomString() {
        StringBuilder builder = new StringBuilder();
        Random r = new Random();
        final int length = 10 + r.nextInt(64 - 1);
        for (int i = 0; i < length; ++i) {
            builder.append((char)('0' + r.nextInt(10)));
        }
        return builder.toString();
    }

    // TimerTask task = new TimerTask() {

    public void run() {

        while (true) {

            synchronized (message) {

                try {

                    message.notify();

                    FirstThread.message.wait();

                    message.isSend = false;

                    System.out.println("Сообщение получено");

                    FirstThread.message.wait();

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }
    }

    // };

    public static void main(String[] args) {

        new Thread(new FirstThread()).start();

        new Thread(new SecondThread()).start();

        int n = 4;

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
                n);

//        for (int i = 0; i < n; i++) {
//            executor.scheduleAtFixedRate(new FirstThread(), i, 1,
//                    TimeUnit.SECONDS);
//        }

        // TimerTask task = new TimerTask() {
        // public void run() {
        //
        // new Thread(new FirstThread()).start();
        //
        // new Thread(new SecondThread()).start();
        // }
        // };
        //
        // timer.schedule(task, 1000);

    }
}
