package com.epam.ships.infra.communication.api.io;


import com.epam.ships.infra.communication.api.Message;

/**
 * @author Piotr, Magda, Sandor
 * @see Message
 * <p>
 * An implementing class receives an instance of
 * a class implementing BaseMessage interface.
 * @since 2017-12-07
 */
public interface Receiver {
  Message receive();
}
