package tests;

import javafx.application.Application;
import javafx.stage.Stage;
import views.GameOnline;

public class GameTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GameOnline gameOnline =new GameOnline();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
