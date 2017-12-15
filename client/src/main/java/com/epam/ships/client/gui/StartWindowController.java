package com.epam.ships.client.gui;

import com.epam.ships.client.client.Client;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    void initialize(final Client client) {
        this.client = client;
    }

    @FXML
    private void onConnectPressed() {
        showLoadingWheel();

        final String serverAddress = tServerAddress.getText();

        logger.info("server address: " + serverAddress);

        final int serverPort = Integer.valueOf(tServerPort.getText());
        //TODO: check

        logger.info("server port: " + serverPort);

        //TODO:check if client != null

        final boolean isConnect = client.connect(serverAddress, serverPort);

        if(!isConnect) {
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
            String serverNotRespondingURL = "/fxml/serverNotResponse.fxml";
            FXMLLoader notResponseLoader = new FXMLLoader(getClass().getResource(serverNotRespondingURL));
            Parent notResponse = notResponseLoader.load();
            Pane mainPane = (Pane) mainAnchorPane.getParent();

            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(notResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
            //TODO: handle
        }
    }
}
