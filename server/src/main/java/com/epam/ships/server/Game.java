package com.epam.ships.server;

import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Piotr, Magda
 * @since 2017-12-09
 *
 * It starts a communication bus
 * and receives messages from it.
 */
class Game {

    private CommunicationBus communicationBus;

    Game (CommunicationBus communicationBus) {
        this.communicationBus = communicationBus;
    }

    /**
     * A draft dummy method used for demo.
     *
     * @throws IOException
     */
    void letsChatALittleBit() throws IOException {
        while (true) {
            JSONObject jsonReceived = communicationBus.receive();

            if (jsonReceived.has("name")) {
                String name = jsonReceived.get("name").toString();
                JSONObject response = new JSONObject();
                if (Character.isUpperCase(name.charAt(0))) {
                    response.put("name", "ok");
                } else {
                    response.put("error", "name should begin with capital letter");
                }
                communicationBus.send(response);
            }

            if (jsonReceived.has("end") && jsonReceived.getBoolean("end")) {
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
