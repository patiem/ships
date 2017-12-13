package com.epam.ships.server;

import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr, Magda
 * @since 2017-12-09
 *
 * It starts a server socket and
 * accepts two client sockets.
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
     *
     * @throws IOException
     */
    public void connectClients() throws IOException {
        clientSockets = new ArrayList<>();
        logger.info("Waiting for the 1st client.. ");
        acceptClient();
//        logging.info("waiting for the 2nd client..");
//        acceptClient();
        logger.info("Clients are connected");
    }

    /**
     * It accepts a client socket
     * while storing it in a list.
     *
     * @throws IOException
     */
    private void acceptClient() throws IOException {
        Socket client = serverSocket.accept();
        clientSockets.add(client);
    }

    /**
     * @return a list of stored client sockets.
     */
    public List<Socket> getClientSockets() {
        return clientSockets;
    }
}
