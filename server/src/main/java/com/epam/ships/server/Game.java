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
            JSONObject jsonRecived = communicationBus.receive();
            System.out.println(jsonRecived);
            if(jsonRecived.has("end") && jsonRecived.getBoolean("end")) {
                break;
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
