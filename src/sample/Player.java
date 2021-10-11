package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

public class Player {
    private double width = 50;
    private double height = 20;
    private double x;
    private double y;
    private double speedX = 2;
    private Image image = new Image("res/player.png");
    private int hp = 3;
    private boolean bulletReady = true;

    public Player(double x, double y) {
        this.x = x / 2 - width / 2;
        this.y = y - height - 50;
    }

    public boolean isBulletReady() {
        return bulletReady;
    }

    public void setBulletReady(boolean bulletReady) {
        this.bulletReady = bulletReady;
    }

    public void update() {
        Controller.gc.drawImage(image, x, y);
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
