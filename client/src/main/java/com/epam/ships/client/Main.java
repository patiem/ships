package com.epam.ships.client;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.api.io.Sender;
import com.epam.ships.infra.communication.core.json.io.JSONReceiver;
import com.epam.ships.infra.communication.core.json.io.JSONSender;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.application.Application;

import java.io.IOException;
import java.net.Socket;

public class Main {
    private static final Target logger = new SharedLogger(Main.class);
    
    private static final String IP_ADDRESS = "127.0.0.1";
    
    private static final int PORT = 8189;

    public static void main(String[] args) {
        logger.info("Client is up and running.");
        new Thread(() -> Application.launch(GuiMain.class)).start();
        try (Socket socket = new Socket(IP_ADDRESS, PORT)) {
            Sender sender = new JSONSender(socket.getOutputStream());
            
            //First Message
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
        
            //Second message
            Message secondMessage = new MessageBuilder()
                                            .withHeader("Connection")
                                            .withStatus("OK")
                                            .withAuthor("piotr")
                                            .withStatement("Hey, it's Piotr :-)")
                                            .build();
            sender.send(secondMessage);
        
            logger.info(receiver.receive());
        
            boolean flag = true;
            while(flag) {
                Thread.sleep(300);
            }
        
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
}

