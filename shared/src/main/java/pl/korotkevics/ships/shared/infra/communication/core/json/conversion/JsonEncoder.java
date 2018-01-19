package pl.korotkevics.ships.shared.infra.communication.core.json.conversion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.conversion.Encoder;
import pl.korotkevics.ships.shared.infra.communication.core.message.BaseMessage;

/**
 * Converts a BaseMessage instance into a JsonElement.
 * @author Sandor Korotkevics
 * @see Encoder
 * @see BaseMessage
 * @since 2017-12-10
 */
public class JsonEncoder implements Encoder<JsonElement> {

  /**
   * Converts an instance of a class implementing
   * BaseMessage interface into a JsonElement instance.
   *
   * @param message an instance of a class implementing
   *                BaseMessage interface.
   * @return a result of conversion of an instance of a class implementing
   *     BaseMessage interface into a JsonElement instance.
   */
  @Override
  public JsonElement encode(Message message) {
    Gson gson = new GsonBuilder()
        .enableComplexMapKeySerialization()
        .create();
    return gson.toJsonTree(message, BaseMessage.getGeneratedType());
  }

}
