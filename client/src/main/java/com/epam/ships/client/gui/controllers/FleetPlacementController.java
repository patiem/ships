package com.epam.ships.client.gui.controllers;

import com.epam.ships.client.client.Client;
import com.epam.ships.client.gui.events.TurnChangeEvent;
import com.epam.ships.fleet.Fleet;
import com.epam.ships.fleet.Mast;
import com.epam.ships.fleet.Ship;
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
import java.util.ArrayList;
import java.util.List;

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
    private Group gFourMastShips;

    @FXML
    private Group gThreeMastShips;

    @FXML
    private Group gTwoMastShips;

    @FXML
    private Group gOneMastShips;

    @FXML
    private GridPane yourBoard;

    private volatile boolean myTurn;

    private Fleet fleet;

    private List<Ship> ships;

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

    private EventHandler<DragEvent> shipOnDragEntered =
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

    private EventHandler<DragEvent> boardOnDragOver =
            event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
            };

    private EventHandler<DragEvent> boardOnDragExited =
            event -> {
                ((Rectangle)event.getSource()).setOpacity(1);
                event.consume();
            };

    @FXML
    public void initialize() {
        eventButton.addEventHandler(TurnChangeEvent.TURN_EVENT, event -> setMyTurn());
        bReady.setOnAction(event -> loadGameWindow());
        initializeBoard();
        addDragEventsToShips();
        ships = new ArrayList<>(10);
    }

    private void initializeBoard() {
        final int margin = 50;
        final NumberBinding allRectanglesWidth = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty().add(-margin));
        final NumberBinding allRectanglesHeight = Bindings.min(yourBoard.heightProperty(),
                yourBoard.widthProperty()).add(-margin);

        for(int i = 0; i < BOARD_SIZE; i++ ) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                final int recIndex = j * BOARD_SIZE + i;
                final int fillIndex = i * BOARD_SIZE + j;

                Rectangle yourRect = getYourRect(allRectanglesWidth, allRectanglesHeight);
                yourBoard.add(yourRect, i, j);
                setEventsOnField(yourRect, recIndex, fillIndex);
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

    private void setEventsOnField(Rectangle rectangle, final int recIndex, final int fillIndex) {
        rectangle.setOnDragEntered(boardOnDragEntered);
        rectangle.setOnDragOver(boardOnDragOver);
        rectangle.setOnDragExited(boardOnDragExited);

        rectangle.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                success = true;
            }

            int mast  = ((Group)event.getGestureSource()).getChildren().size();
            event.setDropCompleted(success);

            rectangle.setFill(Color.GREEN);

            int index = fillIndex + 1;

            Mast[] masts = new Mast[mast];
            masts[0] = Mast.ofIndex(String.valueOf(recIndex));

            for(int i1 = 1; i1 < mast; i1++) {
                ((Rectangle) yourBoard.getChildren().get(index + i1)).setFill(Color.GREEN);
                masts[i1] = Mast.ofIndex(String.valueOf(recIndex + i1 * BOARD_SIZE));
            }

            ships.add(Ship.ofMasts(masts));

            logger.info("first ship index " + recIndex);
            logger.info("n mast " + mast);
            event.consume();
        });
    }

    private void addDragEventsToShips() {
        setEventsOnShips(gFourMastShips);
        setEventsOnShips(gThreeMastShips);
        setEventsOnShips(gTwoMastShips);
        setEventsOnShips(gOneMastShips);
    }

    private void setEventsOnShips(Group shipsGroup) {
        for (Node ship : shipsGroup.getChildren()) {
            ship.setOnMouseEntered(onMouseEnteredOnShip);
            ship.setOnMouseExited(onMouseExitFromShip);
            ship.setOnDragEntered(shipOnDragEntered);
            ship.setOnDragDetected(shipOnDragDetected);
            ship.setOnDragDone(shipOnDragDone);
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

            getClient().sendFleet(Fleet.ofShips((Ship[])ships.toArray()));

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
            gameController.initializeTurn(myTurn);
            gameController.initializeClient();
            gameController.initializeBoards(yourBoard);

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
        logger.info("set my turn fleet");
        this.myTurn = true;
    }
}
