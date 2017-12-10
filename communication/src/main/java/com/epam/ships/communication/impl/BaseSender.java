package com.epam.ships.communication.impl;

import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.api.Sender;
import com.epam.ships.communication.api.json.JSONEncoder;
import com.epam.ships.communication.impl.json.BaseEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.*;

public class BaseSender implements Sender {

    private static final String ENCODING = "UTF-8";

    private static Logger logger = LogManager.getLogger(BaseSender.class);

    private final OutputStream outputStream;

    public BaseSender(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void send (Message message) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(outputStream, ENCODING), true);
            JSONEncoder jsonEncoder = new BaseEncoder();
            printWriter.println(jsonEncoder.encode(message));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }
}
