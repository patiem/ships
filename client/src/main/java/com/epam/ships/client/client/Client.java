package com.epam.ships.client.client;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.api.io.Sender;
import com.epam.ships.infra.communication.core.json.io.JSONReceiver;
import com.epam.ships.infra.communication.core.json.io.JSONSender;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Runnable {
    private static final Target logger = new SharedLogger(Client.class);

    private Socket clientSocket = null;
    private final MessageHandler messageHandler;
    private volatile boolean shouldRun;

    public Client() {
        this.messageHandler = new MessageHandler();
        shouldRun = true;
    }

    public boolean connect(final String ipAddress, final int port) {
        try {
            clientSocket = new Socket();
            final InetAddress address = InetAddress.getByName(ipAddress);
            final int connectionTimeout = 500;
            clientSocket.connect(new InetSocketAddress(address, port), connectionTimeout);
        } catch (IOException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        try {
            listenLoop();
        } catch (final IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void closeClient() {
        this.shouldRun = false;
        if(clientSocket == null) {
            return;
        }

        try {
            clientSocket.close();
            Platform.exit();
            System.exit(0);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void listenLoop() throws IOException {
        int endTriggerCount = 0;
        while (shouldRun && endTriggerCount < 1) {
            Receiver receiver = new JSONReceiver(clientSocket.getInputStream());
            Message message = receiver.receive();

            if(!shouldRun) {
                break;
            }

            logger.info(message);
            if(endWillBeTriggered(message)) {
                endTriggerCount++;
            }

            try {
                messageHandler.handle(message);
            } catch (IllegalStateException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private boolean endWillBeTriggered(Message message) {
        return message.getStatus().equalsIgnoreCase("end");
    }

    public void sendShot(int shotIndex) {
        try {
            Sender sender = new JSONSender(clientSocket.getOutputStream());
            Message shot = new MessageBuilder().withHeader("shot")
                    .withAuthor("client").withStatus("OK").withStatement(String.valueOf(shotIndex)).build();
            sender.send(shot);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void setEventTrigger(Button button) {
        messageHandler.setCurrentEventButton(button);
    }
}
