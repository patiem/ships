package pl.korotkevics.ships.server.gamestates.play;

import pl.korotkevics.ships.server.MessageReceiver;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;
import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.MessageSender;
import pl.korotkevics.ships.server.TurnManager;
import pl.korotkevics.ships.server.gamestates.endgame.GameEndWithWalkoverState;
import pl.korotkevics.ships.server.gamestates.endgame.GameEndWithWinState;
import pl.korotkevics.ships.server.gamestates.GameState;

import java.util.List;

/**
 * Main class where the play goes.
 *
 * @author Piotr Czyż
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
   * Creates PlayState instance.
   *
   * @param communicationBus client server communication bus
   * @param fleets           list of clients fleets
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
   * Processes gameplay until it has a winner.
   *
   * @return GameState, depends on game flow it can be GameEndWithWalkoverState
   *         or GameEndWithWinState.
   */
  @Override
  public GameState process() {
    sendYourTurnMessage();
    messageReceiver.receive(turnManager.getCurrentPlayer());
    if (messageReceiver.isAShot()) {
      Message shot = messageReceiver.getMessage();
      isGameWon = shotHandler.handle(turnManager.isCurrentPlayerFirstPlayer(), shot);
    } else {
      return new GameEndWithWalkoverState(communicationBus);
    }
    if (isGameWon) {
      return new GameEndWithWinState(communicationBus, turnManager);
    }
    return this;
  }

  private void sendYourTurnMessage() {
    MessageSender messageSender = new MessageSender(communicationBus, logger);
    messageSender.send(turnManager.getCurrentPlayer(), Header.YOUR_TURN);
  }
}
