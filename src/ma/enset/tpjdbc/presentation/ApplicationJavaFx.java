package ma.enset.tpjdbc.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ApplicationJavaFx extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TabPane root = FXMLLoader.load(getClass().getResource("view/mainView.fxml"));
        Scene scene = new Scene(root,700,500);
        scene.getStylesheets().add(getClass().getResource("css/style.css").toString());
        primaryStage.setTitle("Product Mnagement");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("view/img/logo.png")));
        //primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
