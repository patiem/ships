package com.epam.ships.infra.communication.api.conversion;

import com.epam.ships.infra.communication.api.Message;

/**
 * @author Sandor
 * @see Message
 *
 * An implementing class has to ensure that
 * T is converted into a class
 * instance implementing BaseMessage interface.
 * @since 2017-12-10
 */
public interface Decoder<T> {
    /**
     * @param t it decodes from this type.
     * @return BaseMessage an instance of a class
     * implementing BaseMessage interface.
     */
    Message decode(T t);
}
