package pl.korotkevics.ships.shared.infra.communication.core.message;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BaseMessageTest {
  @Test
  public void stringRepresentationIsProper() {
    //given - when
    Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withStatement("Hello World")
        .withHeader(Header.WIN)
        .build();
    //then
    assertEquals(message.toString(), "BaseMessage(header=WIN, status=OK, author=SERVER, statement=Hello World, fleet=Fleet(fleet={}))");
  }
}