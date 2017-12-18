package com.epam.ships.client.client;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.api.io.Sender;
import com.epam.ships.infra.communication.core.json.io.JSONReceiver;
import com.epam.ships.infra.communication.core.json.io.JSONSender;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Runnable {
    private static final Target logger = new SharedLogger(Client.class);

    private Socket clientSocket;
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
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        try {
            listenLoop();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void closeClient() {
        this.shouldRun = false;
    }

    private void listenLoop() throws IOException {
        Receiver receiver = new JSONReceiver(clientSocket.getInputStream());
        Message opponentConnect = receiver.receive();
        logger.info(opponentConnect);
        try {
            messageHandler.handle(opponentConnect);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        Message greetings = receiver.receive();
        logger.info(greetings);
        //TODO: in the future receiving in loop and no end loop
        endLoop();
    }

    private void endLoop() {
        final int sleepTimeMs = 300;

        while(shouldRun) {
            try {
                Thread.sleep(sleepTimeMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendShot(int shotIndex) {
        try {
            Sender sender = new JSONSender(clientSocket.getOutputStream());
            Message shot = new MessageBuilder().withHeader("shot")
                    .withAuthor("client").withStatus("OK").withStatement(String.valueOf(shotIndex)).build();
            sender.send(shot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEventTrigger(Button button) {
        messageHandler.setCurrentEventButton(button);
    }
}
