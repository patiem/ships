package com.epam.ships.communication.api;


import org.json.JSONObject;

import java.io.IOException;

public interface Receiver {
    JSONObject receive() throws IOException;
}
