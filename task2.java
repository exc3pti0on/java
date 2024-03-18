import java.util.concurrent.CopyOnWriteArrayList;


class Reader implements Runnable {
    private CopyOnWriteArrayList<Integer> numList;
    public Reader(CopyOnWriteArrayList<Integer> numList) {
        this.numList = numList;
    }
    public void run() {
        try {
            while (true) {
                Thread.sleep(500);
                numList.forEach(number -> System.out.println(number));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
class Writer implements Runnable {
    private CopyOnWriteArrayList<Integer> numList;

    public Writer(CopyOnWriteArrayList<Integer> numList) {
        this.numList = numList;
    }

    public void run() {
        try {
            int num = 1;
            while (true) {
                Thread.sleep(1000);
                numList.add(num++);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();
        new Thread(new Reader(sharedList)).start();
        new Thread(new Writer(sharedList)).start();
    }
}
