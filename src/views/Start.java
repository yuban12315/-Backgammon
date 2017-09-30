package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.beans.EventHandler;

public class Start {

    private Stage stage;
    public Start( ) throws Exception{
        stage=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("五子棋---开始界面");
    }

    public Stage getStage() {
        return stage;
    }
}
