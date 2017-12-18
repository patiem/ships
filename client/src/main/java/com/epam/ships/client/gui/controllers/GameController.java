package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
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

    @FXML
    private GridPane yourBoard;

    @FXML
    private GridPane opponentBoard;

    @FXML
    private AnchorPane mainAnchorPane;


    @FXML
    public void initialize() {
        final NumberBinding yourBoardRectsAreaSize = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty().add(-50));
        final NumberBinding yourBoardRectsAreaSizeH = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty()).add(-50);

        final NumberBinding opponentBoardRectsAreaSize = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty().add(-50));
        final NumberBinding opponentBoardRectsAreaSizeH = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty()).add(-50);

        final int boardSize = 10;

        for(int i = 0; i < boardSize; i++ ) {
            for(int j = 0; j < boardSize; j++) {

                //hack
                final int ii = i;
                final int jj = j;

                Rectangle yourRect = new Rectangle(15, 15, Color.GRAY);
                Rectangle opponentRect = new Rectangle(15, 15, Color.GRAY);

                opponentRect.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    opponentRect.setFill(Color.BLACK);
                    final int shotIndex = jj * boardSize + ii;
                    System.out.println(shotIndex);
                    getClient().sendShot(shotIndex);
                });

                yourRect.widthProperty().bind(yourBoardRectsAreaSize.divide(boardSize));
                yourRect.heightProperty().bind(yourBoardRectsAreaSizeH.divide(boardSize));

                opponentRect.widthProperty().bind(opponentBoardRectsAreaSize.divide(boardSize));
                opponentRect.heightProperty().bind(opponentBoardRectsAreaSizeH.divide(boardSize));

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
