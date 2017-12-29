package com.epam.ships.infra.communication.core.message;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Value-object used as a communication mean.
 * Setters are only available to MessageBuilder.
 * @author Sandor Korotkevics
 * @see MessageBuilder
 * @see BaseMessage
 * @since 2017-12-10
 */

@ToString
@EqualsAndHashCode
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PACKAGE)
public class BaseMessage implements Message {
  private Header header;
  private Status status;
  private Author author;
  private String statement;
  private Fleet fleet;

  /**
   * It is declared explicitly to
   * limit its access to MessageBuilder only.
   */
  BaseMessage() {
  }
}
