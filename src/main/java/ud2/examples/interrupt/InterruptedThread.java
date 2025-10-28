package ud2.examples.interrupt;

class InterruptedThread extends Thread {
    public InterruptedThread(String name) {
        super(name);
    }

    @Override
    public void run(){
        try {
            for(int i = 1; i < 1000; i++) {
                System.out.printf("El hilo %s te saluda por %dª vez.\n",
                        Thread.currentThread().getName(), i
                );
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +" interrumpido.");
        }
    }
}