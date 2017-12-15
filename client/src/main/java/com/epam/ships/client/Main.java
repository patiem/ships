package com.epam.ships.client;

import com.epam.ships.client.gui.GuiMain;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.application.Application;

public class Main {
    private static final Target logger = new SharedLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Client is up and running.");
        new Thread(() -> Application.launch(GuiMain.class)).start();
    }
}

