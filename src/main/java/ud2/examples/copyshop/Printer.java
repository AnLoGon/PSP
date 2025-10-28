package ud2.examples.copyshop;

public class Printer {
    private String model;
    private int delay;

    public Printer(String model, int delay) {
        this.model = model;
        this.delay = delay;
    }

    public String getModel() {
        return model;
    }

    public void printDocument(Document d){
        try {
            int milis = d.getPages()*delay;
            Thread.sleep(milis);
            System.out.printf("Impresora %s: Documento %s imprimido con %d páginas (%.2f segundos).\n",
                    model,
                    d.getTitle(),
                    d.getPages(),
                    milis/1000.0
            );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}