package com.epam.ships.client.client;

import com.epam.ships.client.JavaFxInitializer;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import javafx.scene.control.Button;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertTrue;

public class MessageHandlerTest {

  @BeforeClass
  public void setup() throws InterruptedException {
    if(!JavaFxInitializer.isLaunched()) {
      JavaFxInitializer.initialize();
    } else if (!JavaFxInitializer.isEnable()){
      throw new SkipException("skipping test because of lack of support for graphics");
    }
  }

  @Test
  public void shouldCallConnectionEndTrigger() {
    //given
    Map<Header, EventTrigger> triggers = new EnumMap<>(Header.class);
    ConnectionEndTrigger connectionEndTrigger = mock(ConnectionEndTrigger.class);
    triggers.put(Header.CONNECTION, connectionEndTrigger);
    Message message = new MessageBuilder()
        .withHeader(Header.CONNECTION)
        .withStatus(Status.END)
        .withStatement("End of a message")
        .build();

    MessageHandler messageHandler = new MessageHandler(triggers);
    messageHandler.setCurrentEventButton(new Button());

    //when
    messageHandler.handle(message);

    //then
    verify(connectionEndTrigger, times(1)).fire(messageHandler.getCurrentEventButton(), message.getStatement());
  }

  @Test
  public void shouldReturnTrueAfterTriggerOpponentWithdrawEven() {
    //given
    Map<Header, EventTrigger> triggers = new EnumMap<>(Header.class);
    ConnectionEndTrigger connectionEndTrigger = mock(ConnectionEndTrigger.class);
    triggers.put(Header.CONNECTION, connectionEndTrigger);
    Message message = new MessageBuilder()
        .withHeader(Header.CONNECTION)
        .withStatus(Status.END)
        .withStatement("End of a message")
        .build();

    MessageHandler messageHandler = new MessageHandler(triggers);
    messageHandler.setCurrentEventButton(new Button());

    //when
    messageHandler.handle(message);

    assertTrue(messageHandler.isEndConnectionTriggered());
  }
}