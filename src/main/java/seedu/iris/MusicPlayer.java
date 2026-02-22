package seedu.iris;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Handles music player functions which includes resuming and pausing
 */
public class MusicPlayer {

    private MediaPlayer mediaPlayer;
    private Media bgm = new Media(this.getClass().getResource("/seedu/iris/music/bgm.mp3").toExternalForm());

    /**
     * Constructs MusicPlayer which sets the music as the mp3 file accordingly
     */
    public MusicPlayer() {
        mediaPlayer = new MediaPlayer(bgm);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.1);
    }

    public void play() {
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public boolean isPlaying() {
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}
