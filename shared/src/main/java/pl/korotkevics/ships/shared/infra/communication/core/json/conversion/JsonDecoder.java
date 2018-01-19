package pl.korotkevics.ships.shared.infra.communication.core.json.conversion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.conversion.Decoder;
import pl.korotkevics.ships.shared.infra.communication.core.message.BaseMessage;

/**
 * Converts a JsonElement into a BaseMessage instance.
 *
 * @author Sandor Korotkevics
 * @see Decoder
 * @see BaseMessage
 * @since 2017-12-10
 */
public class JsonDecoder implements Decoder<JsonElement> {

  /**
   * Converts a JsonElement instance into a BaseMessage
   * instance.
   *
   * @param jsonElement a JsonElement instance representing
   *                    a BaseMessage.
   * @return BaseMessage a result of conversion of JsonElement into BaseMessage
   */
  @Override
  public Message decode(JsonElement jsonElement) {
    Gson gson = new GsonBuilder().create();

    return gson.fromJson(jsonElement, BaseMessage.class);
  }
}
