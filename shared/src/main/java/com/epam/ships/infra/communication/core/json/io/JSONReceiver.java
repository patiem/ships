package com.epam.ships.infra.communication.core.json.io;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.api.io.Receiver;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;
import com.epam.ships.infra.communication.core.json.conversion.JSONDecoder;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Piotr, Magda, Sandor
 * @see Receiver
 * @see JSONReceiver
 * @see Decoder
 * @see JSONDecoder
 * @see Message
 * <p>
 * It receives a message from an input stream.
 * @since 2017-12-07
 */

public class JSONReceiver implements Receiver {

  private final InputStream inputStream;

  /**
   * @param inputStream it will read from this stream.
   */
  public JSONReceiver(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  /**
   * It reads from input stream, coverts it first
   * to a JsonElement and the to a BaseMessage.
   * <p>
   * If there is nothing to read from an input stream,
   * it returns an corresponding BaseMessage as well.
   *
   * @return BaseMessage input interpreted as a message.
   */
  @Override
  public Message receive() {
    Scanner scanner = new Scanner(inputStream, "UTF-8");
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
    Decoder<JsonElement> jsonDecoder = new JSONDecoder();
    JsonParser jsonParser = new JsonParser();
    JsonElement jsonElement = jsonParser.parse(stringBuilder.toString());
    return jsonDecoder.decode(jsonElement);
  }
}
