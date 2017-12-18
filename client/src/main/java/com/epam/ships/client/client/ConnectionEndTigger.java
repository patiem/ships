package com.epam.ships.client.client;

import javafx.scene.control.Button;

public class ConnectionEndTigger implements EventTrigger {
    @Override
    public void fire(Button button, String messageStatement) {
        //button.fireEvent();
        System.out.println("WIN because 2 client leave");

    }
}
