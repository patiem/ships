package com.epam.ships.server;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.api.io.Sender;
import com.epam.ships.infra.communication.core.json.io.JSONReceiver;
import com.epam.ships.infra.communication.core.json.io.JSONSender;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.net.Socket;

@EqualsAndHashCode
class WrappedClient {

  private final Target logger = new SharedLogger(this.getClass());

  private final Socket socket;
  private Sender out;
  private Receiver in;

  WrappedClient(Socket socketClient) {
    this.socket = socketClient;
    this.setUpIO();
  }

  private void setUpIO() {
    try {
      this.out = new JSONSender(socket.getOutputStream());
      this.in = new JSONReceiver(socket.getInputStream());
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
