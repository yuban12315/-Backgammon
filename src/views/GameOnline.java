package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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

        Parent root = FXMLLoader.load(getClass().getResource("StartMenu.fxml"));
        stage.setTitle("五子棋");
        stage.setScene(new Scene(root, 740, 520));
        stage.setResizable(false);
    }


}
