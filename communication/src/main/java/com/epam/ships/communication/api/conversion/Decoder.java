package com.epam.ships.communication.api.conversion;

import com.epam.ships.communication.api.Message;

/**
 * @author Sandor
 * @since 2017-12-10
 * @see Message
 *
 * An implementing class has to ensure that
 * T is converted into a class
 * instance implementing Message interface.
 */
public interface Decoder<T> {
    /**
     *
     * @param t it decodes from this type.
     * @return Message an instance of a class
     * implementing Message interface.
     */
    Message decode(T t);
}
