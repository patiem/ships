package pl.korotkevics.ships.shared.infra.communication.core.json.io;

import org.testng.annotations.Test;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.io.Sender;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.testng.Assert.assertEquals;

@Test
public class JsonSenderTest {
  public void itSendsProperlyEncodedMessage() {
    //given
    OutputStream outputStream = new ByteArrayOutputStream();
    Sender sender = new JsonSender(outputStream);
    Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.WIN)
        .build();
    //when
    sender.send(message);
    //then
    assertEquals(outputStream.toString(), "{\"header\":\"WIN\",\"status\":\"OK\"," +
        "\"author\":\"SERVER\",\"statement\":\"\",\"fleet\":{\"fleet\":{}}}" + System.lineSeparator());
  }
}