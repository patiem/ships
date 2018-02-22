package pl.korotkevics.ships.server.communication;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;
import pl.korotkevics.ships.shared.transcript.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Starts a server and gets its clients' sockets
 * and adds them wrapped into own list.
 *
 * @author Piotr Czyż, Magdalena Aarsman, Sandor Korotkevics
 * @since 2017-12-09
 */
public class CommunicationBus {


  private final Target logger = new SharedLogger(CommunicationBus.class);
  private final List<Socket> serverClients;
  private List<WrappedClient> clients;
  private GameEntity gameEntity;
  private Repository repository;
  private boolean isFirstPlayer = true;

  /**
   * Create communication bus instance.
   *
   * @param serverClients List of clients sockets
   */
  public CommunicationBus(final List<Socket> serverClients) {
    this.serverClients = serverClients;
  }

  /**
   * It ask AppServer for clients and then wrap them into wrapped client.
   */
  public void start() {
    repository = new GameRepository();
    clients = new ArrayList<>();
    serverClients.forEach(this::wrapClient);
    gameEntity = GameEntity.build(serverClients);
    repository.add(gameEntity);
    logger.info("Communication bus started..");
  }

  /**
   * Close clients sockets and clear list of wrapped clients.
   */
  public void stop() {
    clients.forEach(WrappedClient::close);
    clients.clear();
  }

  private void wrapClient(final Socket socketClient) {
    String playerName = getPlayerName();
    WrappedClient wrappedClient = new WrappedClient(socketClient, playerName);
    clients.add(wrappedClient);
  }

  /**
   * It receives message from a player.
   *
   * @param sender player who sent a message
   * @return received message
   */
  public Message receive(final WrappedClient sender) {

    Message message = sender.receive();
    addTranscript(sender, message);
    return message;
  }

  /**
   * It sends message to wrapped client.
   *
   * @param recipient message addressee
   * @param response  message to send
   */
  public void send(final WrappedClient recipient, final Message response) {
    recipient.send(response);
  }

  void sendToAll(final Message message) {
    clients.forEach(wrappedClient -> send(wrappedClient, message));
  }

  /**
   * Gets player which was connected earlier.
   *
   * @return WrappedClient
   */
  public WrappedClient getFirstClient() {
    return this.clients.get(0);
  }

  /**
   * Gets player which was connected as second one.
   *
   * @return WrappedClient
   */
  public WrappedClient getSecondClient() {
    return this.clients.get(1);
  }

  private String getPlayerName() {
    String[] names = {"pl_1", "pl_2"};
    String name = isFirstPlayer ? names[0] : names[1];
    isFirstPlayer = false;
    return name;
  }

  public void addTranscript(WrappedClient player, Message message) {
    if (message.getHeader().equals(Header.MANUAL_PLACEMENT)) {
      gameEntity.addFleet(FleetEntity.build(message.getFleet(), player.getName()));
    } else {
      gameEntity.addTranscript(Transcript.build(message, player.getName()));
    }
    repository.add(gameEntity);
  }
}
