package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    public Canvas canvas;
    public static GraphicsContext gc;
    private Player player;
    private AnimationTimer animationTimer;
    private ArrayList<String> input;
    private Timer timer;
    private Timer bulletTimer;
    private Bullet playersBullet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        player = new Player(canvas.getWidth(), canvas.getHeight());
        input = new ArrayList<>();
        playersBullet = new Bullet(-10, 0, true);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handleInput();
                collision();
                draw();
            }
        };
        timer = new Timer();
        bulletTimer = new Timer();
        startGame();
    }

    private void startGame() {
        Alien.spawn();
        animationTimer.start();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(Alien::updateAliens);
            }
        }, 0, 2000);
        bulletTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                int s = Alien.aliens.size();
                int randomNumber = random.nextInt(s);
                newRandomBullet(randomNumber);
            }
        }, 1000, 100);
    }

    private void newRandomBullet(int randomNumber) {
        Alien alien = Alien.aliens.get(randomNumber);
        if (alien.isActive() && !alien.bullet.isActive()) {
            alien.bullet.setX(alien.getX() + alien.getWidth() / 2);
            alien.bullet.setY(alien.getY());
            alien.bullet.setFriendly(false);
            alien.bullet.setActive(true);
        } else {
            if (randomNumber == Alien.aliens.size() - 1) {
                return;
            }
            randomNumber++;
            newRandomBullet(randomNumber);
        }
    }

    private void draw() {
        gc.setFill(Paint.valueOf("BLACK"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        player.update();
        Alien.drawAliens();
        Alien.updateBullets();
        if (!player.isBulletReady()) {
            playersBullet.update();
        }
        gc.setFill(Paint.valueOf("WHITE"));
        gc.setFont(new Font(24));
        gc.fillText(String.valueOf(player.getHp()), 10, canvas.getHeight() - 50);
        if (player.getHp() == 0) {
            gc.setFont(new Font(48));
            gc.fillText("GAME OVER", canvas.getWidth() / 2 - 125, canvas.getHeight() / 2);
            return;
        }
        if (Alien.aliens.size() == Alien.killedAliens) {
            gc.setFont(new Font(48));
            gc.fillText("GG LOL XDDDDDD", canvas.getWidth() / 2 - 175, canvas.getHeight() / 2);
        }
    }

    private void handleInput() {
        if (input.contains("A")) {
            if (player.getX() - player.getSpeedX() <= 0) {
                return;
            }
            player.setX(player.getX() - player.getSpeedX());

        }
        if (input.contains("D")) {
            if (player.getX() + player.getWidth() >= canvas.getWidth()) {
                return;
            }
            player.setX(player.getX() + player.getSpeedX());
        }
        if (input.contains("SPACE") && player.isBulletReady()) {
            playersBullet.setX(player.getX() + player.getWidth() / 2);
            playersBullet.setY(player.getY());
            player.setBulletReady(false);
        }
    }

    private void collision() {
        Alien.aliens.forEach(alien -> {
            if (alien.getY() > canvas.getHeight()) {
                player.setHp(0);
                return;
            }
            if (
                    alien.isActive() &&
                            playersBullet.getX() < alien.getX() + alien.getWidth() &&
                            playersBullet.getX() + playersBullet.getWidth() > alien.getX() &&
                            playersBullet.getY() < alien.getY() + alien.getHeight() &&
                            playersBullet.getY() + playersBullet.getHeight() > alien.getY()
            ) {
                alien.setActive(false);
                Alien.killedAliens++;
                player.setBulletReady(true);
                playersBullet.setX(-10);
                playersBullet.setY(0);
            } else if (playersBullet.getY() < 0) {
                player.setBulletReady(true);
                playersBullet.setX(-10);
                playersBullet.setY(0);
            }

            if (
                    alien.bullet.getX() < player.getX() + player.getWidth() &&
                            alien.bullet.getX() + alien.bullet.getWidth() > player.getX() &&
                            alien.bullet.getY() < player.getY() + player.getHeight() &&
                            alien.bullet.getY() + alien.bullet.getHeight() > player.getY()
            ) {
                if (player.getHp() != 0) {
                    player.setHp(player.getHp() - 1);
                }
                alien.bullet.setX(-10);
                alien.bullet.setY(0);
                alien.bullet.setActive(false);
            } else if (alien.bullet.getY() > canvas.getHeight()) {
                alien.bullet.setX(-10);
                alien.bullet.setY(0);
                alien.bullet.setActive(false);
            }
        });
    }

    public void keyPressed(KeyEvent keyEvent) {
        String code = keyEvent.getCode().toString();
        if (!input.contains(code)) {
            input.add(code);
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        input.remove(keyEvent.getCode().toString());
    }
}
