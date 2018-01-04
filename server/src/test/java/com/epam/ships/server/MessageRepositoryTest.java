package com.epam.ships.server;

import com.epam.ships.shared.infra.communication.api.Message;
import com.epam.ships.shared.infra.communication.api.message.Author;
import com.epam.ships.shared.infra.communication.api.message.Header;
import com.epam.ships.shared.infra.communication.core.message.MessageBuilder;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class MessageRepositoryTest {


  public void shouldGetMessageByHeader() {
    //given
    Header header = Header.SHIP_DESTRUCTED;

    Message expectedMessage = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(header)
        .build();

    //when
    Message result = new MessageRepository().getMessage(header);

    //then
    assertEquals(result, expectedMessage);
  }
}