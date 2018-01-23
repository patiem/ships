package pl.korotkevics.ships.client.client;

import javafx.scene.control.Button;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.korotkevics.ships.client.JavaFxInitializer;
import pl.korotkevics.ships.client.gui.events.HitShotEvent;
import pl.korotkevics.ships.client.gui.events.LossEvent;
import pl.korotkevics.ships.client.gui.events.MissShotEvent;
import pl.korotkevics.ships.client.gui.events.OpponentConnectedEvent;
import pl.korotkevics.ships.client.gui.events.OpponentWithdrawEvent;
import pl.korotkevics.ships.client.gui.events.ShipDestroyedEvent;
import pl.korotkevics.ships.client.gui.events.TurnChangeEvent;
import pl.korotkevics.ships.client.gui.events.WinEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;

import java.util.EnumMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

public class MessageHandlerTest {

  @BeforeClass
  public void setUp() throws InterruptedException {
    if (!JavaFxInitializer.isLaunched()) {
      JavaFxInitializer.initialize();
    }
  }

  @BeforeMethod
  public void skipTestIfThereIsNoGraphicsSupport() {
    if (!JavaFxInitializer.isEnable()) {
      throw new SkipException("skipping test because of lack of support for graphics");
    }
  }

  @Test
  public void shouldCallConnectionEndTrigger() {
    //given
    ConnectionEndTrigger connectionEndTrigger = mock(ConnectionEndTrigger.class);
    MessageHandler messageHandler = produceMessageHandler(connectionEndTrigger);
    Message message = connectionEndMessage();

    //when
    messageHandler.handle(message);

    //then
    verify(connectionEndTrigger, times(1)).fire(messageHandler.getDispatcherAdapter(), message);
  }

  @Test
  public void shouldCallRightTrigger() {
    //given
    Button eventButton = mock(Button.class);
    //LoseTrigger loseTrigger = new LoseTrigger(lossEvent);
    LoseTrigger loseTrigger = mock(LoseTrigger.class);

    //when
    Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.LOSE)
        .withStatus(Status.OK)
        .build();


    Map<Header, EventTrigger> triggers = new EnumMap<>(Header.class);
    triggers.put(Header.LOSE, loseTrigger);
    DispatcherAdapter dispatcherAdapter = new DispatcherAdapter();
    MessageHandler messageHandler = new MessageHandler(triggers, dispatcherAdapter);
    messageHandler.setCurrentEventButton(eventButton);
    messageHandler.handle(message);
    //then
    verify(loseTrigger, times(1)).fire(dispatcherAdapter, message);
  }


  @Test
  public void shouldReturnTrueAfterTriggerOpponentWithdrawEven() {
    //given
    ConnectionEndTrigger connectionEndTrigger = mock(ConnectionEndTrigger.class);
    MessageHandler messageHandler = produceMessageHandler(connectionEndTrigger);
    Message message = connectionEndMessage();

    //when
    messageHandler.handle(message);

    assertTrue(messageHandler.isEndConnectionTriggered());
  }

  private Message connectionEndMessage() {
    return new MessageBuilder()
        .withHeader(Header.CONNECTION)
        .withStatus(Status.END)
        .withStatement("End of a message")
        .build();
  }

  private MessageHandler produceMessageHandler(EventTrigger eventTrigger) {
    Map<Header, EventTrigger> triggers = new EnumMap<>(Header.class);
    triggers.put(Header.CONNECTION, eventTrigger);
    MessageHandler messageHandler = new MessageHandler(triggers, new DispatcherAdapter());
    messageHandler.setCurrentEventButton(new Button());
    return messageHandler;
  }

  @DataProvider
  public static Object[][] triggersToCall() {
    return new Object[][] {
        {new LoseTrigger(mock(LossEvent.class)), new MessageBuilder()
            .withAuthor(Author.SERVER)
            .withHeader(Header.LOSE)
            .withStatus(Status.OK)
            .build()},
        {new ConnectionEndTrigger(mock(OpponentWithdrawEvent.class)), new MessageBuilder()
            .withAuthor(Author.SERVER)
            .withHeader(Header.CONNECTION)
            .withStatus(Status.END)
            .build()},
        {new HitShotTrigger(mock(HitShotEvent.class)), new MessageBuilder()
            .withAuthor(Author.SERVER)
            .withHeader(Header.HIT)
            .withStatus(Status.OK)
            .build()},
        {new MissShotTrigger(mock(MissShotEvent.class)), new MessageBuilder()
            .withAuthor(Author.SERVER)
            .withHeader(Header.MISS)
            .withStatus(Status.OK)
            .build()},
        {new OpponentConnectedTrigger(mock(OpponentConnectedEvent.class)), new MessageBuilder()
            .withAuthor(Author.SERVER)
            .withHeader(Header.OPPONENT_CONNECTED)
            .withStatus(Status.OK)
            .build()},
        {new ShipDestroyedTrigger(mock(ShipDestroyedEvent.class)), new MessageBuilder()
            .withAuthor(Author.SERVER)
            .withHeader(Header.SHIP_DESTROYED)
            .withStatus(Status.OK)
            .build()},
        {new TurnTrigger(mock(TurnChangeEvent.class)), new MessageBuilder()
            .withAuthor(Author.SERVER)
            .withHeader(Header.YOUR_TURN)
            .withStatus(Status.OK)
            .build()},
        {new WinTrigger(mock(WinEvent.class)), new MessageBuilder()
            .withAuthor(Author.SERVER)
            .withHeader(Header.WIN)
            .withStatus(Status.OK)
            .build()}
    };
  }

  @Test(dataProvider = "triggersToCall")
  public void RightTRiggerCall(EventTrigger eventTrigger, Message message) {
    DispatcherAdapter dispatcherAdapter = mock(DispatcherAdapter.class);
    Button eventButton = mock(Button.class);
    Map<Header, EventTrigger> triggers = new EnumMap<>(Header.class);

    MessageHandler messageHandler = new MessageHandler(triggers, dispatcherAdapter);


    triggers.put(message.getHeader(), eventTrigger);
    when(dispatcherAdapter.getEventButton()).thenReturn(eventButton);
    messageHandler.handle(message);

    verify(dispatcherAdapter).fireEvent(eventTrigger.getEvent());
  }
}