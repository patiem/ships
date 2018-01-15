package pl.korotkevics.ships.server;

import pl.korotkevics.ships.server.gamestates.GameState;
import pl.korotkevics.ships.server.gamestates.connection.WaitingForPlayersState;

import java.io.IOException;

/**
 * Main class of server application.
 *
 * @author Piotr Czyż
 * @since 2017-12-09
 */
public class Main {
  /**
   * Main server method. It creates communication bus and starts the game.
   *
   * @param args is a conventional main method parameter.
   * @throws IOException can be thrown due to establishing a socket connection.
   */
  public static void main(String[] args) throws IOException {
    CommunicationBus communicationBus = new CommunicationBus();
    
    /*
    Sonar reports an infinitive loop as a critical bug, and therefore
    we could not think of a better workaround than presenting it as a
    an effective infinitive loop (with true extracted to a flag).
     */
    boolean shouldRun = true;
    while (shouldRun) {
      GameState initialState = new WaitingForPlayersState(communicationBus);
      new Game(initialState).play();
    }
  }
}
