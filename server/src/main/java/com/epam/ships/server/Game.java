package com.epam.ships.server;

import org.json.JSONObject;

import java.io.IOException;

class Game {
    private CommunicationBus communicationBus;

    Game(CommunicationBus communicationBus) {
        this.communicationBus = communicationBus;
    }

    void letsChatALittleBit() throws IOException {
        while (true) {
            if(communicationBus.hasClient()) {
                JSONObject jsonObject = communicationBus.receive();
                communicationBus.closeClient();
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
