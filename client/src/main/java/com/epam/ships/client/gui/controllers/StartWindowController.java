package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.client.gui.controllers.MainController;
import com.epam.ships.client.validators.PortValidator;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
            client.sendMessage();
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
}
