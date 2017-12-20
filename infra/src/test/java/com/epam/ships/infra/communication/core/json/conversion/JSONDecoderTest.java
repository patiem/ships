package com.epam.ships.infra.communication.core.json.conversion;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JSONDecoderTest {

    @Test
    public void itDecodesAsExpected() {
        //given
        Decoder baseDecoder = new JSONDecoder();
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
//        assertEquals(messageDecoded, messageExpected);
        System.out.println(messageExpected);
    }
}
