package app.views;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Start {

    private Stage stage;
    public Start( ) throws Exception{
        stage=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("五子棋---开始界面");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("ss");
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}
