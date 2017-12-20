package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Encoder;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.google.gson.JsonElement;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JSONEncoderTest {
    @Test
    public void itEncodesAsExpected() {
        //given
        Encoder<JsonElement> baseEncoder = new JSONEncoder();
        Message message = new MessageBuilder()
                .withAuthor("Sandor")
                .withHeader("Connection")
                .withStatus("OK")
                .withStatement("The first real test in this project:-)")
                .build();
        //when
        JsonElement jsonElement = baseEncoder.encode(message);
        //then
        assertEquals(jsonElement.toString(), "{\"header\":\"Connection\",\"status\":\"OK\",\"author\":\"Sandor\"," +
                                                     "\"statement\":\"The first real test in this project:-)\"}");
    }
}
