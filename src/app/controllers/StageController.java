package app.controllers;

import javafx.stage.Stage;

import java.util.Stack;

public class StageController {
    private Stack<Stage> stages;
    private Stage currentStage;
    public static StageController stageController = null;

    public void pushStage(Stage stage) {
        stages.push(stage);
        currentStage=stage;
        stage.show();

    }


    private StageController() {
        stages=new Stack<Stage>();
    }

    public static StageController getInstance() {
        if (stageController == null) {
            stageController = new StageController();
        }
        return stageController;
    }

    public void back() {
        if (!stages.empty()) {
            currentStage.close();
            currentStage = stages.pop();
            System.out.println(stages.size());
        }
        if (stages.empty()){
            currentStage.close();
        }
    }

    public void pop(){
        if(!stages.empty()){
            stages.pop();
        }
    }
}
