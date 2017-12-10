package com.epam.ships.communication.api;

/**
 * @author Piotr, Magda, Sandor
 * @since 2017-12-07
 * @see Message
 *
 * An implementing class sends an instance of
 * a class implementing Message interface.
 */
public interface Sender {
    void send(Message message);
}
