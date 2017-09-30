package app.controllers;

import javafx.stage.Stage;

import java.util.Stack;

public class StageController {
    private Stack<Stage> stages;
    private Stage currentStage;
    public static StageController stageController = null;

    public void pushStage(Stage stage) {
        System.out.println(stages.size());
        stages.push(stage);
        stage.show();
    }

    private StageController() {
        stages=new Stack<Stage>();
    }

    public static StageController getStageController() {
        if (stageController == null) {
            stageController = new StageController();
        }
        return stageController;
    }

    public void back() {
        if (!stages.empty()) {
            currentStage.close();
            currentStage = stages.pop();
        }
    }
}
