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
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Client implements Runnable {
    private final Socket clientSocket;
    private static final Target logger = new SharedLogger(Client.class);

    public Client() {
        this.clientSocket = new Socket();
    }

    public boolean connect(final String ipAddress, final int port) {
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            clientSocket.connect(new InetSocketAddress(address, port));
        } catch (UnknownHostException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            return false;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }

        return true;
    }

    public void run() {
        try {
            Sender sender = new JSONSender(clientSocket.getOutputStream());

            Message firstMessage = getMessage("Connection", "OK",
                    "Magda", "Hey, it's Magda :)");
            sender.send(firstMessage);

            Receiver receiver = new JSONReceiver(clientSocket.getInputStream());
            logger.info(receiver.receive());

            Message secondMessage = getMessage("Connection", "OK",
                    "piotr", "Hey, it's Piotr :)");
            sender.send(secondMessage);

            logger.info(receiver.receive());

        } catch (IOException e) {
            logger.error(e.getMessage());
            //TODO: handle
        }

        endLoop();
    }

    private void endLoop() {
        final int sleepTimeMs = 300;
        boolean flag = true;

        while(flag) {
            try {
                Thread.sleep(sleepTimeMs);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                //TODO: handle
            }
        }
    }

    private Message getMessage(final String header, final String status, final String author, final String statement) {
        return new MessageBuilder()
                        .withHeader(header)
                        .withStatus(status)
                        .withAuthor(author)
                        .withStatement(statement)
                        .build();
    }
}
