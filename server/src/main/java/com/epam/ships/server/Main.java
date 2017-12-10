package com.epam.ships.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) throws IOException {

        CommunicationBus communicationBus = new CommunicationBus();

        while (true) {
            communicationBus.start();
            Game game = new Game(communicationBus);
            game.letsChatALittleBit();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            communicationBus.stop();
        }
    }
}
