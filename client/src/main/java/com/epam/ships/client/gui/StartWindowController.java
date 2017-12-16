package com.epam.ships.client.gui;

import com.epam.ships.client.client.Client;
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

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView imPolandFlag;

    @FXML
    private ImageView imEnglandFlag;

    @FXML
    private ImageView imCannon;

    @FXML
    private VBox vbWheel;

    @FXML
    private TextField tServerAddress;

    @FXML
    private TextField tServerPort;

    private Client client;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private void onConnectPressed() {
        final double imageCapacity = 0.4;

        gridPane.setDisable(true);
        imPolandFlag.setOpacity(imageCapacity);
        imEnglandFlag.setOpacity(imageCapacity);
        imCannon.setOpacity(imageCapacity);
        vbWheel.setVisible(true);

        final String serverAddress = tServerAddress.getText();

        logger.info("server address: " + serverAddress);

        final int serverPort = Integer.valueOf(tServerPort.getText());
        //TODO: check

        logger.info("server port: " + serverPort);

        //TODO:check if client != null

        final boolean isConnect = client.connect(serverAddress, serverPort);

        if(!isConnect) {
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
        } else {
            Thread clientThread = new Thread(client);
            clientThread.start();
        }
    }

    void initialize(final ImageView imPolandFlag, final ImageView imEnglandFlag, final Client client) {
        this.imPolandFlag = imPolandFlag;
        this.imEnglandFlag = imEnglandFlag;
        this.client = client;
    }
}
