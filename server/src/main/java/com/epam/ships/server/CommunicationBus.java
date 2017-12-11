package com.epam.ships.server;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr, Magda
 * @since 2017-12-09
 *
 * It starts a server, gets its client sockets
 * and adds them wrapped into own list.
 */

class CommunicationBus {

    private final Target logger = new SharedLogger(CommunicationBus.class);

    private final AppServer appServer;

    private List<WrappedClient> clients;

    CommunicationBus() throws IOException {
        appServer = new AppServer(8189);
        clients = new ArrayList<>();
    }

    void start() throws IOException {
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

    void stop() {
        for (WrappedClient c : clients) {
            c.close();
        }
        clients.removeAll(clients);
    }

    Message receive() {
        return this.clients.get(0).receive();
    }

    public void send(Message response) {
        this.clients.get(0).send(response);
    }
}
