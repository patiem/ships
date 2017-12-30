package com.epam.ships.server;

import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * It starts a server socket and accepts two client sockets.
 * @author Piotr Czyż
 * @since 2017-12-09
  */
class AppServer {

  private final Target logger = new SharedLogger(AppServer.class);

  private final ServerSocket serverSocket;

  private List<Socket> clientSockets;

  AppServer(int port) throws IOException {
    logger.info("Server is up and waiting for clients..");
    this.serverSocket = new ServerSocket(port);
  }

  /**
   * It connects two clients.
   * @throws IOException - if accepting client failed
   */
  void connectClients() throws IOException {
    clientSockets = new ArrayList<>();
    logger.info("Waiting for the 1st client.. ");
    acceptClient();
    logger.info("1st client connected... ");
    logger.info("waiting for the 2nd client..");
    acceptClient();
    logger.info("2nd client connected... ");
    logger.info("Clients are connected");
  }

  /**
   * It accepts a client socket
   * while storing it in a list.
   *
   * @throws IOException on failure
   */
  private void acceptClient() throws IOException {
    Socket client = serverSocket.accept();
    clientSockets.add(client);
  }

  /**
   * @return a list of stored client sockets.
   */
  List<Socket> getClientSockets() {
    return clientSockets;
  }
}
