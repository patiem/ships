package com.epam.ships.infra.communication.api.conversion;

import com.epam.ships.infra.communication.api.Message;

/**
 * An implementing class has to ensure that
 * T is converted into a class
 * instance implementing BaseMessage interface.
 * @author Sandor Korotkevics
 * @see Message
 * @since 2017-12-10
 */
public interface Decoder<T> {
  /**
   * Implementing BaseMessage interface.
   * @param type it decodes from this type.
   * @return BaseMessage an instance of a class
   */
  Message decode(T type);
}
