package com.epam.ships.client.gui;

import com.epam.ships.client.client.Client;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController {

    private static final Target logger = new SharedLogger(Client.class);

    @FXML
    private Pane mainPane;

    @FXML
    private ImageView imPolandFlag;

    @FXML
    private ImageView imEnglandFlag;

    @FXML
    void initialize(final Client client) {
        String connectWindowURL = "/fxml/connectWindow.fxml";

        try {
            FXMLLoader connectLoader = new FXMLLoader(getClass().getResource(connectWindowURL));
            Parent connect = connectLoader.load();
            StartWindowController startWindowController = connectLoader.getController();
            startWindowController.initialize(imPolandFlag, imEnglandFlag, client);

            mainPane.getChildren().clear();
            mainPane.getChildren().add(connect);
        } catch (IOException e) {
            logger.error(e.getMessage());
            //TODO: handle
        }
    }
}
