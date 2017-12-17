package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.io.IOException;

public class MainController {

    private static final Target logger = new SharedLogger(Client.class);

    @FXML
    private Pane mainPane;

    @FXML
    private Button mainEventButton;

    @Getter
    private Client client;

    @FXML
    public void initialize(final Client client) {
        final String connectWindowURL = "/fxml/connectWindow.fxml";
        this.client = client;;

        try {
            final FXMLLoader connectLoader = new FXMLLoader(getClass().getResource(connectWindowURL));
            final Parent connect = connectLoader.load();

            mainPane.getChildren().clear();
            mainPane.getChildren().add(connect);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
