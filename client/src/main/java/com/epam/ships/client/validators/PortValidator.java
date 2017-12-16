package com.epam.ships.client.validators;

public class PortValidator {

    public int asInt (String providedPort) throws IllegalArgumentException {
        int port = convertToInt(providedPort);
        checkRange(port);
        return port;
    }

    private  int convertToInt (String providedPort) {
        int port = 0;
        try {
            port = Integer.valueOf(providedPort);
        } catch (NumberFormatException e) {
            throw e;
        }
        return port;
    }

    private void checkRange(int port) throws IllegalArgumentException {
        if(port < 0 || port > 0xFFFF) {
            throw new IllegalArgumentException("port value is out of range");
        }
    }
}
