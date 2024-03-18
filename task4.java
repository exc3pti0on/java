import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ClinicSimulation {
    private ConcurrentLinkedQueue<Patient> queue = new ConcurrentLinkedQueue<>();
    private AtomicInteger maxQueueLength = new AtomicInteger(0);

    public static void main(String[] args) {
        ClinicSimulation clinic = new ClinicSimulation();
        clinic.startSimulation();
    }
    public void startSimulation() {
        new Thread(new Therapist()).start();
        new Thread(new MRT()).start();
        while (true) {
            try {
                Thread.sleep((long) (300 + Math.random() * 300));
                queue.add(new Patient());
                maxQueueLength.updateAndGet(x -> Math.max(x, queue.size()));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    class Therapist implements Runnable {
        public void run() {
            while (true) {
                if (!queue.isEmpty()) {
                    Patient patient = queue.poll();
                    patient.seeTherapist();
                }
            }
        }
    }
    class MRT implements Runnable {
        public void run() {
            while (true) {
                if (!queue.isEmpty()) {
                    Patient patient = queue.peek();
                    if (patient.isTherapistDone()) {
                        queue.poll();
                        patient.seeMRT();
                    }
                }
            }
        }
    }
    class Patient {
        private boolean therapistDone = false;
        public void seeTherapist() {
            try {
                Thread.sleep((long) (Math.random() * 1000));
                therapistDone = true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        public void seeMRT() {
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        public boolean isTherapistDone() {
            return therapistDone;
        }
    }
}
