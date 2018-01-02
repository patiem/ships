package com.epam.ships.client.client;

import com.epam.ships.client.JavaFxInitializer;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import javafx.scene.control.Button;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertTrue;

public class MessageHandlerTest {

  @BeforeClass
  public void setUp() throws InterruptedException {
    if(!JavaFxInitializer.isLaunched()) {
      JavaFxInitializer.initialize();
    }
  }

  @BeforeMethod
  public void skipTestIfThereIsNoGraphicsSupport() {
    if (!JavaFxInitializer.isEnable()){
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
    verify(connectionEndTrigger, times(1)).fire(messageHandler.getCurrentEventButton(), message.getStatement());
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
    MessageHandler messageHandler = new MessageHandler(triggers);
    messageHandler.setCurrentEventButton(new Button());
    return messageHandler;
  }
}