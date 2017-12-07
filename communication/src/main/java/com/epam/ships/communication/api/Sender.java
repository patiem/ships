package com.epam.ships.communication.api;

import org.json.JSONObject;

public interface Sender {
    void send(JSONObject jsonObject);
}
