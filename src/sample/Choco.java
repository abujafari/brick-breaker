package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

public class Choco implements Serializable {
    public Rectangle choco;

    public double x;
    public double y;
    public long timeCreated;
    public boolean taken = false;

    public Choco(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;


        draw();

    }

    private void draw() {
        choco = new Rectangle();
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        String url;
        if (new Random().nextInt(20) % 2 == 0) {
            url = "sample/assets/c1.png";
        } else {
            url = "sample/assets/c2.png";
        }

        choco.setFill(new ImagePattern(new Image(url)));
    }



}
