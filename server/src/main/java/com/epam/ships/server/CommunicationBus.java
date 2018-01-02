package com.epam.ships.server;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * It starts a server, gets its client sockets
 * and adds them wrapped into own list.
 *
 * @author Piotr Czyż
 * @since 2017-12-09
 */

public class CommunicationBus {

  private static final int SERVER_PORT = 8189;
  private final Target logger = new SharedLogger(CommunicationBus.class);
  private final AppServer appServer;

  private List<WrappedClient> clients;

  public CommunicationBus() throws IOException {
    appServer = new AppServer(SERVER_PORT);
    clients = new ArrayList<>();
  }

  public void start() {
    appServer.connectClients();
    for (Socket socketClient : appServer.getClientSockets()) {
      wrapClient(socketClient);
    }
    logger.info("Communication bus started..");
  }

  private void wrapClient(Socket socketClient) {
    WrappedClient wrappedClient = new WrappedClient(socketClient);
    clients.add(wrappedClient);
  }

  public void stop() {
    for (WrappedClient c : clients) {
      c.close();
    }

    clients.clear();
  }

  public Message receive(WrappedClient sender) {
    return sender.receive();
  }

  void send(final WrappedClient recipient, final Message response) {
    recipient.send(response);
  }

  void sendToAll(final Message message) {
    clients.forEach(wrappedClient -> send(wrappedClient, message));
  }

  public final WrappedClient getFirstClient() {
    return this.clients.get(0);
  }

  public final WrappedClient getSecondClient() {
    return this.clients.get(1);
  }
}
