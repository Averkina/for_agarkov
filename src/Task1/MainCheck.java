package Task1;

import java.util.*;

public class MainCheck {

    static class PagerThread extends Thread {

        private LinkedList<String> inputQueue;
        private LinkedList<String> outputQueue;

        PagerThread(LinkedList<String> inputQueue, LinkedList<String> outputQueue) {
            this.inputQueue = inputQueue;
            this.outputQueue = outputQueue;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (inputQueue) {
                    if (inputQueue.isEmpty()) {
                        try {
                            inputQueue.wait();
                        } catch (InterruptedException ignore) {
                        }
                    }
                    while (!inputQueue.isEmpty()) {
                        String inputMessage = inputQueue.removeFirst();
                        System.out.println("Thread '" + this.getName() + "' received: " + inputMessage);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignore) {
                        }
                        synchronized (outputQueue) {
                            String outputMessage = createRandomMessage();
                            outputQueue.add(outputMessage);
                            System.out.println("Thread '" + this.getName() + "' sent: " + outputMessage);
                            outputQueue.notify();
                        }
                    }
                }
            }
        }
    }
    
    private static int randomNumber(int min, int max) {
        return (int)(min + Math.random() * (max - min + 1));
    }

    private static String createRandomMessage() {
        int len = randomNumber(10, 64);
        byte[] buf = new byte[len];
        for (int i = 0; i < len; i++) {
            buf[i] = (byte)(randomNumber('A', 'Z'));
        }
        return new String(buf);
    }

    public static void main(String[] args) throws InterruptedException {

        LinkedList<String> firstQueue = new LinkedList<>();
        LinkedList<String> secondQueue = new LinkedList<>();

        Thread secondThread = new PagerThread(firstQueue, secondQueue);
        secondThread.setName("Second");

        Thread firstThread = new PagerThread(secondQueue, firstQueue);
        firstThread.setName("First");

        firstThread.start();
        secondThread.start();

        firstQueue.add("First message");
        synchronized (firstQueue) {
            firstQueue.notify();
        }

        firstThread.join();
        secondThread.join();
    }
}
