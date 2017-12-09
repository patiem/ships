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
            JSONObject jsonReceived = communicationBus.receive();
            System.out.println(jsonReceived);

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
