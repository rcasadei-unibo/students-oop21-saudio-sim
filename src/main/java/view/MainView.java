package view;

import java.io.File;
import java.io.FileInputStream;

import controller.MainController;
import controller.view.MainControllerView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application {
    private static final String FXML_PATH = "src/main/resources/fxml/MainView.fxml";

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage mainStage) throws Exception {
        final MainController ctrMainApp = new MainController();
        mainStage.setTitle("Collector");

        final FXMLLoader loader = new FXMLLoader();
        final Parent root = loader.load(new FileInputStream(new File(FXML_PATH)));
        final MainControllerView ctrMainV = loader.getController();
        ctrMainV.setControllerApplication(ctrMainApp);

        final Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
