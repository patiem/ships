package pl.korotkevics.ships.shared.infra.communication.core.json.io;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.conversion.Encoder;
import pl.korotkevics.ships.shared.infra.communication.api.io.Sender;
import pl.korotkevics.ships.shared.infra.communication.core.json.conversion.JsonEncoder;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * It sends a message to an output stream.
 * @author Piotr Czyż, Magda Aarsman, Sandor Korotkevics
 * @see Sender
 * @see JsonSender
 * @see Encoder
 * @see JsonEncoder
 * @see Message
 * @since 2017-12-07
 */

public class JsonSender implements Sender {

  private static final String ENCODING = "UTF-8";

  private final Target logger = new SharedLogger(JsonSender.class);

  private final OutputStream outputStream;

  /**
   * @param outputStream it will write to this stream.
   */
  public JsonSender(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  /**
   * It converts a message into a JsonElement,
   * and then sends it to an output stream.
   *
   * @param message a message to send.
   */
  @Override
  public void send(Message message) {
    PrintWriter printWriter = null;
    try {
      printWriter = new PrintWriter(new OutputStreamWriter(outputStream, ENCODING), true);
      Encoder encoder = new JsonEncoder();
      printWriter.println(encoder.encode(message));
    } catch (UnsupportedEncodingException e) {
      logger.error(e.getMessage());
    }
  }
}
