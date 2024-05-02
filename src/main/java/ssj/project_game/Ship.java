package ssj.project_game;


import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;



public class Ship extends Character{
    private Polygon character;
    private Point2D point2D;

    public Ship(int x, int y){
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
    }
}
