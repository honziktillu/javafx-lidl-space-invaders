package sample;

import javafx.scene.paint.Paint;

public class Bullet {

    private double x = -10;
    private double y = 0;
    private double width = 4;
    private double height = 12;
    private double speedY = 4;
    private boolean isFriendly = false;
    private boolean isActive = false;

    public Bullet() {

    }

    public Bullet(double x, double y, boolean isFriendly) {
        this.x = x;
        this.y = y;
        this.isFriendly = isFriendly;
        if (isFriendly) {
            speedY *= -1;
        }
    }

    public void update() {
        y += speedY;
        if (isFriendly) {
            Controller.gc.setFill(Paint.valueOf("GREEN"));
        } else {
            Controller.gc.setFill(Paint.valueOf("WHITE"));
        }
        Controller.gc.fillRect(x, y, width, height);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
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
}
