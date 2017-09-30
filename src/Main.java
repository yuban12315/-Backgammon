import app.controllers.StageController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import app.views.Start;

public class Main extends Application implements EventHandler<WindowEvent> {
    public void start(Stage stage)throws Exception{
        StageController stageController=StageController.getStageController();
        Start start=new Start();
        stageController.pushStage(start.getStage());
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(WindowEvent event) {
        try {
            //NetService.getInstance(Controller.netType).close();
            System.out.println("ss");
        } catch (Exception e) {
            //ignore this
        }
        System.exit(0);

    }
}
