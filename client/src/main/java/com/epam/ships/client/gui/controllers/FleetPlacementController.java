package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.client.gui.events.TurnChangeEvent;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class FleetPlacementController {

    private static final Target logger = new SharedLogger(Client.class);
    private static final int BOARD_SIZE = 10;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Button bReady;

    @FXML
    private Button eventButton;

    @FXML
    private Group gFourMastShip;

    @FXML
    private Group gThreeMastShips;

    @FXML
    private Group gTwoMastShips;

    @FXML
    private Group gOneMastShips;

    @FXML
    private GridPane yourBoard;

    private boolean myTurn;

    //Events Handlers
    private EventHandler<MouseEvent> onMouseEnteredOnShip =
            event -> {
                for (Node child : ((Group)event.getSource()).getChildren()) {
                    Rectangle rec = (Rectangle) child;
                    rec.setFill(Color.BLUE);
                }
                event.consume();
            };

    private EventHandler<MouseEvent> onMouseExitFromShip =
            event -> {
                for (Node child : ((Group)event.getSource()).getChildren()) {
                    Rectangle rec = (Rectangle) child;
                    rec.setFill(Color.web("#7A16C2"));
                }
                event.consume();
            };

    private EventHandler<DragEvent> boardOnDragEntered =
            event -> {
                ((Rectangle) event.getSource()).setOpacity(0.5);
                event.consume();
            };

    private EventHandler<DragEvent> shipOnDragEnetered =
            event -> {
                for (Node child : ((Group)event.getSource()).getChildren()) {
                    Rectangle rec = (Rectangle) child;
                    rec.setFill(Color.RED);
                }
                event.consume();
            };

    private EventHandler<MouseEvent> shipOnDragDetected =
            event -> {
                Dragboard db = ((Group)event.getSource()).startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Ship");
                db.setContent(content);
                event.consume();
            };

    private EventHandler<DragEvent> shipOnDragDone =
            event -> {
                for (Node child : ((Group)event.getSource()).getChildren()) {
                    Rectangle rec = (Rectangle) child;
                    rec.setFill(Color.GRAY);
                }
                logger.info("end drag");
                event.consume();

                ((Group)event.getSource()).setDisable(true);
            };


    @FXML
    public void initialize() {
        bReady.setOnAction(event -> loadGameWindow());
        eventButton.addEventHandler(TurnChangeEvent.TURN_EVENT, event -> setMyTurn());
        initializeBoard();
        addDragEventsToShips();
    }

    private void initializeBoard() {
        final int margin = 50;
        final NumberBinding allRectanglesWidth = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty().add(-margin));
        final NumberBinding allRectanglesHeight = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty()).add(-margin);

        for(int i = 0; i < BOARD_SIZE; i++ ) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                final int recIndex = j * BOARD_SIZE + i;;
                final int fillIndex = i * BOARD_SIZE + j;

                Rectangle yourRect = getYourRect(allRectanglesWidth, allRectanglesHeight);
                yourBoard.add(yourRect, i, j);

                yourRect.setOnDragEntered(boardOnDragEntered);

                yourRect.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        Dragboard db = event.getDragboard();
                        if (db.hasString()) {
                            event.acceptTransferModes(TransferMode.COPY);
                        }
                        event.consume();
                    }
                });

                yourRect.setOnDragExited(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        yourRect.setOpacity(1);
                        event.consume();
                    }
                });

                yourRect.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {

                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            success = true;
                        }

                        int mast  = ((Group)event.getGestureSource()).getChildren().size();
                        event.setDropCompleted(success);

                        yourRect.setFill(Color.GREEN);

                        int index = fillIndex + 1;

                        for(int i = 1; i < mast; i++) {
                            ((Rectangle) yourBoard.getChildren().get(index + i)).setFill(Color.GREEN);
                        }

                        logger.info("first ship index " + recIndex);
                        logger.info("n mast " + mast);
                        event.consume();
                    }
                });


                GridPane.setHalignment(yourRect, HPos.CENTER);
            }
        }
    }

    private Rectangle getYourRect(NumberBinding allRectanglesWidth, NumberBinding allRectanglesHeight) {
        final int initialSize = 15;
        Rectangle yourRect = new Rectangle(initialSize, initialSize, Color.GRAY);
        yourRect.widthProperty().bind(allRectanglesWidth.divide(BOARD_SIZE));
        yourRect.heightProperty().bind(allRectanglesHeight.divide(BOARD_SIZE));

        return yourRect;
    }

    private void addDragEventsToShips() {

        gFourMastShip.setOnMouseEntered(onMouseEnteredOnShip);
        gFourMastShip.setOnMouseExited(onMouseExitFromShip);
        gFourMastShip.setOnDragEntered(shipOnDragEnetered);
        gFourMastShip.setOnDragDetected(shipOnDragDetected);
        gFourMastShip.setOnDragDone(shipOnDragDone);

        for (Node threeMastShip : gThreeMastShips.getChildren()) {
            threeMastShip.setOnMouseEntered(onMouseEnteredOnShip);
            threeMastShip.setOnMouseExited(onMouseExitFromShip);
            threeMastShip.setOnDragEntered(shipOnDragEnetered);
            threeMastShip.setOnDragDetected(shipOnDragDetected);
            threeMastShip.setOnDragDone(shipOnDragDone);
        }

        for (Node twoMastShip : gTwoMastShips.getChildren()) {
            twoMastShip.setOnMouseEntered(onMouseEnteredOnShip);
            twoMastShip.setOnMouseExited(onMouseExitFromShip);
            twoMastShip.setOnDragEntered(shipOnDragEnetered);
            twoMastShip.setOnDragDetected(shipOnDragDetected);
            twoMastShip.setOnDragDone(shipOnDragDone);
        }

        for (Node oneMastShip : gOneMastShips.getChildren()) {
            oneMastShip.setOnMouseEntered(onMouseEnteredOnShip);
            oneMastShip.setOnMouseExited(onMouseExitFromShip);
            oneMastShip.setOnDragEntered(shipOnDragEnetered);
            oneMastShip.setOnDragDetected(shipOnDragDetected);
            oneMastShip.setOnDragDone(shipOnDragDone);
        }
    }

    void initializeClient() {
        getClient().setEventTrigger(eventButton);
    }

    private Client getClient() {
        MainController mainController = (MainController) mainAnchorPane.getParent().getUserData();
        return mainController.getClient();
    }

    private void loadGameWindow() {
        try {
            final String gameWindowURL = "/fxml/gameWindow.fxml";
            final FXMLLoader gameWindowLoader = new FXMLLoader(getClass().getResource(gameWindowURL));
            final Parent gameWindow = gameWindowLoader.load();
            final AnchorPane mainPane = (AnchorPane) mainAnchorPane.getParent();

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            Stage stage = (Stage) mainPane.getScene().getWindow();
            final int shrinkSize = 300;
            stage.setMinHeight(screenBounds.getHeight() - shrinkSize);
            stage.setMinWidth(screenBounds.getWidth() - shrinkSize);

            mainPane.getChildren().clear();
            mainPane.getChildren().setAll(gameWindow);

            GameController gameController = gameWindowLoader.getController();
            gameController.initializeClient();
            gameController.initializeTurn(myTurn);

            final double margin = 0.0;
            AnchorPane.setTopAnchor(gameWindow, margin);
            AnchorPane.setBottomAnchor(gameWindow, margin);
            AnchorPane.setLeftAnchor(gameWindow, margin);
            AnchorPane.setRightAnchor(gameWindow, margin);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void setMyTurn() {
        logger.info("set my turn");
        myTurn = true;
    }
}
