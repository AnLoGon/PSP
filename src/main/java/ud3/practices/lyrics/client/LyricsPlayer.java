package ud3.practices.lyrics.client;

import java.util.ArrayList;
import java.util.List;

public class LyricsPlayer {
    private final Player player;
    private final Loader loader;
    private final List<String> lines;
    private boolean end;

    public LyricsPlayer(String filename) {
        player = new Player(this);
        loader = new Loader(this, filename);
        lines = new ArrayList<>();
        end = false;
    }

    public synchronized void setEnd(boolean end) {
        this.end = end;
    }
    public synchronized boolean ended(int i) {
        return end && i >= lines.size();
    }

    public synchronized boolean isLineAvailable(int i){
        return i < lines.size();
    }

    public synchronized String getLine(int i) throws InterruptedException {
        while (!isLineAvailable(i)) wait();

        return lines.get(i);
    }

    public synchronized int addLine(String line){
        lines.add(line);
        notifyAll();
        return lines.size() - 1;
    }


    public void start(){
        player.start();
        loader.start();
    }
    public void join() throws InterruptedException {
        player.join();
        loader.join();
    }
    public void interrupt() {
        player.interrupt();
        loader.interrupt();
    }

    public static void main(String[] args) {
        LyricsPlayer lyricsPlayer = new LyricsPlayer("files/ud2/lyrics.txt");
        lyricsPlayer.start();

        try {
            lyricsPlayer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
