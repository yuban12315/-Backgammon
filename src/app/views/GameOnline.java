package app.views;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/*棋盘界面*/

public class GameOnline {

    private Color colorChessboard = Color.valueOf("#FBE39B");
    private Color colorLine = Color.valueOf("#884B09");
    private Color colorMark = Color.valueOf("#FF7F27");

    public Stage getStage() {
        return stage;
    }

    private Stage stage = new Stage();

    public GameOnline() throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("gameO.fxml"));
        stage.setTitle("五子棋");
        stage.setScene(new Scene(root, 720, 720));
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //关闭连接
            }
        });
    }


}
