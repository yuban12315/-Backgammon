package app.views;

import app.controllers.StageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameOffline{
    public Stage getStage() {
        return stage;
    }

    private Stage stage = new Stage();

    public GameOffline()throws Exception{
        Parent root= FXMLLoader.load(getClass().getResource("gameF.fxml"));
        stage.setTitle("五子棋");
        stage.setScene(new Scene(root, 770, 900));
        stage.setResizable(false);
    }
}
