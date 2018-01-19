package pl.korotkevics.ships.shared.infra.communication.core.message;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.StringUtils;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;

import java.lang.reflect.Type;

/**
 * Value-object used as a communication mean.
 * @since 2017-12-10
 */
@AutoValue
public abstract class BaseMessage implements Message {

  public abstract Header getHeader();

  public abstract Status getStatus();

  public abstract Author getAuthor();

  public abstract String getStatement();

  public abstract Fleet getFleet();

  public static Builder builder() {
    return new AutoValue_BaseMessage.Builder()
            .setHeader(Header.DEFAULT)
            .setStatus(Status.OK)
            .setAuthor(Author.AUTO)
            .setStatement(StringUtils.EMPTY)
            .setFleet(Fleet.empty());
  }
  // this used for Gson serialization
  public static Type getGeneratedType(){
    return AutoValue_BaseMessage.class;
  }

  /**
   * Builder for {@link BaseMessage} object.
   */
  @AutoValue.Builder
  public static abstract class Builder {
    public abstract Builder setHeader(Header header);
    public abstract Builder setStatus(Status status);
    public abstract Builder setAuthor(Author author);
    public abstract Builder setStatement(String statement);
    public abstract Builder setFleet(Fleet fleet);

    public abstract BaseMessage build();
  }
}
