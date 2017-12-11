package com.epam.ships.client;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.api.io.Sender;
import com.epam.ships.infra.communication.core.json.io.JSONReceiver;
import com.epam.ships.infra.communication.core.json.io.JSONSender;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;

import java.io.IOException;
import java.net.Socket;

public class Main {

    private final static Target logger = new SharedLogger(Main.class);

    private final static String IP_ADDRESS = "127.0.0.1";

    private final static int PORT = 8189;

    public static void main(String[] args) {

        logger.info("Client is up and running.");

        try (Socket socket = new Socket(IP_ADDRESS, PORT)) {

            Sender sender = new JSONSender(socket.getOutputStream());

            //Imitating the 1st client

            Message firstMessage = new MessageBuilder()
                    .withHeader("Connection")
                    .withStatus("OK")
                    .withAuthor("Magda")
                    .withStatement("Hey, it's Magda :-)")
                    .build();
            sender.send(firstMessage);

            Receiver receiver = new JSONReceiver(socket.getInputStream());

            logger.info(receiver.receive());

            Thread.sleep(300);

            //Imitating the 2nd client

            Message secondMessage = new MessageBuilder()
                    .withHeader("Connection")
                    .withStatus("OK")
                    .withAuthor("piotr")
                    .withStatement("Hey, it's Piotr :-)")
                    .build();
            sender.send(secondMessage);

            logger.info(receiver.receive());

            while(true) {
                Thread.sleep(300);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

