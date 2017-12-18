package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Magda
 * @since 2017-12-10
 */

public class GameController {

    private static final Target logger = new SharedLogger(Client.class);

    @FXML
    private GridPane yourBoard;

    @FXML
    private GridPane opponentBoard;

    @FXML
    private AnchorPane mainAnchorPane;


    @FXML
    public void initialize() {
        final NumberBinding rectanglesSize = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty().add(-50));
        final NumberBinding rectanglesSizeH = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty()).add(-50);

        final int boardSize = 10;

        for(int i = 0; i < boardSize; i++ ) {
            for(int j = 0; j < boardSize; j++) {
                final int shotIndex = j * boardSize + i;

                Rectangle yourRect = new Rectangle(15, 15, Color.GRAY);
                Rectangle opponentRect = new Rectangle(15, 15, Color.GRAY);

                opponentRect.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    opponentRect.setFill(Color.BLACK);
                    logger.info(shotIndex);
                    getClient().sendShot(shotIndex);
                });

                yourRect.widthProperty().bind(rectanglesSize.divide(boardSize));
                yourRect.heightProperty().bind(rectanglesSizeH.divide(boardSize));

                opponentRect.widthProperty().bind(rectanglesSize.divide(boardSize));
                opponentRect.heightProperty().bind(rectanglesSizeH.divide(boardSize));

                yourBoard.add(yourRect, i, j);
                opponentBoard.add(opponentRect, i, j);

                GridPane.setHalignment(yourRect, HPos.CENTER);
                GridPane.setHalignment(opponentRect, HPos.CENTER);
            }
        }
    }

    private Client getClient() {
        MainController mainController = (MainController) mainAnchorPane.getParent().getUserData();
        return mainController.getClient();
    }

}
