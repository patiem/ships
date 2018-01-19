package pl.korotkevics.ships.server.gamestates.play;

import org.testng.annotations.Test;
import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.MessageReceiver;
import pl.korotkevics.ships.server.WrappedClient;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.core.message.BaseMessage;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class MessageReceiverTest {
  public void shouldReceiveMessageFromPlayer() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    WrappedClient wrappedClient = mock(WrappedClient.class);

    //when
    MessageReceiver messageReceiver = new MessageReceiver(communicationBus);
    messageReceiver.receive(wrappedClient);

    //then
    verify(communicationBus, times(1)).receive(wrappedClient);
  }

  public void shouldBeAShotIfLastMessageWasShot() {
    //given
    Message lastMessage = BaseMessage.builder()
        .setAuthor(Author.SERVER)
        .setHeader(Header.SHOT)
        .build();
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    WrappedClient wrappedClient = mock(WrappedClient.class);
    when(communicationBus.receive(wrappedClient)).thenReturn(lastMessage);

    MessageReceiver messageReceiver = new MessageReceiver(communicationBus);
    messageReceiver.receive(wrappedClient);

    //when
    boolean result = messageReceiver.isAShot();

    //then
    assertTrue(result);
  }

  public void shouldNotBeAShotIfLastMessageWasNotShot() {
    //given
    Message lastMessage = BaseMessage.builder()
        .setAuthor(Author.AUTO)
        .setHeader(Header.CONNECTION)
        .build();
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    WrappedClient wrappedClient = mock(WrappedClient.class);
    when(communicationBus.receive(wrappedClient)).thenReturn(lastMessage);

    MessageReceiver messageReceiver = new MessageReceiver(communicationBus);
    messageReceiver.receive(wrappedClient);
    //when
    boolean result = messageReceiver.isAShot();

    //then
    assertFalse(result);
  }
}