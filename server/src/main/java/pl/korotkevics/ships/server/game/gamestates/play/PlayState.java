package pl.korotkevics.ships.server.game.gamestates.play;

import pl.korotkevics.ships.server.communication.CommunicationBus;
import pl.korotkevics.ships.server.communication.MessageReceiver;
import pl.korotkevics.ships.server.communication.MessageSender;
import pl.korotkevics.ships.server.communication.WrappedClient;
import pl.korotkevics.ships.server.game.TurnManager;
import pl.korotkevics.ships.server.game.gamestates.GameState;
import pl.korotkevics.ships.server.game.gamestates.endgame.GameEndWithWalkoverState;
import pl.korotkevics.ships.server.game.gamestates.endgame.GameEndWithWinState;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.util.List;

/**
 * Main class where the play goes.
 *
 * @author Piotr Czyż
 * @see MessageReceiver
 * @see CommunicationBus
 * @see TurnManager
 * @see ShotHandler
 * @since 2018-01-02
 */
public class PlayState implements GameState {
  private final MessageReceiver messageReceiver;
  private final ShotHandler shotHandler;
  private final Target logger = new SharedLogger(PlayState.class);
  private final TurnManager turnManager;
  private CommunicationBus communicationBus;
  private boolean isGameWon;

  /**
   * @param communicationBus client server communication bus.
   * @param fleets           list of clients' fleets.
   */
  public PlayState(final CommunicationBus communicationBus, final List<Fleet> fleets) {
    this.communicationBus = communicationBus;
    this.messageReceiver = new MessageReceiver(communicationBus);
    isGameWon = false;
    this.turnManager = new TurnManager(communicationBus.getFirstClient(),
        communicationBus.getSecondClient());
    this.shotHandler = new ShotHandler(communicationBus, turnManager, fleets);
  }

  /**
   * Processing game until it has a winner.
   *
   * @return GameState which can be either game end with walkover
   *    or game end with end depending on a game flow.
   * @see GameEndWithWalkoverState
   * @see GameEndWithWinState
   */
  @Override
  public GameState process() {
    sendYourTurnMessage();
    messageReceiveConfirmation(turnManager.getCurrentPlayer());
    messageReceiver.receive(turnManager.getCurrentPlayer());
    logger.info("WAITING FOR SHOT");
    if (messageReceiver.isAShot()) {
      Message shot = messageReceiver.getMessage();
      isGameWon = shotHandler.handle(turnManager.isCurrentPlayerFirstPlayer(), shot);
      logger.info("WAITING FOR CONFIRMATION");
      this.messageReceiveConfirmation(turnManager.getCurrentPlayer());
      this.messageReceiveConfirmation(turnManager.getOtherPlayer());
    }
    else {
      return new GameEndWithWalkoverState(communicationBus);
    }
    if (isGameWon) {
      return new GameEndWithWinState(communicationBus, turnManager);
    }
    return this;
  }



  private void messageReceiveConfirmation(WrappedClient wrappedClient) {
    logger.error("sprawdzam");
    boolean getYou = false;
    while(!getYou) {
      messageReceiver.receive(wrappedClient);
      logger.error("Odebrałem wiadomość");
      if (messageReceiver.isConfirmation()) {
        logger.error("to było potwierdzenie");
        getYou = true;
      }
    }
    logger.info("CONFIRMED");
  }

  private void sendYourTurnMessage() {
    this.rest();
    MessageSender messageSender = new MessageSender(communicationBus, logger);
    messageSender.send(this.turnManager.getCurrentPlayer(), Header.YOUR_TURN);
  }

  private void rest() {
    final int restTime = 300;
    try {
      Thread.sleep(restTime);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      logger.error(e.getMessage());
    }
  }
}
