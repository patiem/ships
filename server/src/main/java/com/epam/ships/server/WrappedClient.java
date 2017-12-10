package com.epam.ships.server;

import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.api.Receiver;
import com.epam.ships.communication.api.Sender;
import com.epam.ships.communication.impl.BaseReceiver;
import com.epam.ships.communication.impl.BaseSender;
import com.epam.ships.communication.impl.message.MessageBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

class WrappedClient {

    private static Logger logger = LogManager.getLogger(WrappedClient.class);

    private final Socket socket;
    private Sender out;
    private Receiver in;

    WrappedClient(Socket socketClient) {
        socket = socketClient;
        this.setUpIO();
    }

    private void setUpIO() {
        try {
            this.out = new BaseSender(socket.getOutputStream());
            this.in = new BaseReceiver(socket.getInputStream());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    Message receive() {
        try {
            return this.in.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MessageBuilder().build();
    }

    void close() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void send(Message message) {
        this.out.send(message);
    }
}
