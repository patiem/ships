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
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

@Test
public class BaseMessageTest {
  
  public void stringRepresentationIsProper() {
    //given - when
    Message message = new MessageBuilder().withAuthor(Author.SERVER).withStatement("Hello World")
                          .withHeader(Header.WIN).build();
    //then
    assertEquals(message.toString(), "BaseMessage(header=WIN, status=OK, author=SERVER, " +
                                         "statement=Hello World, fleet=Fleet(fleet={}))");
  }
  
  public void defaultMessagesAreEqual() {
    //given
    Message firstMessageToCompare = new MessageBuilder().build();
    Message secondMessageToCompare = new MessageBuilder().build();
    //when - then
    assertEquals(firstMessageToCompare, secondMessageToCompare);
  }
  
  
  public void defaultMessagesHaveSameHashcode() {
    //given
    Message firstMessageToCompare = new MessageBuilder().build();
    Message secondMessageToCompare = new MessageBuilder().build();
    //when - then
    assertTrue(firstMessageToCompare.hashCode()==secondMessageToCompare.hashCode());
  }
  
  public void equalsWorksAsExpectedForSameMessages() {
    //given - when
    Message firstMessageToCompare = new MessageBuilder()
                                        .withFleet(this.produceFleet())
                                        .withAuthor(Author.CLIENT)
                                        .withHeader(Header.MANUAL_PLACEMENT)
                                        .withStatement("Hey hey hey")
                                        .build();
  
    Message secondMessageToCompare = new MessageBuilder()
                                        .withFleet(this.produceFleet())
                                        .withAuthor(Author.CLIENT)
                                        .withHeader(Header.MANUAL_PLACEMENT)
                                        .withStatement("Hey hey hey")
                                        .build();
    //then
    assertEquals(firstMessageToCompare, secondMessageToCompare);
  }
  
  public void equalsWorksAsExpectedForDifferentMessages() {
    //given - when
    Message firstMessageToCompare = new MessageBuilder()
                                        .withFleet(this.produceFleet())
                                        .withHeader(Header.RANDOM_PLACEMENT)
                                        .build();
    
    Message secondMessageToCompare = new MessageBuilder()
                                         .withFleet(this.produceFleet())
                                         .withHeader(Header.MANUAL_PLACEMENT)
                                         .withStatement("Yeah")
                                         .build();
    //then
    assertNotEquals(firstMessageToCompare, secondMessageToCompare);
  }
  
  public void hashCodesAreEqualForSameMessages() {
    //given - when
    Message firstMessageToCompare = new MessageBuilder()
                                        .withFleet(this.produceFleet())
                                        .withAuthor(Author.CLIENT)
                                        .withHeader(Header.MANUAL_PLACEMENT)
                                        .withStatement("CAFEBABE")
                                        .build();
  
    Message secondMessageToCompare = new MessageBuilder()
                                         .withFleet(this.produceFleet())
                                         .withAuthor(Author.CLIENT)
                                         .withHeader(Header.MANUAL_PLACEMENT)
                                         .withStatement("CAFEBABE")
                                         .build();
    //then
    assertTrue(firstMessageToCompare.hashCode() == secondMessageToCompare.hashCode());
  }
  
  public void hashCodesAreNotEqualForDifferentMessages() {
    //given - when
    Message firstMessageToCompare = new MessageBuilder()
                                        .withFleet(this.produceFleet())
                                        .withAuthor(Author.SERVER)
                                        .withHeader(Header.MANUAL_PLACEMENT)
                                        .withStatement("SCALA")
                                        .build();
    
    Message secondMessageToCompare = new MessageBuilder()
                                         .withFleet(this.produceFleet())
                                         .withHeader(Header.MANUAL_PLACEMENT)
                                         .withStatement("OPENJDK")
                                         .build();
    //then
    assertFalse(firstMessageToCompare.hashCode() == secondMessageToCompare.hashCode());
  }
  
  
  private Fleet produceFleet() {
    List<Ship> ships = Arrays.asList(Ship.ofMasts(Mast.ofIndex("5")),
        Ship.ofMasts(Mast.ofIndex("2"), Mast.ofIndex("3"), Mast.ofIndex("1")),
        Ship.ofMasts(Mast.ofIndex("21"), Mast.ofIndex("23"), Mast.ofIndex("24")));
    return Fleet.ofShips(ships);
  }
  
}