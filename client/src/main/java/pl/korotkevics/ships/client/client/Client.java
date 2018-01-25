package pl.korotkevics.ships.client.client;

import javafx.scene.control.Button;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.io.Receiver;
import pl.korotkevics.ships.shared.infra.communication.api.io.Sender;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;
import pl.korotkevics.ships.shared.infra.communication.core.json.io.JsonReceiver;
import pl.korotkevics.ships.shared.infra.communication.core.json.io.JsonSender;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Enables communication with server side.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-13
 */
public class Client implements Runnable {
  private static final Target logger = new SharedLogger(Client.class);
  private final MessageHandler messageHandler;
  private Socket clientSocket;
  private volatile boolean shouldRun;

  /**
   * Creates Client instance.
   *
   * @param messageHandler - messageHandler instance
   * @param shouldRun      - flag informing if client should start/run
   */
  public Client(final MessageHandler messageHandler, boolean shouldRun) {
    this.messageHandler = messageHandler;
    this.shouldRun = shouldRun;
  }

  /**
   * Connecting client with server using providing address and port.
   *
   * @param ipAddress - server ip address
   * @param port      - server port
   * @param socket    - socket to connect on.
   * @return true if success, false on failure
   */
  public boolean connect(final String ipAddress, final int port, Socket socket) {
    try {
      clientSocket = socket;
      final InetAddress address = InetAddress.getByName(ipAddress);
      final int connectionTimeout = 500;
      clientSocket.connect(new InetSocketAddress(address, port), connectionTimeout);
    } catch (IOException | IllegalArgumentException e) {
      logger.error(e.getMessage());
      return false;
    }
    return true;
  }

  /**
   * Override run method of Runnable interface.
   * Setting name of thread to "Client listen Thread" and start listen loop.
   */
  @Override
  public void run() {
    Thread.currentThread().setName("Client listen Thread");
    listenLoop();
  }

  /**
   * Closing client, stopping listen thread.
   */
  public void closeClient() {
    this.shouldRun = false;
    if (clientSocket == null) {
      return;
    }

    try {
      clientSocket.close();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  private void listenLoop() {

    while (shouldRun && !messageHandler.isEndConnectionTriggered()) {
      try {
        Receiver receiver = new JsonReceiver(clientSocket.getInputStream());
        Message message = receiver.receive();

        logger.info(message);

        messageHandler.handle(message);
//            if (message.getHeader().equals(Header.SHOT) || message.getHeader().equals(Header.HIT) || message.getHeader().equals(Header.SHIP_DESTROYED) ||
//                message.getHeader().equals(Header.MISS) || message.getHeader().equals(Header.YOUR_TURN)  ) {
//              JsonSender sender = new JsonSender(clientSocket.getOutputStream());
//              Message confirm = new MessageBuilder().withHeader(Header.CONFIRMATION).build();
//              sender.send(confirm);
//              logger.info(confirm);
//            }
      } catch (IOException | IllegalStateException e) {
        logger.error(e.getMessage());
      }
    }

    closeSocket();
  }

  private void closeSocket() {
    final int waitTime = 100;
    try {
      Thread.sleep(waitTime);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      logger.error(e.getMessage());
    }
    try {
      clientSocket.close();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * Sending message containing shot index to server.
   *
   * @param shotIndex - index of last shot
   */
  public void sendShot(int shotIndex) {
    try {
      Sender sender = new JsonSender(clientSocket.getOutputStream());
      Message shot = new MessageBuilder().withHeader(Header.SHOT)
          .withAuthor(Author.CLIENT)
          .withStatement(String.valueOf(shotIndex))
          .build();
      sender.send(shot);
      isReachable();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * Sending message containing fleet placement to server.
   *
   * @param fleet - Fleet object representing user fleet.
   */
  public void sendFleet(Fleet fleet) {
    try {
      Sender sender = new JsonSender(clientSocket.getOutputStream());
      Message fleetMsg = new MessageBuilder()
          .withHeader(Header.MANUAL_PLACEMENT)
          .withAuthor(Author.CLIENT)
          .withFleet(fleet)
          .build();
      sender.send(fleetMsg);
      isReachable();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * Set handle to gui to trigger some events in response for server message.
   *
   * @param button - gui handler as button.
   */
  public void setEventTrigger(Button button) {
    messageHandler.setCurrentEventButton(button);
  }


  /**
   * Sending request for random fleet placement to server.
   */
  public void askForRandomFleet() {
    Message askForRandomFleet = new MessageBuilder()
        .withHeader(Header.RANDOM_PLACEMENT)
        .withAuthor(Author.CLIENT)
        .build();

    try {
      Sender sender = new JsonSender(clientSocket.getOutputStream());
      sender.send(askForRandomFleet);
      isReachable();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  void isReachable() throws IOException {
    boolean reachable = clientSocket.getInetAddress().isReachable(30000);
    if (!reachable) {
      Message message = new MessageBuilder()
          .withStatus(Status.END)
          .withHeader(Header.CONNECTION)
          .build();

      messageHandler.handle(message);
    }
  }

}
