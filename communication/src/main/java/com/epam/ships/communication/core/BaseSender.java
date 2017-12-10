package com.epam.ships.communication.core;

import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.api.Receiver;
import com.epam.ships.communication.api.Sender;
import com.epam.ships.communication.api.json.JSONDecoder;
import com.epam.ships.communication.api.json.JSONEncoder;
import com.epam.ships.communication.core.json.BaseDecoder;
import com.epam.ships.communication.core.json.BaseEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author Piotr, Magda, Sandor
 * @since 2017-12-07
 * @see Sender
 * @see BaseSender
 * @see JSONEncoder
 * @see BaseEncoder
 * @see Message
 *
 * It sends a message to an output stream.
 */

public class BaseSender implements Sender {

    private static final String ENCODING = "UTF-8";

    private static Logger logger = LogManager.getLogger(BaseSender.class);

    private final OutputStream outputStream;

    /**
     *
     * @param outputStream it will write to this stream.
     */
    public BaseSender(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     *
     * It converts a message into a JSONObject,
     * and then sends it to an output stream.
     *
     * @param message a message to send.
     */
    @Override
    public void send(Message message) {
        /*TODO connection is never closed, think of an elegant solution (try-with-resources won't help here, since it closes socket)*/
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
