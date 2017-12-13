package com.epam.ships.server;

import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;

import java.io.IOException;

public class Main {
    private static final Target logger = new SharedLogger(Main.class);

    public static void main(String[] args) throws IOException {

        CommunicationBus communicationBus = new CommunicationBus();

        while (true) {
            communicationBus.start();
            Game game = new Game(communicationBus);
            game.letsChatALittleBit();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            communicationBus.stop();
        }
    }
}
