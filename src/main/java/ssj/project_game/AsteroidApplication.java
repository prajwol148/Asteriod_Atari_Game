package ssj.project_game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AsteroidApplication extends Application {
    public static int WIDTH = 300;
    public static int HEIGHT = 200;

    public void start(Stage stage) throws Exception{
        Pane pane= new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        //Creating ship
        Ship triangleShip= new Ship(WIDTH / 2, HEIGHT / 2);
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }


        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        pane.getChildren().add(triangleShip.getCharacter());



        Scene scene= new Scene(pane);

        HashMap<KeyCode, Boolean> keypressed= new HashMap<>();
        scene.setOnKeyPressed(event->{
                keypressed.put(event.getCode(), Boolean.TRUE);

        });
        scene.setOnKeyReleased(event->{
            keypressed.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if (keypressed.getOrDefault(KeyCode.LEFT, false)) {
                    triangleShip.turnLeft();
                }

                if (keypressed.getOrDefault(KeyCode.RIGHT, false)) {
                    triangleShip.turnRight();
                }

                if (keypressed.getOrDefault(KeyCode.UP, false)) {
                    triangleShip.accelerate();
                }

                triangleShip.move();
                asteroids.forEach(asteroid -> asteroid.move());

                asteroids.forEach(asteroid -> {
                    if (triangleShip.collide(asteroid)) {
                        stop();
                    }
                });

            }
        }.start();



        stage.setTitle("Asteriod - Atari Game");
        stage.setScene(scene);
        stage.show();
    }
}
