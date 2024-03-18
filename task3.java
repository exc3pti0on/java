import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class MFC {
    private AtomicBoolean window1 = new AtomicBoolean(false);
    private AtomicBoolean window2 = new AtomicBoolean(false);
    private AtomicBoolean window3 = new AtomicBoolean(false);
    private AtomicInteger youngLeft = new AtomicInteger(0);
    private AtomicInteger elderlyLeft = new AtomicInteger(0);
    private AtomicInteger businessmenLeft = new AtomicInteger(0);
    private AtomicInteger youngTotal = new AtomicInteger(0);
    private AtomicInteger elderlyTotal = new AtomicInteger(0);
    private AtomicInteger businessmenTotal = new AtomicInteger(0);

    public void serveCustomer(int category) {
        boolean served = false;
        switch (category) {
            case 1: 
                youngTotal.incrementAndGet();
                served = tryServe(window1)  tryServe(window2)  tryServe(window3);
                if (!served) youngLeft.incrementAndGet();
                break;
            case 2: 
                elderlyTotal.incrementAndGet();
                served = tryServe(window2);
                if (!served) elderlyLeft.incrementAndGet();
                break;
            case 3:
                businessmenTotal.incrementAndGet();
                served = tryServe(window3);
                if (!served) businessmenLeft.incrementAndGet();
                break;
        }
    }
    private boolean tryServe(AtomicBoolean window) {
        if (!window.getAndSet(true)) {
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                window.set(false); 
            }
            return true;
        }
        return false;
    }
    public void printStatistics() {
        System.out.println("Процент ушедших молодых: " + (100.0 * youngLeft.get() / youngTotal.get()) + "%");
        System.out.println("Процент ушедших пожилых: " + (100.0 * elderlyLeft.get() / elderlyTotal.get()) + "%");
        System.out.println("Процент ушедших бизнесменов: " + (100.0 * businessmenLeft.get() / businessmenTotal.get()) + "%");
    }
    public static void main(String[] args) {
        MFC mfc = new MFC();
        for (int i = 0; i < 100; i++) {
            int category = (int) (Math.random() * 3) + 1;
            new Thread(() -> mfc.serveCustomer(category)).start();
        }
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        mfc.printStatistics();
    }
}
