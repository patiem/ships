package pl.korotkevics.ships.client.gui.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pl.korotkevics.ships.client.client.Client;
import pl.korotkevics.ships.client.gui.events.RandomPlacementEvent;
import pl.korotkevics.ships.client.gui.util.GridToBoardConverter;
import pl.korotkevics.ships.client.gui.util.ShipOrientation;
import pl.korotkevics.ships.client.validators.ShipPlacementValidator;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.fleet.Mast;
import pl.korotkevics.ships.shared.fleet.Ship;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Fleet Placement window controller.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-19
 */
public class FleetPlacementController implements Initializable {

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
  private RadioButton rbVertical;

  @FXML
  private RadioButton rbHorizontal;

  @FXML
  private Button buttonClear;


  private List<Ship> ships;
  private Fleet fleet;

  private ShipOrientation shipOrientation;

  private boolean shipPlacementSuccess;

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
  private ResourceBundle resourceBundle;

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
          setOpacity(fillIndex, mastCount, noOpacity);
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
      return index + (mastCount - 1) * BOARD_SIZE
          > yourBoard.getChildren().size() - notRectangleChildCount;
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
        fleet = Fleet.ofShips(ships);
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
      this.getClient().sendFleet(fleet);

      final String gameWindowUrl = "/fxml/gameWindow.fxml";
      final FXMLLoader gameWindowLoader = new FXMLLoader(getClass().getResource(gameWindowUrl));
      gameWindowLoader.setResources(this.resourceBundle);
      final Parent gameWindow = gameWindowLoader.load();
      final AnchorPane mainPane = (AnchorPane) mainAnchorPane.getParent();

      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

      Stage stage = (Stage) mainPane.getScene().getWindow();
      final int shrinkSize = 300;
      stage.setMinHeight(screenBounds.getHeight() - shrinkSize);
      stage.setMinWidth(screenBounds.getWidth() - shrinkSize);

      mainPane.getChildren().clear();
      mainPane.getChildren().setAll(gameWindow);

      GameWindowController gameController = gameWindowLoader.getController();
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
    final ButtonType yesButton = new ButtonType(this.resourceBundle.getString("yes"),
        ButtonBar.ButtonData.YES);
    final ButtonType noButton = new ButtonType(this.resourceBundle.getString("no"),
        ButtonBar.ButtonData.NO);

    Alert alert = showInfoAlert(yesButton, noButton);
    if (alert.getResult() == noButton) {
      return;
    }

    this.disableDragAndDropShips(true);
    this.clearBoard();
    this.buttonRandom.setDisable(true);
    this.getClient().askForRandomFleet();
  }

  private Alert showInfoAlert(final ButtonType yesButton, final ButtonType noButton) {
    final Alert alert = new Alert(Alert.AlertType.INFORMATION, this.resourceBundle
        .getString("alertInfo")
        + System.lineSeparator()
        + System.lineSeparator()
        + this.resourceBundle.getString("alertQuestion"),
        yesButton, noButton);
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.setTitle(this.resourceBundle.getString("information"));
    alert.setHeaderText(this.resourceBundle.getString("information"));
    alert.showAndWait();
    return alert;
  }

  private void disableDragAndDropShips(final boolean disable) {
    this.disableDragAndDropForGroupOfShips(this.groupFourMastShips, disable);
    this.disableDragAndDropForGroupOfShips(this.groupThreeMastShips, disable);
    this.disableDragAndDropForGroupOfShips(this.groupTwoMastShips, disable);
    this.disableDragAndDropForGroupOfShips(this.groupOneMastShips, disable);
  }

  private void disableDragAndDropForGroupOfShips(final Group shipsGroup, final boolean disable) {
    for (Node ship : shipsGroup.getChildren()) {
      ship.setDisable(disable);
      for (Node child : ((Group) ship).getChildren()) {
        final Rectangle rec = (Rectangle) child;
        if (disable) {
          rec.setFill(Color.GRAY);
        } else {
          final String purple = "#7A16C2";
          rec.setFill(Color.web(purple));
        }
      }
    }
  }

  private void getRandomFleet(final Fleet fleet) {
    this.buttonReady.setDisable(false);
    this.fleet = fleet;
    this.drawFleet();
    this.buttonRandom.setDisable(false);
  }

  private void drawFleet() {
    this.fleet
        .toIntegerList()
        .forEach(i -> this.drawMast(this.convertToGridIndex(i)));
  }

  private void drawMast(int index) {
    Rectangle rec = (Rectangle) (yourBoard.getChildren().get(index));
    rec.setFill(Color.GREEN);
  }

  private int convertToGridIndex(final int index) {
    final int column = index / BOARD_SIZE;
    final int row = index - (column * BOARD_SIZE);
    return row * BOARD_SIZE + column + 1;
  }

  private void clearBoardAction() {
    buttonReady.setDisable(true);
    this.disableDragAndDropShips(false);
    clearBoard();
  }

  private void clearBoard() {
    ships.clear();
    for (int i = 1; i < 101; i++) {
      ((Rectangle) yourBoard.getChildren().get(i)).setFill(Color.GRAY);
    }
  }

  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    this.resourceBundle = resources;
    buttonReady.setOnAction(event -> loadGameWindow());
    buttonReady.setDisable(true);
    initializeBoard();
    addDragEventsToShips();
    ships = new ArrayList<>(SHIPS_COUNT);
    shipOrientation = ShipOrientation.VERTICAL;
    rbVertical.setOnAction(event -> shipOrientation = ShipOrientation.VERTICAL);
    rbHorizontal.setOnAction(event -> shipOrientation = ShipOrientation.HORIZONTAL);
    buttonRandom.setOnAction(event -> askForRandomFleet());
    eventButton.addEventHandler(RandomPlacementEvent.RANDOM_PLACEMENT_EVENT,
        event -> getRandomFleet(event.getFleet()));
    buttonClear.setOnAction(event -> clearBoardAction());
    this.addDropShadows();
  }

  private void addDropShadows() {
    final DropShadow shadow = new DropShadow();
    this.buttonRandom.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> buttonRandom
        .setEffect(shadow));
    this.buttonRandom.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> buttonRandom
        .setEffect(null));
    this.buttonReady.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> buttonReady
        .setEffect(shadow));
    this.buttonReady.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> buttonReady
        .setEffect(null));
    this.buttonClear.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> buttonClear
        .setEffect(shadow));
    this.buttonClear.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> buttonClear
        .setEffect(null));
  }
}

