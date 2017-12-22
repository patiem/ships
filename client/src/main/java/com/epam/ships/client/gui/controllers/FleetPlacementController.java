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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import java.util.stream.IntStream;

public class FleetPlacementController {

    private static final Target logger = new SharedLogger(Client.class);
    private static final int BOARD_SIZE = 10;
    private static final int SHIPS_COUNT = 10;

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

    @FXML
    private GridPane rightGrid;

    @FXML
    private ChoiceBox choiceBox;

    private List<Ship> ships;

    private Orientation shipOrientation;

    private boolean shipPlacementSuccess;

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
                if(shipPlacementSuccess) {
                    for (Node child : ((Group) event.getSource()).getChildren()) {
                        Rectangle rec = (Rectangle) child;
                        rec.setFill(Color.GRAY);
                    }
                    logger.info("end drag");
                    ((Group) event.getSource()).setDisable(true);
                }
                shipPlacementSuccess = false;
                event.consume();
            };

    private EventHandler<DragEvent> boardOnDragOver =
            event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
            };

    @FXML
    public void initialize() {
        bReady.setOnAction(event -> loadGameWindow());
        bReady.setDisable(true);
        initializeBoard();
        addDragEventsToShips();
        ships = new ArrayList<>(SHIPS_COUNT);
        shipOrientation = Orientation.VERTICAL;
        choiceBox.setItems(FXCollections.observableArrayList("VERTICAL", "HORIZONTAL"));
        choiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            shipOrientation = Orientation.valueOf((String)newValue);
            logger.info((String)newValue);
                });
    }

    private void initializeBoard() {
        final int margin = 50;
        final NumberBinding allRectanglesWidth = Bindings.min(yourBoard.widthProperty(),
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
        rectangle.setOnDragEntered(event -> {
                double opacity = 0.5;
                int mastCount  = ((Group)event.getGestureSource()).getChildren().size();
                int index = fillIndex;
                if(shipOrientation.equals(Orientation.VERTICAL)) {
                    index += 1;
                    ((Rectangle) event.getSource()).setOpacity(opacity);
                    for(int i = 1; i < mastCount; i++) {
                        ((Rectangle) yourBoard.getChildren().get(index + i)).setOpacity(opacity);
                    }
                } else {
                    if(index + mastCount * BOARD_SIZE > yourBoard.getChildren().size()) {
                        return;
                    }
                    ((Rectangle) event.getSource()).setOpacity(opacity);
                    index += 1;
                    for(int i = 1; i < mastCount; i++) {
                        ((Rectangle) yourBoard.getChildren().get(index + i * BOARD_SIZE)).setOpacity(opacity);
                    }
                }
                event.consume();
            });
        rectangle.setOnDragOver(boardOnDragOver);
        rectangle.setOnDragExited(
                event -> {
                    double noOpacity = 1.0;
                    int mastCount  = ((Group)event.getGestureSource()).getChildren().size();
                    int index = fillIndex;
                    if(shipOrientation.equals(Orientation.VERTICAL)) {
                        index += 1;
                        ((Rectangle)event.getSource()).setOpacity(1);
                        for(int i = 1; i < mastCount; i++) {
                            ((Rectangle) yourBoard.getChildren().get(index + i)).setOpacity(noOpacity);
                        }
                    } else {
                        if(index + mastCount * BOARD_SIZE > yourBoard.getChildren().size()) {
                            return;
                        }
                        index += 1;
                        ((Rectangle)event.getSource()).setOpacity(1);
                        for(int i = 1; i < mastCount; i++) {
                            ((Rectangle) yourBoard.getChildren().get(index + i * BOARD_SIZE)).setOpacity(noOpacity);
                        }
                    }
                    event.consume();
                });
        setFieldOnDragDropped(rectangle, recIndex, fillIndex);
    }

    private void setFieldOnDragDropped(Rectangle rectangle, final int recIndex, final int fillIndex) {
        rectangle.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                success = true;
            }
            event.setDropCompleted(success);
            int index = fillIndex;

            int mastCount  = ((Group)event.getGestureSource()).getChildren().size();
            Mast[] masts = new Mast[mastCount];

            if(shipOrientation.equals(Orientation.VERTICAL)) {
                masts[0] = Mast.ofIndex(String.valueOf(recIndex));
                rectangle.setFill(Color.GREEN);
                index += 1;
                for (int i1 = 1; i1 < mastCount; i1++) {
                    ((Rectangle) yourBoard.getChildren().get(index + i1)).setFill(Color.GREEN);
                    masts[i1] = Mast.ofIndex(String.valueOf(recIndex + i1 * BOARD_SIZE));
                }
            } else {
                masts[0] = Mast.ofIndex(String.valueOf(recIndex));
                if(index + mastCount * BOARD_SIZE > yourBoard.getChildren().size()) {
                    shipPlacementSuccess = false;
                    return;
                }
                index += 1;
                rectangle.setFill(Color.GREEN);
                for (int i1 = 1; i1 < mastCount; i1++) {
                    ((Rectangle) yourBoard.getChildren().get(index + i1 * BOARD_SIZE)).setFill(Color.GREEN);
                    masts[i1] = Mast.ofIndex(String.valueOf(recIndex + i1));
                }
            }
            ships.add(Ship.ofMasts(masts));
            if(ships.size() == SHIPS_COUNT) {
                bReady.setDisable(false);
            }

            logger.info("first index of ship " + recIndex);
            logger.info("ship has " + mastCount + " masts");
            shipPlacementSuccess = true;
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
            getClient().sendFleet(Fleet.ofShips(ships));

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
}
