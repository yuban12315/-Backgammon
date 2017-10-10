package app.controllers;

import app.views.GameOffline;
import app.views.GameOnline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartCtrl {
    @FXML
    Button OfflineBtn;
    @FXML
    Button OnlineBtn;

    @FXML
    protected void offlineGame() throws Exception{
        System.out.println("单机游戏");
      StageController stageController=StageController.getInstance();
        GameOffline gameOffline=new GameOffline();
        stageController.pushStage(gameOffline.getStage());
    }

    @FXML
    protected void onlineGame() throws Exception{
        System.out.println("联机游戏");
        StageController stageController=StageController.getInstance();
        GameOnline gameOnline=new GameOnline();
        stageController.pushStage(gameOnline.getStage());
    }

    @FXML
    protected void exit(){
        System.out.println("退出");
        StageController stageController=StageController.getInstance();
        stageController.back();
    }

}
