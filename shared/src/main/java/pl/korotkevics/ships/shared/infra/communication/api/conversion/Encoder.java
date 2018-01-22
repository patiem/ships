package pl.korotkevics.ships.shared.infra.communication.api.conversion;

import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * An implementing class has to ensure that a class
 * instance implementing BaseMessage interface is converted
 * into T.
 *
 * @author Sandor Korotkevics
 * @see Message
 * @since 2017-12-10
 */
public interface Encoder<T> {

  /**
   * @param message an instance of a class implementing
   *                BaseMessage interface.
   * @return T it encodes into this type.
   */
  T encode(Message message);
}
