package com.epam.ships.client.gui;

import com.epam.ships.client.client.Client;
import com.epam.ships.client.gui.controllers.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main class of gui to initialize and start gui thread.
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
public class GuiMain extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    Thread.currentThread().setName("gui - thread");
    final String fxmlMainWindowPath = "/fxml/root.fxml";
    final String windowTitle = "Battleships";
    final Client client = new Client();
    final int sceneWidth = 600;
    final int sceneHeight = 400;

    FXMLLoader rootLoader = new FXMLLoader(getClass().getResource(fxmlMainWindowPath));
    Parent root = rootLoader.load();
    MainController mainController = rootLoader.getController();
    mainController.initialize(client);

    primaryStage.setTitle(windowTitle);
    primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
    primaryStage.setResizable(false);
    primaryStage.show();

    primaryStage.setOnHiding((WindowEvent event) -> {
      client.closeClient();
      Platform.exit();
    });
  }

}
