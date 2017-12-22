package com.epam.ships.infra.communication.api;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.api.io.Sender;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;

/**
 * @author Sandor
 * @see Receiver
 * @see Sender
 *
 * It is communication mean contract between
 * Receiver and Sender.
 * @since 2017-12-10
 */
public interface Message {

    /**
     * @return header String value.
     */
    Header getHeader();

    /**
     * @return status String value.
     */
    Status getStatus();

    /**
     * @return author String value.
     */
    Author getAuthor();

    /**
     * @return statement String value.
     */
    String getStatement();
    
    /**
     * @return Object implementing Attachable interface.
     */
    Fleet getFleet();
    
}
