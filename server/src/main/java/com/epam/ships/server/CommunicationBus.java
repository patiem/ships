package com.epam.ships.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class CommunicationBus {

    private static final Logger logger = LogManager.getLogger(CommunicationBus.class);
    private final AppServer appServer;
    private List<WrappedClient> clients;

    CommunicationBus() throws IOException {
        appServer = new AppServer(8189);
        clients = new ArrayList<>();
        logger.info("Server is up and connectClients.");
    }

    void start() throws IOException {
         appServer.connectClients();
         for(Socket socketClient : appServer.getClients()){
             wrapClient(socketClient);
         }
    }

    private void wrapClient(Socket socketClient) throws IOException {
        WrappedClient wrappedClient = new WrappedClient(socketClient);
        clients.add(wrappedClient);
    }

    JSONObject receive() {
        return this.clients.get(0).receive();
    }

    void closeClient() {
        System.out.println("close client connetction");
        clients.get(0).close();
        clients.remove(0);
    }

    boolean hasClient() {
        return !clients.isEmpty();
    }

    void stop() {
        for(WrappedClient c : clients) {
            c.close();
        }
        clients.removeAll(clients);
    }

    public void send(JSONObject response) throws UnsupportedEncodingException {
        this.clients.get(0).send(response);
    }
}
