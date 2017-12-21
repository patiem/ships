package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.fleet.Mast;
import com.epam.ships.fleet.Ship;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.api.conversion.Encoder;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.google.gson.JsonElement;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class JSONDecoderTest {

    @Test
    public void itDecodesMessageWithFleetAsExpected() {
        List<Ship> ships = Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"),
                Mast.ofIndex("2"), Mast.ofIndex("1")));
        Message sent = new MessageBuilder()
                                      .withAuthor("Client")
                                      .withHeader("Placement")
                                      .withStatus("OK")
                                      .withFleet(Fleet.ofShips(ships))
                                      .build();
        Encoder<JsonElement> encoder = new JSONEncoder();
        JsonElement encoded = encoder.encode(sent);
        Decoder<JsonElement> decoder = new JSONDecoder();
        Message received = decoder.decode(encoded);
        assertEquals(sent, received);
    }
    
    @Test
    public void itDecodesMessageWithoutFleetAsExpected() {
        final Message sent = new MessageBuilder()
                                        .withAuthor("server")
                                        .withHeader("opponentConnected")
                                        .withStatus("OK")
                                        .withStatement("true")
                                        .build();
        Encoder<JsonElement> encoder = new JSONEncoder();
        JsonElement encoded = encoder.encode(sent);
        Decoder<JsonElement> decoder = new JSONDecoder();
        Message received = decoder.decode(encoded);
        assertEquals(sent, received);
    }
}
