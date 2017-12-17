package com.epam.ships.client.gui.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    @FXML
    private GridPane yourBoard;

    @FXML
    private GridPane opponentBoard;

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

                Rectangle yourRect = new Rectangle(15, 15, Color.GRAY);
                Rectangle opponentRect = new Rectangle(15, 15, Color.GRAY);

                opponentRect.setOnMouseClicked(mouseEvent -> opponentRect.setFill(Color.BLACK));

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
}
