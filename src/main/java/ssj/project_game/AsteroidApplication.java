package ssj.project_game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AsteroidApplication extends Application {
    public static int WIDTH = 800;
    public static int HEIGHT = 800;

    public void start(Stage stage) throws Exception{
        Pane pane= new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        Image backgroundImage = new Image("file:bg.jpg");
        if (backgroundImage.isError()) {
            System.out.println("Error loading background image.");
        } else {
            System.out.println("Background image loaded successfully.");
        }

        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(WIDTH);
        backgroundImageView.setFitHeight(HEIGHT);
        pane.getChildren().add(backgroundImageView);




        Text text = new Text(WIDTH / 2.2, 50, "Score: 0");
        text.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 30));
        text.setFill(javafx.scene.paint.Color.YELLOW);
        text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        AtomicInteger points = new AtomicInteger();

        pane.getChildren().add(text);

        //Creating ship
        Ship triangleShip= new Ship(WIDTH / 2, HEIGHT / 2);
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }


        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        pane.getChildren().add(triangleShip.getCharacter());

        ArrayList<Projectiles> projectiles = new ArrayList<>();



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

                if (Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
                    if (!asteroid.collide(triangleShip)) {
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }

                if (keypressed.getOrDefault(KeyCode.LEFT, false)) {
                    triangleShip.turnLeft();
                }

                if (keypressed.getOrDefault(KeyCode.RIGHT, false)) {
                    triangleShip.turnRight();
                }

                if (keypressed.getOrDefault(KeyCode.UP, false)) {
                    triangleShip.accelerate();
                }
                if (keypressed.getOrDefault(KeyCode.DOWN, false)) {
                    triangleShip.deaccelerate();
                }

                if (keypressed.getOrDefault(KeyCode.SPACE, false) && projectiles.size()<3) {
                    // we shoot
                    Projectiles projectile = new Projectiles((int) triangleShip.getCharacter().getTranslateX(), (int) triangleShip.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(triangleShip.getCharacter().getRotate());
                    projectiles.add(projectile);
                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));

                    pane.getChildren().add(projectile.getCharacter());
                }

                triangleShip.move();
                asteroids.forEach(asteroid -> asteroid.move());
                projectiles.forEach(projectile-> projectile.move());

                asteroids.forEach(asteroid -> {
                    if (triangleShip.collide(asteroid)) {
                        stop();
                    }
                });




                // Remove collided asteroids and projectiles
                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if(projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                    if(!projectile.isAlive()){
                        text.setText("Points: " + points.addAndGet(1));
                    }
                });

                projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
                projectiles.removeAll(projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .collect(Collectors.toList()));

                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroids.removeAll(asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));

            }
        }.start();

        stage.setTitle("Asteroid - Atari Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}