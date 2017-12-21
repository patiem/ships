package com.epam.ships.infra.communication.api.conversion;

import com.epam.ships.infra.communication.api.Message;

/**
 * @author Sandor
 * @see Message
 *
 * An implementing class has to ensure that
 * T is converted into a class
 * instance implementing Message interface.
 * @since 2017-12-10
 */
public interface Decoder<T> {
    /**
     * @param t it decodes from this type.
     * @return Message an instance of a class
     * implementing Message interface.
     */
    Message decode(T t);
}
