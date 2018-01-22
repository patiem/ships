package pl.korotkevics.ships.shared.infra.communication.core.message;

import org.apache.commons.lang3.StringUtils;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;

/**
 * Builds a message with given or/and
 * default values.
 * Along with BaseMessage interface allows
 * to encapsulate BaseMessage class.
 *
 * @author Sandor Korotkevics
 * @see BaseMessage
 * @since 2017-12-10
 */

public class MessageBuilder {

  private Header header = Header.DEFAULT;
  private Status status = Status.OK;
  private Author author = Author.AUTO;
  private String statement = StringUtils.EMPTY;
  private Fleet fleet = Fleet.empty();

  /**
   * Builds a message (BaseMessage).
   * Sets default values if else were not provided.
   *
   * @return an unmodifiable BaseMessage instance.
   */
  public final BaseMessage build() {
    final BaseMessage message = new BaseMessage();
    message.setHeader(this.header);
    message.setStatus(this.status);
    message.setAuthor(this.author);
    message.setStatement(this.statement);
    message.setFleet(this.fleet);
    return message;
  }

  /**
   * @param header value.
   * @return a chain instance of MessageBuilder.
   */
  public MessageBuilder withHeader(final Header header) {
    this.header = header;
    return this;
  }

  /**
   * @param status value.
   * @return a chain instance of MessageBuilder.
   */
  public MessageBuilder withStatus(final Status status) {
    this.status = status;
    return this;
  }

  /**
   * @param author value.
   * @return a chain instance of MessageBuilder.
   */
  public MessageBuilder withAuthor(final Author author) {
    this.author = author;
    return this;
  }

  /**
   * @param statement a String value.
   * @return a chain instance of MessageBuilder.
   */
  public MessageBuilder withStatement(final String statement) {
    this.statement = statement;
    return this;
  }

  /**
   * @param fleet object.
   * @return a chain instance of MessageBuilder.
   */
  public MessageBuilder withFleet(final Fleet fleet) {
    this.fleet = fleet;
    return this;
  }
}
