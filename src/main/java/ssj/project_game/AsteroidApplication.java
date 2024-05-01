package ssj.project_game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.HashMap;

public class AsteroidApplication extends Application {
    public void start(Stage stage) throws Exception{
        Pane pane= new Pane();
        pane.setPrefSize(600,400);

        //Creating ship
        Polygon triangleShip = new Polygon(-5, -5, 10, 0, -5, 5);
        triangleShip.setTranslateX(300);
        triangleShip.setTranslateY(200);



        pane.getChildren().add(triangleShip);


        Scene scene= new Scene(pane);

        HashMap<KeyCode, Boolean> keypressed= new HashMap<>();
        scene.setOnKeyPressed(event->{
                keypressed.put(event.getCode(), Boolean.TRUE);

        });
        scene.setOnKeyReleased(event->{
            keypressed.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer(){
            public void handle(long now){
                if(keypressed.getOrDefault(KeyCode.LEFT, false)){
                    triangleShip.setRotate(triangleShip.getRotate()-5);
                }
                if(keypressed.getOrDefault(KeyCode.RIGHT,false)){
                    triangleShip.setRotate(triangleShip.getRotate()+5);
                }
            }
        }.start();



        stage.setTitle("Asteriod- Atari Game");
        stage.setScene(scene);
        stage.show();
    }
}
