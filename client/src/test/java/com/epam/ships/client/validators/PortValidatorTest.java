package com.epam.ships.client.validators;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PortValidatorTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenTryToConvertNotANumber() {
        //given
        PortValidator portValidator = new PortValidator();
        String testObject = "ala";

        //when
        portValidator.asInt(testObject);
        //then
        //throws
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenTryToPassOutOfBoundValue() {
        //given
        PortValidator portValidator = new PortValidator();
        String testObject = "666666";

        //when
        portValidator.asInt(testObject);
        //then
        //throws
    }

    @Test
    public void shouldGetProperValueOfPort() {
        //given
        PortValidator portValidator = new PortValidator();
        String testObject = "6666";

        //when
        int port = portValidator.asInt(testObject);

        //then
        assertEquals(port, 6666);
    }

}