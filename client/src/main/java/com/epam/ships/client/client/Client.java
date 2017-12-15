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
    private String ipAddress;
    private int port;
    private final Socket clientSocket;

    private static final Target logger = new SharedLogger(Client.class);

    public Client() {
        this.clientSocket = new Socket();
    }

    public boolean connect(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;

        InetAddress address = null;

        try {
            address = InetAddress.getByName(this.ipAddress);
        } catch (UnknownHostException e) {
            logger.error(e.getMessage());
            return false;
        }

        try {
            clientSocket.connect(new InetSocketAddress(address, this.port));
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return false;
        }

        return true;
    }

    public void run() {
        Sender sender = null;
        try {
            sender = new JSONSender(clientSocket.getOutputStream());
        } catch (IOException e) {
            logger.error(e.getMessage());
            //TODO: handle
        }

        //First Message
            Message firstMessage = new MessageBuilder()
                    .withHeader("Connection")
                    .withStatus("OK")
                    .withAuthor("Magda")
                    .withStatement("Hey, it's Magda :-)")
                    .build();
            sender.send(firstMessage);
        Receiver receiver = null;

        try {
            receiver = new JSONReceiver(clientSocket.getInputStream());
            logger.info(receiver.receive());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            //TODO: handle
        }

        //Second message
            Message secondMessage = new MessageBuilder()
                    .withHeader("Connection")
                    .withStatus("OK")
                    .withAuthor("piotr")
                    .withStatement("Hey, it's Piotr :-)")
                    .build();
            sender.send(secondMessage);

        try {
            logger.info(receiver.receive());
        } catch (IOException e) {
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
