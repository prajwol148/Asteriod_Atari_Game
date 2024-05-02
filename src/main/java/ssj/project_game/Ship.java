package ssj.project_game;


import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;



public class Ship {
    private Polygon character;
    private Point2D point2D;

    public Ship(int x, int y){
        this.character= new Polygon(-5, -5, 10, 0, -5, 5);
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        this.point2D= new Point2D(0,0);
    }
    public Polygon getCharacter() {
        return character;
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 5);
    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 5);
    }

    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.point2D.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.point2D.getY());
    }
}
