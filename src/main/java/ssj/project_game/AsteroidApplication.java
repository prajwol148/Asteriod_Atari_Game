package ssj.project_game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AsteroidApplication extends Application {
    public void start(Stage stage) throws Exception{
        Pane pane= new Pane();
        pane.setPrefSize(600,400);


        Scene scene= new Scene(pane);
        stage.setTitle("Asteriod- Atari Game");
        stage.setScene(scene);
        stage.show();
    }
}
