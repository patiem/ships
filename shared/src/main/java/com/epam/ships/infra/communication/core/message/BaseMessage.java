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
 * @author Sandor
 * @see MessageBuilder
 * @see BaseMessage
 * <p>
 * Value-object used as a communication mean.
 * <p>
 * Setters are only available to MessageBuilder.
 * @since 2017-12-10
 */

@ToString
@EqualsAndHashCode
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PACKAGE)
public class BaseMessage implements Message {
  //TODO introduce enums?
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
