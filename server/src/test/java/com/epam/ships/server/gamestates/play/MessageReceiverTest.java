package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.WrappedClient;
import org.testng.annotations.Test;

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

  public void shouldBeTrueIfLastMessageWasShot() {
    //given
    Message lastMessage = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.SHOT)
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

  public void shouldBeFalseIfLastMessageWasNotShot() {
    //given
    Message lastMessage = new MessageBuilder()
        .withAuthor(Author.AUTO)
        .withHeader(Header.CONNECTION)
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