package com.epam.ships.client.validators;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PortValidatorTest {


    @DataProvider(name = "properValues")
    public static Object[][] properData() {
        return new Object[][] {
                {"1", 1},
                {"1234", 1234},
                {"8189", 8189},
                {"65535", 65535}
        };
    }

    @DataProvider(name = "invalidValues")
    public static Object[][] invalidData() {
        return new Object[][] {
                {"-1"},
                {"0"},
                {""},
                {"66666"},
                {"ala"}
        };
    }


    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "invalidValues")
    public void shouldThrowIllegalArgumentException(String testPort) {
        //given
        PortValidator portValidator = new PortValidator();

        //when
        portValidator.asInt(testPort);
        //then
        //throws
    }

    @Test(dataProvider = "properValues")
    public void shouldGetProperValueOfPort(String testPort, int properReturnedPort) {
        //given
        PortValidator portValidator = new PortValidator();

        //when
        int port = portValidator.asInt(testPort);

        //then
        assertEquals(port, properReturnedPort);
    }

}