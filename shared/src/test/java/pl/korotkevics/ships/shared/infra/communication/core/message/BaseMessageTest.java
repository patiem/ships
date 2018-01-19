package pl.korotkevics.ships.shared.infra.communication.core.message;

import org.testng.annotations.Test;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;

import static org.testng.Assert.assertEquals;

public class BaseMessageTest {
  @Test
  public void stringRepresentationIsProper() {
    //given - when
    Message message = BaseMessage.builder()
        .setAuthor(Author.SERVER)
        .setStatement("Hello World")
        .setHeader(Header.WIN)
        .build();
    //then
    assertEquals(message.toString(), "BaseMessage{header=WIN, status=OK, author=SERVER, statement=Hello World, fleet=Fleet(fleet={})}");
  }

  @Test
  public void checkDefaultValues(){
    BaseMessage defaultMessage = BaseMessage.builder().build();

    assertEquals(defaultMessage.getHeader(), Header.DEFAULT);
    assertEquals(defaultMessage.getAuthor(), Author.AUTO);
    assertEquals(defaultMessage.getStatus(), Status.OK);
    assertEquals(defaultMessage.getStatement(), "");
    assertEquals(defaultMessage.getFleet(), Fleet.empty());
  }
}