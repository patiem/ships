package com.epam.ships.client.client;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.api.io.Sender;
import com.epam.ships.infra.communication.core.json.io.JSONReceiver;
import com.epam.ships.infra.communication.core.json.io.JSONSender;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Runnable {
    private static final Target logger = new SharedLogger(Client.class);

    private final Socket clientSocket;
    private volatile boolean shouldRun;

    public Client() {
        this.clientSocket = new Socket();
        shouldRun = true;
    }

    public boolean connect(final String ipAddress, final int port) {
        try {
            final InetAddress address = InetAddress.getByName(ipAddress);
            clientSocket.connect(new InetSocketAddress(address, port));
        } catch (IOException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            return false;
        }

        return true;
    }

    public void run() {
        try {
            Sender sender = new JSONSender(clientSocket.getOutputStream());

            Message firstMessage = getMessage("Magda", "Hey, it's Magda :)");
            sender.send(firstMessage);

            Receiver receiver = new JSONReceiver(clientSocket.getInputStream());
            logger.info(receiver.receive());

            Message secondMessage = getMessage("piotr", "Hey, it's Piotr :)");
            sender.send(secondMessage);

            logger.info(receiver.receive());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        endLoop();
    }

    public void closeClient() {
        this.shouldRun = false;
    }

    private void endLoop() {
        final int sleepTimeMs = 300;

        while(shouldRun) {
            try {
                Thread.sleep(sleepTimeMs);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private Message getMessage(final String author, final String statement) {
        return new MessageBuilder()
                        .withHeader("Connection")
                        .withStatus("OK")
                        .withAuthor(author)
                        .withStatement(statement)
                        .build();
    }
}
