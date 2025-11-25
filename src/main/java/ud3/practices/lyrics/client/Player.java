package ud3.practices.lyrics.client;

public class Player extends Thread {
    private final LyricsPlayer lyricsPlayer;
    private int lyricsIndex;

    public Player(LyricsPlayer lyricsPlayer) {
        this.lyricsPlayer = lyricsPlayer;
        lyricsIndex = 0;
    }

    public void playLine(int i) throws InterruptedException {
        String[] line = lyricsPlayer.getLine(i).split(" ");

        for (int j = 0; j < line.length; j++) {
            Thread.sleep(500);
            if(j == 0)
                System.out.printf("%d: ", i);
            else
                System.out.print(" ");
            System.out.print(line[j]);
        }
        System.out.println();
    }

    @Override
    public void run() {
        try {
            while(!lyricsPlayer.ended(lyricsIndex)){
                this.playLine(lyricsIndex);
                lyricsIndex++;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            lyricsPlayer.interrupt();
        }
    }
}
