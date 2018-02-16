package pl.korotkevics.ships.server.communication;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;
import pl.korotkevics.ships.shared.persistence.SingleTranscriptDao;
import pl.korotkevics.ships.shared.persistence.SingleTranscriptRecord;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
  private SingleTranscriptDao singleTranscriptDao = new SingleTranscriptDao();

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
    clients = new ArrayList<>();
    serverClients.forEach(this::wrapClient);
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
    WrappedClient wrappedClient = new WrappedClient(socketClient);
    clients.add(wrappedClient);
  }

  /**
   * It receives message from a player.
   *
   * @param sender player who sent a message
   * @return received message
   */
  public Message receive(final WrappedClient sender) {
    return sender.receive();
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
}
