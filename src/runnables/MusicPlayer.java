package runnables;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer implements Runnable {

    private String musicPathAndFileName;
    private boolean toRepeat;
    private MediaPlayer mediaPlayer;
    
    public MusicPlayer(String musicPathAndFileName) {
        this.musicPathAndFileName = musicPathAndFileName;
        this.toRepeat = false;
    }
    
    public MusicPlayer(String musicPathAndFileName, boolean toRepeat) {
        this.musicPathAndFileName = musicPathAndFileName;
        this.toRepeat = toRepeat;
    }
    
    @Override
    public void run() {
        Media hit = new Media(new File(musicPathAndFileName).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        
        if (this.toRepeat) {
            mediaPlayer.setOnEndOfMedia(() -> {mediaPlayer.seek(Duration.ZERO);});
        }    
        mediaPlayer.play();
    }
    
    public void stopMusic() {
        mediaPlayer.stop();
    }
}
