package pl.korotkevics.ships.shared.infra.communication.api.io;


import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * An implementing class receives an instance of
 * a class implementing BaseMessage interface.
 * @author Piotr Czyż, Magdalena Aarsman, Sandor Korotkevics
 * @see Message
 * @since 2017-12-07
 */
public interface Receiver {
  Message receive();
}
