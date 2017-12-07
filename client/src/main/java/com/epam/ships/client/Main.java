package com.epam.ships.client;

import com.epam.ships.communication.api.Receiver;
import com.epam.ships.communication.api.Sender;
import com.epam.ships.communication.impl.BaseSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Client is up and running.");
        Sender sender;
        try (Socket socket = new Socket("127.0.0.1", 8189)) {
            sender = new BaseSender(socket.getOutputStream());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "John");
            sender.send(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
