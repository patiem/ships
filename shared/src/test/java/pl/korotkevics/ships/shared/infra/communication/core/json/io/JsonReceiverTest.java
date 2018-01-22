package pl.korotkevics.ships.shared.infra.communication.core.json.io;

import org.testng.annotations.Test;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.io.Receiver;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;

import java.io.IOException;
import java.io.InputStream;

import static org.testng.Assert.assertEquals;

@Test
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