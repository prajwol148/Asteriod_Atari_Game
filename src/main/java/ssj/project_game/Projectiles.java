package ssj.project_game;

import javafx.scene.shape.Polygon;

import java.util.List;

public class Projectiles extends Character{

    public Projectiles(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
    }
}
