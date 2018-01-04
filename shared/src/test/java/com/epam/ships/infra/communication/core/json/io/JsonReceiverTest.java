package com.epam.ships.infra.communication.core.json.io;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Status;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.testng.Assert.assertEquals;


public class JsonReceiverTest {
  
  @Test(groups = {"integration"})
  public void itShouldReturnMessageWithEndStatus() {
    //given
    InputStream inputStream = new InputStream() {
      @Override
      public int read() throws IOException {
        return -1;
      }
    };
    JsonReceiver jsonReceiver = new JsonReceiver(inputStream);
    //when
    Message message = jsonReceiver.receive();
    //then
    assertEquals(message.getStatus(), Status.END);
  }
  
}