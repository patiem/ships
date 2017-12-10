package com.epam.ships.communication.core;

import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.api.Receiver;
import com.epam.ships.communication.api.json.JSONDecoder;
import com.epam.ships.communication.core.json.BaseDecoder;
import com.epam.ships.communication.core.message.MessageBuilder;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Piotr, Magda, Sandor
 * @since 2017-12-07
 * @see Receiver
 * @see BaseReceiver
 * @see JSONDecoder
 * @see BaseDecoder
 * @see Message
 *
 * It receives a message from an input stream.
 */

public class BaseReceiver implements Receiver {

    private final InputStream inputStream;

    /**
     *
     * @param inputStream it will read from this stream.
     */
    public BaseReceiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     *
     * It reads from input stream, coverts it first
     * to a JSONObject and the to a Message.
     *
     * If there is nothing to read from an input stream,
     * it returns an corresponding Message as well.
     *
     * @return Message input interpreted as a message.
     */
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
