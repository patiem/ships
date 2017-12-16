package com.epam.ships.client.gui;

import com.epam.ships.client.client.Client;
import com.epam.ships.client.validators.PortValidator;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    private boolean getClient() {
        MainController mainController = (MainController) mainAnchorPane.getParent().getUserData();
        this.client = mainController.getClient();
        if(client == null) {
            logger.error("client is not initialized!");
            return false;
        }

        return true;
    }

    @FXML
    private void onConnectPressed() {
        if (!getClient()) {
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
            String serverNotRespondingURL = "/fxml/serverNotResponding.fxml";
            FXMLLoader notResponseLoader = new FXMLLoader(getClass().getResource(serverNotRespondingURL));
            Parent notResponse = notResponseLoader.load();
            Pane mainPane = (Pane) mainAnchorPane.getParent();
            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(notResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
