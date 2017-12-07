package com.epam.ships.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class AppServer {

    private static final Logger logger = LogManager.getLogger(AppServer.class);

    private ServerSocket serverSocket;
    List<Socket> clients;

    public AppServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
    }

    public void connectClients() throws IOException {
        logger.info("Waiting for a 1 Client... ");
        acceptClient();
//        logger.info("waiting for a 2 Client...");
//        acceptClient();
        logger.info("Clients connected");
    }

    public void acceptClient() throws IOException {
        Socket client = serverSocket.accept();
        clients.add(client);
    }

    public List<Socket> getClients() {
        return clients;
    }
}
