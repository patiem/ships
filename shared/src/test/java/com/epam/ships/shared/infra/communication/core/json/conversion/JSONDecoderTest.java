package com.epam.ships.shared.infra.communication.core.json.conversion;

import com.epam.ships.shared.fleet.Fleet;
import com.epam.ships.shared.fleet.Mast;
import com.epam.ships.shared.fleet.Ship;
import com.epam.ships.shared.infra.communication.api.Message;
import com.epam.ships.shared.infra.communication.api.conversion.Decoder;
import com.epam.ships.shared.infra.communication.api.conversion.Encoder;
import com.epam.ships.shared.infra.communication.api.message.Author;
import com.epam.ships.shared.infra.communication.api.message.Header;
import com.epam.ships.shared.infra.communication.core.message.MessageBuilder;
import com.google.gson.JsonElement;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class JSONDecoderTest {

  @Test
  public void itDecodesAsExpected() {
    Message sent = new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.PLACEMENT)
        .withFleet(Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"),
            Mast.ofIndex("2"), Mast.ofIndex("1")))))
        .build();
    Encoder<JsonElement> encoder = new JsonEncoder();
    JsonElement encoded = encoder.encode(sent);
    Decoder<JsonElement> decoder = new JsonDecoder();
    Message received = decoder.decode(encoded);
    assertEquals(sent, received);
  }
}
