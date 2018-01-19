package pl.korotkevics.ships.shared.infra.communication.core.message;

import org.testng.annotations.Test;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.fleet.Mast;
import pl.korotkevics.ships.shared.fleet.Ship;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class BaseMessageTest {
  
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

  public void checkDefaultValues(){
    BaseMessage defaultMessage = BaseMessage.builder().build();

    assertEquals(defaultMessage.getHeader(), Header.DEFAULT);
    assertEquals(defaultMessage.getAuthor(), Author.AUTO);
    assertEquals(defaultMessage.getStatus(), Status.OK);
    assertEquals(defaultMessage.getStatement(), "");
    assertEquals(defaultMessage.getFleet(), Fleet.empty());
  }
  
  public void equalsWorksAsExpected() {
    //given - when
    Message firstMessageToCompare = BaseMessage.builder()
                                        .setFleet(this.produceFleet())
                                        .setAuthor(Author.CLIENT)
                                        .setHeader(Header.MANUAL_PLACEMENT)
                                        .setStatement("Hey hey hey")
                                        .build();
  
    Message secondMessageToCompare = BaseMessage.builder()
                                        .setFleet(this.produceFleet())
                                        .setAuthor(Author.CLIENT)
                                        .setHeader(Header.MANUAL_PLACEMENT)
                                        .setStatement("Hey hey hey")
                                        .build();
    //then
    assertEquals(firstMessageToCompare, secondMessageToCompare);
  }
  
  public void hashCodeWorksAsExpected() {
    //given - when
    Message firstMessageToCompare = BaseMessage.builder()
                                        .setFleet(this.produceFleet())
                                        .setAuthor(Author.CLIENT)
                                        .setHeader(Header.MANUAL_PLACEMENT)
                                        .setStatement("CAFEBABE")
                                        .build();
  
    Message secondMessageToCompare = BaseMessage.builder()
                                         .setFleet(this.produceFleet())
                                         .setAuthor(Author.CLIENT)
                                         .setHeader(Header.MANUAL_PLACEMENT)
                                         .setStatement("CAFEBABE")
                                         .build();
    //then
    assertTrue(firstMessageToCompare.hashCode() == secondMessageToCompare.hashCode());
  }
  
  private Fleet produceFleet() {
    List<Ship> ships = Arrays.asList(Ship.ofMasts(Mast.ofIndex("5")),
        Ship.ofMasts(Mast.ofIndex("2"), Mast.ofIndex("3"), Mast.ofIndex("1")),
        Ship.ofMasts(Mast.ofIndex("21"), Mast.ofIndex("23"), Mast.ofIndex("24")));
    return Fleet.ofShips(ships);
  }
}