package com.epam.ships.communication.impl;

import com.epam.ships.communication.api.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class BaseReceiver implements Receiver {

    private static Logger logger = LogManager.getLogger(BaseReceiver.class);

    private final InputStream inputStream;

    public BaseReceiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public JSONObject receive() throws IOException {

        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String message = "";
        if(scanner.hasNextLine()) {
            message= scanner.nextLine();
        }
        else {
            message = "{end:true}";
        }
        return new JSONObject(message);
    }
}
