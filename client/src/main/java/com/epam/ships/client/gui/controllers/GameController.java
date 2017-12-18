package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.client.gui.events.OpponentWithdrawEvent;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
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

    private static final int BOARD_SIZE = 10;

    @FXML
    private GridPane yourBoard;

    @FXML
    private GridPane opponentBoard;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Button eventButton;

    @FXML
    public void initialize() {
        initializeBoard();
        eventButton.addEventHandler(OpponentWithdrawEvent.OPPONENT_WITHDRAW, opponentConnectedEvent -> opponentWithdraw());
    }

    void initializeClient() {
        getClient().setEventTrigger(eventButton);
    }

    private void initializeBoard() {
        final NumberBinding allRectanglesWidth = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty().add(-50));
        final NumberBinding allRectanglesHeight = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty()).add(-50);

        for(int i = 0; i < BOARD_SIZE; i++ ) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                final int shotIndex = j * BOARD_SIZE + i;

                Rectangle yourRect = getYourRect(allRectanglesWidth, allRectanglesHeight);
                Rectangle opponentRect = getOpponentRectangle(allRectanglesWidth, allRectanglesHeight, shotIndex);

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

    private Rectangle getYourRect(NumberBinding allRectanglesWidth, NumberBinding allRectanglesHeight) {
        Rectangle yourRect = new Rectangle(15, 15, Color.GRAY);
        yourRect.widthProperty().bind(allRectanglesWidth.divide(BOARD_SIZE));
        yourRect.heightProperty().bind(allRectanglesHeight.divide(BOARD_SIZE));

        return yourRect;
    }

    private Rectangle getOpponentRectangle(NumberBinding allRectanglesWidth, NumberBinding allRectanglesHeight,
                                           final int opponentShotIndex) {
        Rectangle opponentRect = new Rectangle(15, 15, Color.GRAY);

        opponentRect.setOnMouseClicked((MouseEvent mouseEvent) -> {
            opponentRect.setFill(Color.BLACK);
            logger.info(opponentShotIndex);
            getClient().sendShot(opponentShotIndex);
        });

        opponentRect.widthProperty().bind(allRectanglesWidth.divide(BOARD_SIZE));
        opponentRect.heightProperty().bind(allRectanglesHeight.divide(BOARD_SIZE));

        return opponentRect;
    }

    private void opponentWithdraw() {
        loadWithdrawScreen();
    }

    private void loadWithdrawScreen() {
        MainController mainController = (MainController) mainAnchorPane.getParent().getUserData();
        mainController.loadWithdrawScreen();
    }
}
