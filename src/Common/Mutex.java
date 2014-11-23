package Common;

public class Mutex {

    public synchronized void Acquire()
    {
        try {
            numberOfThreads++;
            System.out.println("Acquire: " + numberOfThreads);
            if (numberOfThreads > 1) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void Release()
    {
        if (numberOfThreads > 0) {
            numberOfThreads--;
            System.out.println("Release: " + numberOfThreads);
            if (numberOfThreads > 0) {
                this.notify();
            }
        }
    }

    private int numberOfThreads = 0;
}
