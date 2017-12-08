package com.epam.ships.client;

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
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8189);
            Sender sender = new BaseSender(socket.getOutputStream());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "Magda");
            sender.send(jsonObject);

            Thread.sleep(5000);

            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("name", "Piotr");
            sender.send(jsonObject2);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
