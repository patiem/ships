package com.epam.ships.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private void onConnectPressed() {
        final double imageCapacity = 0.4;

        gridPane.setDisable(true);
        imPolandFlag.setOpacity(imageCapacity);
        imEnglandFlag.setOpacity(imageCapacity);
        imCannon.setOpacity(imageCapacity);
        vbWheel.setVisible(true);
    }
}
