package com.epam.ships.communication.impl.json;

import com.epam.ships.communication.api.json.JSONEncoder;
import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.impl.message.MessageBuilder;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BaseEncoderTest {

    @Test
    public void itEncodesAsExpected() {
        //given
        JSONEncoder baseEncoder = new BaseEncoder();
        Message message = new MessageBuilder()
                .withAuthor("Sandor")
                .withHeader("Connection")
                .withStatus("OK")
                .withStatement("The first real test in this project:-)")
                .build();
        //when
        JSONObject jsonObject = baseEncoder.encode(message);
        //then
        assertEquals(jsonObject.toString(), "{\"author\":\"Sandor\",\"statement\":\"The first real test in this project:-)\",\"header\":\"Connection\",\"status\":\"OK\"}");
    }
}
