package pl.korotkevics.ships.server;

import lombok.EqualsAndHashCode;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.io.Receiver;
import pl.korotkevics.ships.shared.infra.communication.api.io.Sender;
import pl.korotkevics.ships.shared.infra.communication.core.json.io.JsonReceiver;
import pl.korotkevics.ships.shared.infra.communication.core.json.io.JsonSender;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents a wrapped client which is used
 * by communication bus.
 *
 * @author Piotr Czyż
 * @see CommunicationBus
 * @since 2018-01-02
 */
@EqualsAndHashCode
public class WrappedClient {

  private final Target logger = new SharedLogger(this.getClass());
  private final Socket socket;
  private Sender out;
  private Receiver in;

  WrappedClient(Socket socketClient) {
    this.socket = socketClient;
    this.setUpIo();
  }

  private void setUpIo() {
    try {
      this.out = new JsonSender(socket.getOutputStream());
      this.in = new JsonReceiver(socket.getInputStream());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  Message receive() {
    return this.in.receive();
  }

  void close() {
    try {
      socket.close();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  void send(final Message message) {
    this.out.send(message);
  }
}
