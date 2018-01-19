package pl.korotkevics.ships.shared.infra.communication.api;

import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.io.Receiver;
import pl.korotkevics.ships.shared.infra.communication.api.io.Sender;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;

/**
 * Communication mean contract between Receiver and Sender.
 * @author Sandor Korotkevics
 * @see Receiver
 * @see Sender
 * @since 2017-12-10
 */
public interface Message {

  /**
   * @return header String value.
   */
  Header getHeader();

  /**
   * @return status String value.
   */
  Status getStatus();

  /**
   * @return author String value.
   */
  Author getAuthor();

  /**
   * @return statement String value.
   */
  String getStatement();

  /**
   * @return Object implementing Attachable interface.
   */
  Fleet getFleet();

}
