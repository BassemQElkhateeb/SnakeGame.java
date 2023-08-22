import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SnakeGame {

    public static void main(String[] args) {
                GameFrame gameFrame1 = new GameFrame();
                gameFrame1.gamePanel.music("only.wav", SoundOptions.Loop);


    }

}


