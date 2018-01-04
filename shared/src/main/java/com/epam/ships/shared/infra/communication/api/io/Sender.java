package com.epam.ships.shared.infra.communication.api.io;

import com.epam.ships.shared.infra.communication.api.Message;

/**
 * An implementing class sends an instance of
 * a class implementing BaseMessage interface.
 * @author Piotr Czyż, Magda Aarsman, Sandor Korotkevics
 * @see Message
 * @since 2017-12-07
 */
public interface Sender {
  void send(Message message);
}
