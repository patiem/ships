package com.epam.ships.communication.impl;

import com.epam.ships.communication.api.Receiver;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseReceiver implements Receiver {

    private static Logger logger = LogManager.getLogger(BaseReceiver.class);

    private final InputStream inputStream;

    public BaseReceiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public JSONObject receive() {
        try (OutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
            return new JSONObject(outputStream.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new JSONObject();
    }
}
