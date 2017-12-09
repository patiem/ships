package com.epam.ships.communication.api;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public interface Sender {
    void send(JSONObject jsonObject) throws UnsupportedEncodingException;
}
