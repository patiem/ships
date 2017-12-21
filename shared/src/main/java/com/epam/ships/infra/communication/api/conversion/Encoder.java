package com.epam.ships.infra.communication.api.conversion;

import com.epam.ships.infra.communication.api.Message;

/**
 * @author Sandor
 * @see Message
 * <p>
 * An implementing class has to ensure that a class
 * instance implementing Message interface is converted
 * into T.
 * @since 2017-12-10
 */
public interface Encoder<T> {

    /**
     * @param message an instance of a class implementing
     *                Message interface.
     * @return T it encodes into this type.
     */
    T encode(Message message);
}
