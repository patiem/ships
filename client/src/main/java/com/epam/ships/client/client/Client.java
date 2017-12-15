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
            //First Message
            Message firstMessage = new MessageBuilder()
                    .withHeader("Connection")
                    .withStatus("OK")
                    .withAuthor("Magda")
                    .withStatement("Hey, it's Magda :-)")
                    .build();
            sender.send(firstMessage);

            Receiver receiver = new JSONReceiver(clientSocket.getInputStream());
            logger.info(receiver.receive());

            Thread.sleep(300);

            //Second message
            Message secondMessage = new MessageBuilder()
                    .withHeader("Connection")
                    .withStatus("OK")
                    .withAuthor("piotr")
                    .withStatement("Hey, it's Piotr :-)")
                    .build();
            sender.send(secondMessage);

            logger.info(receiver.receive());

        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            //TODO: handle
        }

        boolean flag = true;

        while(flag) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                //TODO: handle
            }
        }
    }
}
