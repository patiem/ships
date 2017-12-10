package com.epam.ships.communication.core.json;

import com.epam.ships.communication.api.json.JSONDecoder;
import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.core.message.MessageBuilder;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BaseDecoderTest {

    @Test
    public void itDecodesAsExpected() {
        //given
        JSONDecoder baseDecoder = new BaseDecoder();
        JSONObject jsonObject = new JSONObject("{\"author\":\"Sandor\",\"statement\":\"The first real test in this project:-)\",\"header\":\"Connection\",\"status\":\"OK\"}");
        Message messageExpected = new MessageBuilder()
                .withAuthor("Sandor")
                .withHeader("Connection")
                .withStatus("OK")
                .withStatement("The first real test in this project:-)")
                .build();
        //when
        Message messageDecoded = baseDecoder.decode(jsonObject);
        //then
        assertEquals(messageDecoded, messageExpected);
    }
}
