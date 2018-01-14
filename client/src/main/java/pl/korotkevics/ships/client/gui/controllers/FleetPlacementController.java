package pl.korotkevics.ships.client.gui.controllers;

import javafx.scene.effect.DropShadow;
import pl.korotkevics.ships.client.client.Client;
import pl.korotkevics.ships.client.gui.events.RandomPlacementEvent;
import pl.korotkevics.ships.client.gui.events.TurnChangeEvent;
import pl.korotkevics.ships.client.gui.util.GridToBoardConverter;
import pl.korotkevics.ships.client.gui.util.ShipOrientation;
import pl.korotkevics.ships.client.validators.ShipPlacementValidator;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.fleet.Mast;
import pl.korotkevics.ships.shared.fleet.Ship;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
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

/**
 * Fleet Placement window controller.
 * @author Magdalena Aarsman
 * @since 2017-12-19
 */
public class FleetPlacementController {

  private static final Target logger = new SharedLogger(Client.class);
  private static final int BOARD_SIZE = 10;
  private static final int SHIPS_COUNT = 10;

  @FXML
  private AnchorPane mainAnchorPane;

  @FXML
  private Button buttonReady;

  @FXML
  private Button buttonRandom;

  @FXML
  private Button eventButton;

  @FXML
  private Group groupFourMastShips;

  @FXML
  private Group groupThreeMastShips;

  @FXML
  private Group groupTwoMastShips;

  @FXML
  private Group groupOneMastShips;

  @FXML
  private GridPane yourBoard;

  @FXML
  private ChoiceBox choiceBox;

  private List<Ship> ships;

  private ShipOrientation shipOrientation;

  private boolean shipPlacementSuccess;
  private boolean randomShipPlacement;

  //Events Handlers
  private EventHandler<MouseEvent> onMouseEnteredOnShip =
      event -> {
        for (Node child : ((Group) event.getSource()).getChildren()) {
          Rectangle rec = (Rectangle) child;
          rec.setFill(Color.BLUE);
        }
        event.consume();
      };

  private EventHandler<MouseEvent> onMouseExitFromShip =
      event -> {
        for (Node child : ((Group) event.getSource()).getChildren()) {
          Rectangle rec = (Rectangle) child;
          rec.setFill(Color.web("#7A16C2"));
        }
        event.consume();
      };

  private EventHandler<DragEvent> shipOnDragEntered =
      event -> {
        for (Node child : ((Group) event.getSource()).getChildren()) {
          Rectangle rec = (Rectangle) child;
          rec.setFill(Color.RED);
        }
        event.consume();
      };

  private EventHandler<MouseEvent> shipOnDragDetected =
      event -> {
        Dragboard db = ((Group) event.getSource()).startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        content.putString("Ship");
        db.setContent(content);
        event.consume();
      };

