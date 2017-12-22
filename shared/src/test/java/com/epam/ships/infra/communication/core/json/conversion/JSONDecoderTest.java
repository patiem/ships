package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.fleet.Mast;
import com.epam.ships.fleet.Ship;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.api.conversion.Encoder;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.google.gson.JsonElement;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
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
        Encoder<JsonElement> encoder = new JSONEncoder();
        JsonElement encoded = encoder.encode(sent);
        Decoder<JsonElement> decoder = new JSONDecoder();
        Message received = decoder.decode(encoded);
        assertEquals(sent, received);
    }
}
