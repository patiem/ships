package com.epam.ships.infra.communication.api;

import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.api.io.Sender;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see Receiver
 * @see Sender
 *
 * It is communication mean contract between
 * Receiver and Sender.
 */
public interface Message {

    /**
     *
     * @return header String value.
     */
    String getHeader();

    /**
     *
     * @return status String value.
     */
    String getStatus();

    /**
     *
     * @return author String value.
     */
    String getAuthor();

    /**
     *
     * @return statement String value.
     */
    String getStatement();
}
