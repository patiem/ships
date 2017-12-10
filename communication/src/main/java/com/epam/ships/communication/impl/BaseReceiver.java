package com.epam.ships.communication.impl;

import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.api.Receiver;
import com.epam.ships.communication.api.json.JSONDecoder;
import com.epam.ships.communication.impl.json.BaseDecoder;
import com.epam.ships.communication.impl.message.MessageBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class BaseReceiver implements Receiver {

    private final InputStream inputStream;

    public BaseReceiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Message receive() {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        if(scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        else {
            return new MessageBuilder()
                    .withHeader("Connection")
                    .withStatus("END")
                    .withAuthor("Auto")
                    .withStatement("End of a message")
                    .build();
        }
        JSONDecoder baseDecoder = new BaseDecoder();
        return baseDecoder.decode(new JSONObject(stringBuilder.toString()));
    }
}
