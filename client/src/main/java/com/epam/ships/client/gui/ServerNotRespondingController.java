package com.epam.ships.client.gui;

import com.epam.ships.client.client.Client;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ServerNotRespondingController {

    private static final Target logger = new SharedLogger(Client.class);

    @FXML
    AnchorPane mainAnchorPane;

    @FXML
    private void onTryAgainPressed() {
        try {
            String connectWindowURL = "/fxml/connectWindow.fxml";
            FXMLLoader connectLoader = new FXMLLoader(getClass().getResource(connectWindowURL));
            Parent connectWindow = connectLoader.load();
            Pane mainPane = (Pane) mainAnchorPane.getParent();
            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(connectWindow);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
