package com.beigz.chesswinners;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class ChessWinnersApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PrintStream out = new PrintStream(
                new FileOutputStream("console.log", true), true);
        System.setOut(out);

        primaryStage.setTitle(AppConstants.WINDOW_TITLE);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/termsconditions.fxml"));
        Parent root = loader.load();
        EventController controller = loader.getController();
        controller.setHostServices(getHostServices());
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/app-icon.png")));
        primaryStage.show();
    }
}
