package com.epam.ships.server;

import java.io.IOException;

public class Main {
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
