package pl.korotkevics.ships.server.gamestates.play;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;
import pl.korotkevics.ships.server.MessageSender;
import pl.korotkevics.ships.server.TurnManager;
import pl.korotkevics.ships.server.WrappedClient;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

@Test
public class DamageHitNotifierTest {

  public void shouldNotifyAboutHit() {
    //given
    MessageSender messageSender = mock(MessageSender.class);
    TurnManager turnManager = mock(TurnManager.class);
    WrappedClient player = mock(WrappedClient.class);
    WrappedClient opponent = mock(WrappedClient.class);
    when(turnManager.getCurrentPlayer()).thenReturn(player);
    when(turnManager.getOtherPlayer()).thenReturn(opponent);
    Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.HIT)
        .build();

    //when
    new DamageHitNotifier(messageSender, turnManager).notify(message);

    //then
    verify(messageSender, times(1)).send(player, Header.HIT);
    verify(messageSender, times(1)).send(opponent, message);
  }
}