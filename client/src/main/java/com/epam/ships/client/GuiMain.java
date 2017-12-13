package com.epam.ships.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        final String fxmlMainWindowPath = "/fxml/sample.fxml";
        final String windowTitle = "Battleships";
        
        int sceneWidth = 600;
        int sceneHeight = 400;
        
        Parent root = FXMLLoader.load(getClass().getResource(fxmlMainWindowPath));
        primaryStage.setTitle(windowTitle);
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.setResizable(false);
        
        primaryStage.show();
    }
}
