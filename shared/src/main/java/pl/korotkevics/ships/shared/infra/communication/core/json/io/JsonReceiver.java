package pl.korotkevics.ships.shared.infra.communication.core.json.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.conversion.Decoder;
import pl.korotkevics.ships.shared.infra.communication.api.io.Receiver;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;
import pl.korotkevics.ships.shared.infra.communication.core.json.conversion.JsonDecoder;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Receives a message from an input stream.
 *
 * @author Piotr Czyż, Magda Aarsman, Sandor Korotkevics
 * @see Receiver
 * @see JsonReceiver
 * @see Decoder
 * @see JsonDecoder
 * @see Message
 * @since 2017-12-07
 */

public class JsonReceiver implements Receiver {

  private static final String CHARSET_NAME = "UTF-8";
  private final InputStream inputStream;

  /**
   * @param inputStream to read from.
   */
  public JsonReceiver(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  /**
   * Reads from input stream, converts it first
   * to a JsonElement and then to a BaseMessage.
   * If there is nothing to read from an input stream,
   * a corresponding BaseMessage is returned as well.
   *
   * @return BaseMessage input interpreted as a message.
   */
  @Override
  public Message receive() {
    Scanner scanner = new Scanner(inputStream, CHARSET_NAME);
    StringBuilder stringBuilder = new StringBuilder();
    if (scanner.hasNextLine()) {
      stringBuilder.append(scanner.nextLine());
    } else {
      return new MessageBuilder()
          .withHeader(Header.CONNECTION)
          .withStatus(Status.END)
          .withStatement("End of a message")
          .build();
    }
    Decoder<JsonElement> jsonDecoder = new JsonDecoder();
    JsonParser jsonParser = new JsonParser();
    JsonElement jsonElement = jsonParser.parse(stringBuilder.toString());
    return jsonDecoder.decode(jsonElement);
  }
}
