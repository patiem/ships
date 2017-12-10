package com.epam.ships.communication.api;


import java.io.IOException;

public interface Receiver {
    Message receive() throws IOException;
}
