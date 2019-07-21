package gui.graph.sinus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public void start(Stage primaryStage)
    {
        SinusModel sinusModel = new SinusModel();
        SinusPresenter sinusPresenter = new SinusPresenter(sinusModel);
        SinusView sinusView = new SinusView(sinusPresenter);

        sinusPresenter.setSinusView(sinusView);
        sinusView.initView();

        Scene scene = new Scene(sinusPresenter.getSinusView(), 1280, 840);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sinus");
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
