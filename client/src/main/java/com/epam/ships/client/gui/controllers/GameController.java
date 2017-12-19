package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.client.gui.events.OpponentShotEvent;
import com.epam.ships.client.gui.events.OpponentWithdrawEvent;
import com.epam.ships.client.gui.events.TurnChangeEvent;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author Magda
 * @since 2017-12-15
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
        eventButton.addEventHandler(OpponentShotEvent.OPPONENT_SHOT,
                opponentShotEvent -> setOpponentShot(opponentShotEvent.getShotIndex()));
        eventButton.addEventHandler(TurnChangeEvent.TURN_EVENT, event -> setMyTurn());

        opponentBoard.setDisable(true);
        final double opacity = 0.4;
        opponentBoard.setOpacity(opacity);
    }

    void initializeClient() {
        getClient().setEventTrigger(eventButton);
    }

    private void initializeBoard() {
        final int margin = 50;
        final NumberBinding allRectanglesWidth = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty().add(-margin));
        final NumberBinding allRectanglesHeight = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty()).add(-margin);

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
        final int magicNumberWhichNoMattersAfterAll = 15;
        Rectangle yourRect = new Rectangle(magicNumberWhichNoMattersAfterAll, magicNumberWhichNoMattersAfterAll, Color.GRAY);
        yourRect.widthProperty().bind(allRectanglesWidth.divide(BOARD_SIZE));
        yourRect.heightProperty().bind(allRectanglesHeight.divide(BOARD_SIZE));

        return yourRect;
    }

    private Rectangle getOpponentRectangle(NumberBinding allRectanglesWidth, NumberBinding allRectanglesHeight,
                                           final int opponentShotIndex) {
        final int magicNumberWhichNoMattersAfterAll = 15;
        Rectangle opponentRect = new Rectangle(magicNumberWhichNoMattersAfterAll, magicNumberWhichNoMattersAfterAll, Color.GRAY);

        opponentRect.setOnMouseClicked((MouseEvent mouseEvent) -> {
            opponentRect.setFill(Color.BLACK);
            logger.info(opponentShotIndex);
            getClient().sendShot(opponentShotIndex);
            opponentBoard.setDisable(true);
            final double opacity = 0.4;
            opponentBoard.setOpacity(opacity);
        });

        opponentRect.widthProperty().bind(allRectanglesWidth.divide(BOARD_SIZE));
        opponentRect.heightProperty().bind(allRectanglesHeight.divide(BOARD_SIZE));

        return opponentRect;
    }

    private void opponentWithdraw() {
        getClient().closeClient();
        loadWithdrawScreen();
    }

    private void loadWithdrawScreen() {
        try {
            final String opponentWithdrawURL = "/fxml/opponentWithdraw.fxml";
            final FXMLLoader opponentWithdrawLoader = new FXMLLoader(getClass().getResource(opponentWithdrawURL));
            final Parent opponentWithdraw = opponentWithdrawLoader.load();
            final AnchorPane mainPane = (AnchorPane) mainAnchorPane.getParent();
            final int sceneHeight = 400;
            final int sceneWidth = 600;
            final double margin = 0.0;

            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setHeight(sceneHeight);
            stage.setWidth(sceneWidth);

            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(opponentWithdraw);


            AnchorPane.setTopAnchor(opponentWithdraw, margin);
            AnchorPane.setBottomAnchor(opponentWithdraw, margin);
            AnchorPane.setLeftAnchor(opponentWithdraw, margin);
            AnchorPane.setRightAnchor(opponentWithdraw, margin);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void setOpponentShot(int shotIndex) {
        int column = shotIndex / BOARD_SIZE;
        int row = shotIndex - (column * BOARD_SIZE);
        int newShotIndex = row * BOARD_SIZE + column;
        final double noOpacity = 1.0;
        logger.info("new shotIndex: " + newShotIndex);
        Rectangle rec = (Rectangle) (yourBoard.getChildren().get(newShotIndex + 1));
        rec.setFill(Color.BLACK);
        opponentBoard.setDisable(false);
        opponentBoard.setOpacity(noOpacity);
    }

    private void setMyTurn() {
        final double noOpacity = 1.0;
        opponentBoard.setDisable(false);
        opponentBoard.setOpacity(noOpacity);
    }
}
