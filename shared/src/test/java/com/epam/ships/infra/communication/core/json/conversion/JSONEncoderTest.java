package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Encoder;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.google.gson.JsonElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JSONEncoderTest {
    @Test
    public void itEncodesAsExpected() {
        //given
        Encoder<JsonElement> baseEncoder = new JSONEncoder();
        Message message = new MessageBuilder()
                .withAuthor(Author.SERVER)
                .withHeader(Header.CONNECTION)
                .withStatus(Status.OK)
                .withStatement("The first real test in this project:-)")
                .build();
        //when
        JsonElement jsonElement = baseEncoder.encode(message);
        //then
        assertEquals(jsonElement.toString(), "{\"header\":\"CONNECTION\",\"status\":\"OK\",\"author\":\"SERVER\"," +
                                                     "\"statement\":\"The first real test in this project:-)\"," +
                                                     "\"fleet\":{\"fleet\":{}}}");
    }
}
