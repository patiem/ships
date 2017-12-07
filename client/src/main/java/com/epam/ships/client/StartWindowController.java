package com.epam.ships.client;

import javafx.fxml.FXML;
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
        gridPane.setDisable(true);
        imPolandFlag.setOpacity(0.4);
        imEnglandFlag.setOpacity(0.4);
        imCannon.setOpacity(0.4);
        vbWheel.setVisible(true);
    }
}
