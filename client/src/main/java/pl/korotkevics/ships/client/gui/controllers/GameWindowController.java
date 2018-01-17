
package pl.korotkevics.ships.client.gui.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pl.korotkevics.ships.client.client.Client;
import pl.korotkevics.ships.client.gui.events.HitShotEvent;
import pl.korotkevics.ships.client.gui.events.LooseEvent;
import pl.korotkevics.ships.client.gui.events.MissShotEvent;
import pl.korotkevics.ships.client.gui.events.OpponentShotEvent;
import pl.korotkevics.ships.client.gui.events.OpponentWithdrawEvent;
import pl.korotkevics.ships.client.gui.events.TurnChangeEvent;
import pl.korotkevics.ships.client.gui.events.WinEvent;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Game window controller.
 * @author Magdalena Aarsman
 * @since 2017-12-15
 */

public class GameWindowController implements Initializable {
  
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
  private Label winLabel;
  
  private int shotIndex;
  private ResourceBundle resourceBundle;
  
  private void initializeTurn(boolean myTurn) {
    logger.info("initialize turn: " + myTurn);
    
    if (!myTurn) {
      opponentBoard.setDisable(true);
      final double opacity = 0.4;
      opponentBoard.setOpacity(opacity);
    }
  }
  
  void initializeClient() {
    getClient().setEventTrigger(eventButton);
  }
  
  void initializeBoards(GridPane yourBoard) {
    final int margin = 50;
    final NumberBinding allRectanglesWidth = Bindings.min(this.yourBoard.heightProperty(),
        this.yourBoard.widthProperty().add(-margin));
    final NumberBinding allRectanglesHeight = Bindings.min(this.yourBoard.heightProperty(),
        this.yourBoard.widthProperty()).add(-margin);
    
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        final int shotIndex = j * BOARD_SIZE + i;
        
        Rectangle yourRect = getYourRect(allRectanglesWidth, allRectanglesHeight);
        Rectangle opponentRect = getOpponentRectangle(allRectanglesWidth,
            allRectanglesHeight, shotIndex);
        
        this.yourBoard.add(yourRect, i, j);
        opponentBoard.add(opponentRect, i, j);
        
        GridPane.setHalignment(yourRect, HPos.CENTER);
        GridPane.setHalignment(opponentRect, HPos.CENTER);
      }
    }
    
    copyYourBoard(yourBoard);
  }
  
  private void copyYourBoard(GridPane yourBoard) {
    for (int i = 1; i < yourBoard.getChildren().size(); i++) {
      if (((Rectangle) yourBoard.getChildren().get(i)).getFill() == Color.GREEN) {
        ((Rectangle) this.yourBoard.getChildren().get(i)).setFill(Color.GREEN);
      }
    }
  }
  
  private Client getClient() {
    MainController mainController = (MainController) mainAnchorPane.getParent().getUserData();
    return mainController.getClient();
  }
  
  private Rectangle getYourRect(NumberBinding allRectanglesWidth,
                                NumberBinding allRectanglesHeight) {
    final int initialSize = 15;
    Rectangle yourRect = new Rectangle(initialSize, initialSize, Color.GRAY);
    yourRect.widthProperty().bind(allRectanglesWidth.divide(BOARD_SIZE));
    yourRect.heightProperty().bind(allRectanglesHeight.divide(BOARD_SIZE));
    
    return yourRect;
  }
  
  private Rectangle getOpponentRectangle(NumberBinding allRectanglesWidth,
                                         NumberBinding allRectanglesHeight,
                                         final int shotIndex) {
    final int initialSize = 15;
    Rectangle opponentRect = new Rectangle(initialSize, initialSize, Color.GRAY);
    
    opponentRect.setOnMouseClicked((MouseEvent mouseEvent) -> {
      opponentRect.setFill(Color.BLACK);
      logger.info(shotIndex);
      this.shotIndex = shotIndex;
      getClient().sendShot(shotIndex);
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
      final String opponentWithdrawUrl = "/fxml/opponentWithdraw.fxml";
      final FXMLLoader opponentWithdrawLoader =
          new FXMLLoader(getClass().getResource(opponentWithdrawUrl));
      opponentWithdrawLoader.setResources(this.resourceBundle);
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
  
  private void setOpponentShot(final int shotIndex) {
    final int shotIndexInGrid = convertToGridIndex(shotIndex);
    logger.info("new shotIndex: " + shotIndexInGrid);
    final Rectangle rec = (Rectangle) (yourBoard.getChildren().get(shotIndexInGrid));
    if (rec.getFill() == Color.GREEN) {
      rec.setFill(Color.RED);
    } else {
      rec.setFill(Color.BLACK);
    }
  }
  
  private int convertToGridIndex(final int shotIndex) {
    final int column = shotIndex / BOARD_SIZE;
    final int row = shotIndex - (column * BOARD_SIZE);
    return row * BOARD_SIZE + column + 1;
  }
  
  private void changeTurn() {
    final double opacity = 0.4;
    this.opponentBoard.setDisable(true);
    this.opponentBoard.setOpacity(opacity);
  }
  
  private void markAsHit() {
    final int shotIndexInGrid = this.convertToGridIndex(shotIndex);
    final Rectangle rec = (Rectangle) (opponentBoard.getChildren().get(shotIndexInGrid));
    rec.setFill(Color.RED);
  }
  
  private void setMyTurn() {
    logger.info("set my turn game controller");
    final double noOpacity = 1.0;
    opponentBoard.setDisable(false);
    opponentBoard.setOpacity(noOpacity);
  }
  
  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    this.resourceBundle = resources;
    eventButton.addEventHandler(OpponentWithdrawEvent.OPPONENT_WITHDRAW,
        opponentConnectedEvent -> opponentWithdraw());
    eventButton.addEventHandler(OpponentShotEvent.OPPONENT_SHOT,
        opponentShotEvent -> setOpponentShot(opponentShotEvent.getShotIndex()));
    eventButton.addEventHandler(TurnChangeEvent.TURN_EVENT, event -> setMyTurn());
    eventButton.addEventHandler(MissShotEvent.MISS_SHOT, event -> changeTurn());
    eventButton.addEventHandler(HitShotEvent.HIT_SHOT, event -> markAsHit());
    eventButton.addEventHandler(WinEvent.GAME_WIN, event -> renderAsWin());
    eventButton.addEventHandler(LooseEvent.GAME_LOSE, event -> renderAsLoss());
  
    initializeTurn(false);
  }
  
  private void renderAsWin() {
    this.prepareToRenderAnyPossibleResult();
    this.renderSpecificResult("youWin");
  }
  
  private void renderAsLoss() {
    this.prepareToRenderAnyPossibleResult();
    this.renderSpecificResult("youLose");
  }
  
  private void renderSpecificResult(String key) {
    this.winLabel.setText(this.resourceBundle.getString(key));
  }
  
  private void prepareToRenderAnyPossibleResult() {
    this.getClient().closeClient();
    this.disableWithdrawalPossibility();
    this.disableBoards();
  }
  
  private void disableBoards() {
    final double opacity = 0.4;
    this.yourBoard.setDisable(true);
    this.yourBoard.setOpacity(opacity);
    this.opponentBoard.setDisable(true);
    this.opponentBoard.setOpacity(opacity);
    this.winLabel.setStyle("-fx-color: green");
  }
  
  private void disableWithdrawalPossibility() {
    this.eventButton.removeEventHandler(OpponentWithdrawEvent.OPPONENT_WITHDRAW,
        opponentConnectedEvent -> opponentWithdraw());
  }
  
}
