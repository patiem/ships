package pl.korotkevics.ships.server.communication;

import org.testng.annotations.Test;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;
import pl.korotkevics.ships.shared.infra.logging.api.Target;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Test
public class MessageSenderTest {

  public void shouldSendByHeader() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    Target logger = mock(Target.class);
    WrappedClient wrappedClient = mock(WrappedClient.class);
    Header someHeader = Header.SHOT;

    //when
    MessageSender messageSender = new MessageSender(communicationBus, logger);
    messageSender.send(wrappedClient, someHeader);

    //then
    verify(communicationBus, times(1)).send(wrappedClient, new MessageRepository().getMessage(someHeader));
    verify(logger, times(1)).info("Send message with header: " + someHeader);
  }

  public void shouldSendMessage() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    Target logger = mock(Target.class);
    WrappedClient wrappedClient = mock(WrappedClient.class);
    Header someHeader = Header.HIT;
    Message message = new MessageBuilder()
        .withHeader(someHeader)
        .build();

    //when
    MessageSender messageSender = new MessageSender(communicationBus, logger);
    messageSender.send(wrappedClient, message);

    //then
    verify(communicationBus, times(1)).send(wrappedClient, message);
    verify(logger, times(1)).info("Send message with header: " + message.getHeader());
  }

  public void shouldSendToAll() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    Target logger = mock(Target.class);
    Header someHeader = Header.SHOT;

    //when
    MessageSender messageSender = new MessageSender(communicationBus, logger);
    messageSender.sendToAll(someHeader);

    //then
    verify(communicationBus, times(1)).sendToAll(new MessageRepository().getMessage(someHeader));
    verify(logger, times(1)).info("Send message to both players " + someHeader);
  }
}