  private EventHandler<DragEvent> shipOnDragDone =
      event -> {
        if (shipPlacementSuccess) {
          for (Node child : ((Group) event.getSource()).getChildren()) {
            Rectangle rec = (Rectangle) child;
            rec.setFill(Color.GRAY);
          }
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
  void initialize() {
    buttonReady.setOnAction(event -> loadGameWindow());
    buttonReady.setDisable(true);
    initializeBoard();
    addDragEventsToShips();
    ships = new ArrayList<>(SHIPS_COUNT);
    shipOrientation = ShipOrientation.VERTICAL;
    choiceBox.setItems(FXCollections.observableArrayList("VERTICAL", "HORIZONTAL"));
    choiceBox.setValue("VERTICAL");
    choiceBox.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          shipOrientation = ShipOrientation.valueOf((String) newValue);
          logger.info(newValue);
        });

    buttonRandom.setOnAction(event -> askForRandomFleet());
    DropShadow shadow = new DropShadow();
    buttonRandom.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> buttonRandom.setEffect(shadow));
    buttonRandom.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> buttonRandom.setEffect(null));
    buttonReady.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> buttonReady.setEffect(shadow));
    buttonReady.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> buttonReady.setEffect(null));
    eventButton.addEventHandler(RandomPlacementEvent.RANDOM_PLACEMENT_EVENT, event -> getRandomFleet());
  }

  private void initializeBoard() {
    final int margin = 50;
    final NumberBinding allRectanglesWidth = Bindings.min(yourBoard.widthProperty(),
        yourBoard.widthProperty().add(-margin));
    final NumberBinding allRectanglesHeight = Bindings.min(yourBoard.heightProperty(),
        yourBoard.widthProperty()).add(-margin);

    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        final int recIndex = j * BOARD_SIZE + i;
        final int fillIndex = i * BOARD_SIZE + j;

        Rectangle yourRect = getYourRect(allRectanglesWidth, allRectanglesHeight);
        yourBoard.add(yourRect, i, j);
        setEventsOnField(yourRect, recIndex, fillIndex);
        GridPane.setHalignment(yourRect, HPos.CENTER);
      }
    }
  }

  private Rectangle getYourRect(NumberBinding allRectanglesWidth,
                                NumberBinding allRectanglesHeight) {
    final int initialSize = 15;
    Rectangle yourRect = new Rectangle(initialSize, initialSize, Color.GRAY);
    yourRect.widthProperty().bind(allRectanglesWidth.divide(BOARD_SIZE));
    yourRect.heightProperty().bind(allRectanglesHeight.divide(BOARD_SIZE));
    return yourRect;
  }

  private void setEventsOnField(Rectangle rectangle, final int recIndex, final int fillIndex) {
    setFieldOnDragEntered(rectangle, fillIndex);
    rectangle.setOnDragOver(boardOnDragOver);
    setFieldOnDragExited(rectangle, fillIndex);
    setFieldOnDragDropped(rectangle, recIndex, fillIndex);
  }

  private void setOpacity(int index, int mastCount, double opacity) {
    index += 1;

    if (shipOrientation.equals(ShipOrientation.HORIZONTAL)) {
      for (int i = 1; i < mastCount; i++) {
        (yourBoard.getChildren().get(index + i * BOARD_SIZE)).setOpacity(opacity);
      }
    } else {
      for (int i = 1; i < mastCount; i++) {
        (yourBoard.getChildren().get(index + i)).setOpacity(opacity);
      }
    }
  }

  private void setFieldOnDragExited(Rectangle rectangle, int fillIndex) {
    rectangle.setOnDragExited(
        event -> {
          double noOpacity = 1.0;
          int mastCount = ((Group) event.getGestureSource()).getChildren().size();
          if (isOutOfBound(fillIndex, mastCount)) {
            return;
          }
          ((Rectangle) event.getSource()).setOpacity(noOpacity);
          setOpacity(fillIndex, mastCount ,noOpacity);
          event.consume();
        });
  }

  private void setFieldOnDragEntered(Rectangle rectangle, final int index) {
    rectangle.setOnDragEntered(event -> {
      double opacity = 0.5;
      int mastCount = ((Group) event.getGestureSource()).getChildren().size();
      if (shipOrientation.equals(ShipOrientation.HORIZONTAL) && isOutOfBound(index, mastCount)) {
        return;
      }
      GridToBoardConverter gridToBoardConverter = new GridToBoardConverter(yourBoard);
      ShipPlacementValidator shipPlacementValidator =
          new ShipPlacementValidator(shipOrientation, index, mastCount,
              gridToBoardConverter.convert());
      if (!shipPlacementValidator.isPlacementValid()) {
        return;
      }

      ((Rectangle) event.getSource()).setOpacity(opacity);
      setOpacity(index, mastCount, opacity);
      event.consume();
    });
  }

  private boolean isOutOfBound(int index, int mastCount) {
    final int notRectangleChildCount = 2;
    if (shipOrientation.equals(ShipOrientation.HORIZONTAL)) {
      if (index + (mastCount - 1) * BOARD_SIZE
          > yourBoard.getChildren().size() - notRectangleChildCount) {
        return true;
      }
      return false;
    } else {
      if (index + mastCount >= yourBoard.getChildren().size()) {
        return true;
      }
    }
    return false;
  }

  private void setFieldOnDragDropped(Rectangle rectangle, final int recIndex, final int index) {
    rectangle.setOnDragDropped(event -> {
      Dragboard db = event.getDragboard();
      boolean success = false;
      if (db.hasString()) {
        success = true;
      }
      event.setDropCompleted(success);
      int mastCount = ((Group) event.getGestureSource()).getChildren().size();
      Mast[] masts = new Mast[mastCount];

      if (shipOrientation.equals(ShipOrientation.HORIZONTAL) && isOutOfBound(index, mastCount)) {
        shipPlacementSuccess = false;
        return;
      }

      GridToBoardConverter gridToBoardConverter = new GridToBoardConverter(yourBoard);
      ShipPlacementValidator shipPlacementValidator =
          new ShipPlacementValidator(shipOrientation, index, mastCount,
              gridToBoardConverter.convert());
      if (!shipPlacementValidator.isPlacementValid()) {
        shipPlacementSuccess = false;
        return;
      }
      masts[0] = Mast.ofIndex(String.valueOf(recIndex));
      rectangle.setFill(Color.GREEN);
      placeShip(index, masts, mastCount, recIndex);
      ships.add(Ship.ofMasts(masts));
      if (ships.size() == SHIPS_COUNT) {
        buttonReady.setDisable(false);
      }
      shipPlacementSuccess = true;
      event.consume();
    });
  }

  private void placeShip(int index, Mast[] masts, int mastCount, int recIndex) {
    index += 1;

    if (shipOrientation.equals(ShipOrientation.VERTICAL)) {
      for (int i1 = 1; i1 < mastCount; i1++) {
        ((Rectangle) yourBoard.getChildren().get(index + i1)).setFill(Color.GREEN);
        masts[i1] = Mast.ofIndex(String.valueOf(recIndex + i1 * BOARD_SIZE));
      }
    } else {
      for (int i1 = 1; i1 < mastCount; i1++) {
        ((Rectangle) yourBoard.getChildren().get(index + i1 * BOARD_SIZE)).setFill(Color.GREEN);
        masts[i1] = Mast.ofIndex(String.valueOf(recIndex + i1));
      }
    }
  }

  private void addDragEventsToShips() {
    setEventsOnShips(groupFourMastShips);
    setEventsOnShips(groupThreeMastShips);
    setEventsOnShips(groupTwoMastShips);
    setEventsOnShips(groupOneMastShips);
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
      if(!randomShipPlacement) {
        getClient().sendFleet(Fleet.ofShips(ships));
      }

      final String gameWindowUrl = "/fxml/gameWindow.fxml";
      final FXMLLoader gameWindowLoader = new FXMLLoader(getClass().getResource(gameWindowUrl));
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

  private void askForRandomFleet() {
    getClient().askForRandomFleet();
    this.randomShipPlacement = true;
    buttonRandom.setDisable(true);
  }

  private void getRandomFleet() {
    buttonReady.setDisable(false);
    loadGameWindow();
  }
}
