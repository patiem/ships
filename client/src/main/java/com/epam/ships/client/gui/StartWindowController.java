package com.epam.ships.client.gui;

import com.epam.ships.client.client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StartWindowController {

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

    @FXML
    private void onConnectPressed() {
        final double imageCapacity = 0.4;

        gridPane.setDisable(true);
        imPolandFlag.setOpacity(imageCapacity);
        imEnglandFlag.setOpacity(imageCapacity);
        imCannon.setOpacity(imageCapacity);
        vbWheel.setVisible(true);

        String serverAddress = tServerAddress.getText();
        System.out.println("server address: " + serverAddress);

        int serverPort = Integer.parseInt(tServerPort.getText());

        System.out.println("server port: " + serverPort);

        Client client = new Client(serverAddress, serverPort);
        Thread clientThread = new Thread(client);
        clientThread.start();
    }
}
