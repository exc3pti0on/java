public class MyRunnable implements Runnable {
    public void run() {
        System.out.println("ID потока: " + Thread.currentThread().getId());
    }
    public static void main(String[] args) {
        
        MyRunnable runnableOne = new MyRunnable();
        MyRunnable runnableTwo = new MyRunnable();
        MyRunnable runnableThree = new MyRunnable();
        
        Thread threadOne = new Thread(runnableOne);
        Thread threadTwo = new Thread(runnableTwo);
        Thread threadThree = new Thread(runnableThree);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
