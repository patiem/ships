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

    public static Logger logger = LogManager.getLogger(BaseReceiver.class);

    private final InputStream inputStream;

    public BaseReceiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public JSONObject receive() {
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new JSONObject(outputStream.toString());
    }
}
