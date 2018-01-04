package com.epam.ships.shared.infra.communication.core.json.io;

import com.epam.ships.shared.infra.communication.api.Message;
import com.epam.ships.shared.infra.communication.api.io.Receiver;
import com.epam.ships.shared.infra.communication.api.message.Status;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.testng.Assert.assertEquals;

@Test(groups = {"integration"})
public class JsonReceiverTest {
  
  public void itShouldReturnMessageWithEndStatus() {
    //given
    InputStream inputStream = new InputStream() {
      @Override
      public int read() throws IOException {
        return -1;
      }
    };
    Receiver receiver = new JsonReceiver(inputStream);
    //when
    Message message = receiver.receive();
    //then
    assertEquals(message.getStatus(), Status.END);
  }
  
}