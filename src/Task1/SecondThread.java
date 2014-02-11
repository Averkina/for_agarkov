package Task1;

public class SecondThread implements Runnable {

    public void run() {
        
        while (true) {

            
            synchronized (FirstThread.message) {

                FirstThread.message.isSend = true;
                
                System.out.println("Сообщение отправлено");

                
                FirstThread.message.notify();

                try {
                    
                    Thread.sleep(1000);
                    
                    System.out.println("Ждем 1 сек");
                    
                    FirstThread.message.wait();
                    
//                    SecondThread.this.wait();
                    

                } catch (InterruptedException e) {

                    e.printStackTrace(); 

                }
            }
        }
    }
}
