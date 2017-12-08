package com.epam.ships.communication.impl;

import com.epam.ships.communication.api.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class BaseSender implements Sender {

    private static final String ENCODING = "UTF-8";

    private static Logger logger = LogManager.getLogger(BaseSender.class);

    private final OutputStream outputStream;

    public BaseSender(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void send(JSONObject jsonObject) {
        try {
            this.outputStream.write(jsonObject.toString().getBytes(ENCODING));
            this.outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
