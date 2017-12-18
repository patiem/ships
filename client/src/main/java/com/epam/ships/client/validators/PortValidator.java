package com.epam.ships.client.validators;

/**
 * @author Magda
 * @since 2017-12-16
 */

public class PortValidator {

    public int asInt (String providedPort) throws IllegalArgumentException{
        int port = Integer.valueOf(providedPort);
        checkRange(port);
        return port;
    }

    private void checkRange(int port) throws IllegalArgumentException {
        if(port <= 1024 || port > 0xFFFF) {
            throw new IllegalArgumentException("port value is out of range");
        }
    }
}
