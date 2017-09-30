import controllers.StageController;
import controllers.StartCtrl;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import views.GameOnline;
import views.Start;

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
        } catch (Exception e) {
            //ignore this
        }
        System.exit(0);

    }
}
