import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.FontMetrics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    final int GAME_WIDTH = 600;
    final int GAME_HEIGHT = 600;
    final int UNITS = 50;
    int game_units = (GAME_HEIGHT * GAME_WIDTH) / UNITS;
    int[] x_coordinates = new int[game_units];
    int[] y_coordinates = new int[game_units];

    //Game speed
    int speed = 100;
    int snakeBody = 6;
    //food coordinates
    int foodX;
    int foodY;
    char direction = 'r';
    char currentDirection = 'r';
    boolean running = false;
    Timer timer;
    Random random;
    Image MonImage = Toolkit.getDefaultToolkit().getImage("monster1.png");
    int imageGen;
    int monsterTurn;
    Image back1;
    Image snakeImproved;
    Image snakeBodyy = Toolkit.getDefaultToolkit().getImage("bodyr.png");
    Image scoreImage = Toolkit.getDefaultToolkit().getImage("score.png");
    Image gameoverImage = Toolkit.getDefaultToolkit().getImage("Gameover.png");

    String Doom;
    Integer score;
    JButton button = new JButton("Restart?");




    GamePanel()  {

        //Dimensions of panel
        this.setPreferredSize(new Dimension(GAME_WIDTH, 700));
        //Initialize score to 0;
        score =-1;
        //Color of panel
        this.setBackground(Color.darkGray);
        //Makes the key listener dependent on input allowed by the class we created below
        this.addKeyListener(new MyKeyAdapter());
        this.setFocusable(true);
        random = new Random();
        imageGen = random.nextInt(1, 6);
        Doom = "Only.wav";
        button.setFont(new Font("Princetown LET", Font.BOLD, 15));
        button.setBounds(GAME_WIDTH/2 -50,400,100,50);
        button.setBackground(new Color(172,50,50));
        button.addActionListener(e -> { new GameFrame();});
        startGame();
    }

    public void MenuGame(){

    }
    public void menuPainter(Graphics g){

    }


    public void startGame()  {
        running = true;
        createFood();
        timer = new Timer(speed, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);



            draw(g);


    }

    public void draw(Graphics g) {
        if (running) {
            g.setFont(new Font("Princetown LET", Font.BOLD, 75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());

            g.drawImage( randomImage(), 0, 0, GAME_WIDTH, GAME_HEIGHT, this);
            g.setColor(new Color(172,50,50));
            g.drawImage(scoreImage,0, GAME_HEIGHT,GAME_WIDTH, 100, this);
            g.drawString("" + score +"", 450,675);

            g.setColor(Color.red);
            g.drawImage(monsterRandomizer(), foodX, foodY, UNITS, UNITS, this);

            for (int i = 0; i < snakeBody; i++) {
                if (i == 0) {
                    g.setColor(new Color(220, 20, 60));
                    g.drawImage(snakeDirection(), x_coordinates[i], y_coordinates[i], UNITS, UNITS,
                            this);
                } else {
                    g.drawImage(snakeBodyy, x_coordinates[i], y_coordinates[i], UNITS, UNITS,
                            this);

                }
            }
        } else {
            gameOver(g);
        }


    }

    public void createFood() {
        //Randomly generate a point on the panel to create the food
        foodX = random.nextInt(GAME_WIDTH / UNITS) * UNITS;
        foodY = random.nextInt(GAME_HEIGHT / UNITS) * UNITS;
        monsterTurn = random.nextInt(1, 6);
        score++;

    }


    public void checkCollisions() {
        //Collisions with body
        for (int i = 1; i < snakeBody; i++) {
            if (x_coordinates[0] == x_coordinates[i] && y_coordinates[0] == y_coordinates[i]) {
                running = false;
            }
        }
        //left_border
        if (x_coordinates[0] < 0) {
            running = false;
        }
        //right_border
        if (x_coordinates[0] + UNITS > GAME_WIDTH) {
            running = false;
        }
        //bottom_border
        if (y_coordinates[0] < 0) {
            running = false;
        }
        if (y_coordinates[0] + UNITS > GAME_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }

    }

    public void checkFood() {
        for (int i = 0; i < snakeBody; i++) {
            if (x_coordinates[i] == foodX && y_coordinates[i] == foodY) {
                snakeBody++;
                createFood();
            }
            repaint();
        }


    }

    public void move() {
        for (int i = snakeBody; i > 0; i--) {
            x_coordinates[i] = x_coordinates[i - 1];
            y_coordinates[i] = y_coordinates[i - 1];
        }
        currentDirection = direction;
        switch (direction) {
            case 'u' -> y_coordinates[0] = y_coordinates[0] - UNITS;
            case 'd' -> y_coordinates[0] = y_coordinates[0] + UNITS;
            case 'l' -> x_coordinates[0] = x_coordinates[0] - UNITS;
            case 'r' -> x_coordinates[0] = x_coordinates[0] + UNITS;
            default -> {
            }
        }


    }

    public void gameOver(Graphics g)  {
        g.setColor(new Color(172,50,50));
        g.setFont(new Font("Princetown LET", Font.BOLD, 75));
        g.drawImage(gameoverImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, this);
        g.drawImage(scoreImage,0, GAME_HEIGHT,GAME_WIDTH, 100, this);
        g.drawString("" + score +"", 450,675);
    this.add(button);}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();


    }

    void playTune(String location) throws UnsupportedAudioFileException, IOException {
        File file = new File(location);
        try {
            AudioInputStream OST = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(OST);
            clip.start();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }




    public Image randomImage() {
        if (imageGen == 1) {
            back1 = Toolkit.getDefaultToolkit().getImage("back1.png");
        }
        if (imageGen == 2) {
            back1 = Toolkit.getDefaultToolkit().getImage("back2.png");
        }
        if (imageGen == 3) {
            back1 = Toolkit.getDefaultToolkit().getImage("back3.png");
        }
        if (imageGen == 4) {
            back1 = Toolkit.getDefaultToolkit().getImage("back4.png");
        }
        if (imageGen == 5) {
            back1 = Toolkit.getDefaultToolkit().getImage("back5.png");
        }


        return back1;
    }

    Image monsterRandomizer() {
        if (monsterTurn == 1) {
            MonImage = Toolkit.getDefaultToolkit().getImage("monster1.png");
        } else if ((monsterTurn == 2)) {
            MonImage = Toolkit.getDefaultToolkit().getImage("monster2.png");
        } else if ((monsterTurn == 4)) {
            MonImage = Toolkit.getDefaultToolkit().getImage("monster4.png");
        } else if ((monsterTurn == 5)) {
            MonImage = Toolkit.getDefaultToolkit().getImage("monster5.png");
        } else {
            MonImage = Toolkit.getDefaultToolkit().getImage("hero.png");
        }
        return MonImage;

    }

    Image snakeDirection() {
        if (direction == 'u') {
            snakeImproved = Toolkit.getDefaultToolkit().getImage("snakeHead.png");
        }
        if (direction == 'd') {
            snakeImproved = Toolkit.getDefaultToolkit().getImage("snakeD.png");
        }
        if (direction == 'l') {
            snakeImproved = Toolkit.getDefaultToolkit().getImage("snakeL.png");
        }
        if (direction == 'r') {
            snakeImproved = Toolkit.getDefaultToolkit().getImage("snakeR.png");
        }
        return snakeImproved;
    }

    public  void music(final String fileName, final SoundOptions mode) {
        Thread music = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            new File(fileName));

                    final Clip audioLineClip = (Clip) AudioSystem.getLine(
                            new Line.Info(Clip.class));
                    audioLineClip.open(inputStream);
                    audioLineClip.addLineListener(new LineListener() {
                        @Override
                        public void update(LineEvent event) {
                            LineEvent.Type type = event.getType();
                            if (type == LineEvent.Type.OPEN) {
                            } else if (type == LineEvent.Type.CLOSE) {
                                System.exit(0);
                            } else if (type == LineEvent.Type.START) {
                            } else if (type == LineEvent.Type.STOP) {
                                audioLineClip.close();
                            }
                        }
                    });

                    switch (mode) {
                        case Stop:
                            audioLineClip.stop();
                            break;
                        case Play:
                            audioLineClip.start();
                            break;
                        case Loop:
                            audioLineClip.loop(Clip.LOOP_CONTINUOUSLY);
                            break;
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });

        if (mode != SoundOptions.Stop) {
            music.start();

            synchronized (music) {
                while (true) {
                    try {
                        music.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            music.interrupt();
        }
    }






public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    if (direction != 'd') {
                        direction = 'u';
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != 'u') {
                        direction = 'd';
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'l') {
                        direction = 'r';
                    }
                }
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'r') {
                        direction = 'l';
                    }
                }
            }
        }

    }

}
enum SoundOptions {
    Play, Loop, Stop}
