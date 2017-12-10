package com.epam.ships.communication.api.conversion;

import com.epam.ships.communication.api.Message;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see Message
 *
 * An implementing class has to ensure that a class
 * instance implementing Message interface is converted
 * into T.
 */
public interface Encoder<T> {

    /**
     *
     * @param message an instance of a class implementing
     *                Message interface.
     * @return T it encodes into this type.
     */
    T encode(Message message);
}
