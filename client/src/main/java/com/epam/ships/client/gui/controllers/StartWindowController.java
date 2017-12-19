package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.client.gui.events.OpponentConnectedEvent;
import com.epam.ships.client.validators.PortValidator;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Magda
 * @since 2017-12-14
 */

public class StartWindowController {

    private static final Target logger = new SharedLogger(Client.class);

    private Client client;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView imCannon;

    @FXML
    private VBox vbWheel;

    @FXML
    private TextField tServerAddress;

    @FXML
    private TextField tServerPort;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Label lInvalidPort;

    @FXML
    private Button eventButton;

    private PortValidator portValidator;

    @FXML
    void initialize() {
        tServerPort.textProperty().addListener((observableValue, s, t1) -> {
            if(!lInvalidPort.getText().isEmpty()) {
                lInvalidPort.setText("");
            }
        });

        portValidator = new PortValidator();

        eventButton.addEventHandler(OpponentConnectedEvent.OPPONENT_CONNECTED,
                (EventHandler<Event>) event -> loadGameWindow());

        final String defaultHost = "127.0.0.1";
        final String defaultPort = "8189";

        tServerAddress.setText(defaultHost);
        tServerPort.setText(defaultPort);
    }

    private void initializeClient() throws IllegalStateException {
        MainController mainController = (MainController) mainAnchorPane.getParent().getUserData();
        this.client = mainController.getClient();
        if(client == null) {
            throw new IllegalStateException("client is not initialized!");
        }

        client.setEventTrigger(eventButton);
    }

    @FXML
    private void onConnectPressed() {
        try {
            initializeClient();
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            return;
        }

        final String serverAddress = tServerAddress.getText();
        logger.info("server address: " + serverAddress);

        int port;
        try {
            port = portValidator.asInt(tServerPort.getText());
        } catch (IllegalArgumentException e) {
            lInvalidPort.setText("invalid port number");
            return;
        }
        logger.info("server port: " + port);

        showLoadingWheel();
        final boolean isConnected = client.connect(serverAddress, port);

        if (!isConnected) {
            loadServerNotResponseView();
        } else {
            Thread clientThread = new Thread(client);
            clientThread.start();
        }
    }

    private void showLoadingWheel() {
        final double opacity = 0.4;

        gridPane.setDisable(true);
        gridPane.setOpacity(opacity);
        imCannon.setOpacity(opacity);
        vbWheel.setVisible(true);
    }

    private void loadServerNotResponseView() {
        try {
            final String serverNotRespondingURL = "/fxml/serverNotResponding.fxml";
            final FXMLLoader notResponseLoader = new FXMLLoader(getClass().getResource(serverNotRespondingURL));
            final Parent notResponse = notResponseLoader.load();
            final Pane mainPane = (Pane) mainAnchorPane.getParent();
            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(notResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void loadGameWindow() {
        try {
            final String gameWindowURL = "/fxml/gameWindow.fxml";
            final FXMLLoader gameWindowLoader = new FXMLLoader(getClass().getResource(gameWindowURL));
            final Parent gameWindow = gameWindowLoader.load();
            final AnchorPane mainPane = (AnchorPane) mainAnchorPane.getParent();

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            Stage stage = (Stage) mainPane.getScene().getWindow();
            final int shrinkSize = 300;
            stage.setMinHeight(screenBounds.getHeight() - shrinkSize);
            stage.setMinWidth(screenBounds.getWidth() - shrinkSize);

            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(gameWindow);

            GameController mainController = gameWindowLoader.getController();
            mainController.initializeClient();

            final double margin = 0.0;
            AnchorPane.setTopAnchor(gameWindow, margin);
            AnchorPane.setBottomAnchor(gameWindow, margin);
            AnchorPane.setLeftAnchor(gameWindow, margin);
            AnchorPane.setRightAnchor(gameWindow, margin);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
