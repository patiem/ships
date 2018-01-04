package com.epam.ships.infra.communication.core.json.io;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.io.Sender;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.testng.Assert.assertEquals;

@Test(groups = {"integration"})
public class JsonSenderTest {
  public void itSendsProperlyEncodedMessage() {
    //given
    OutputStream outputStream = new ByteArrayOutputStream();
    Sender sender = new JsonSender(outputStream);
    Message message = new MessageBuilder()
                          .withAuthor(Author.SERVER)
                          .withHeader(Header.WIN)
                          .build();
    //when
    sender.send(message);
    //then
    assertEquals(outputStream.toString(), "{\"header\":\"WIN\",\"status\":\"OK\"," +
                                              "\"author\":\"SERVER\",\"statement\":\"\",\"fleet\":{\"fleet\":{}}}\n");
  }
}