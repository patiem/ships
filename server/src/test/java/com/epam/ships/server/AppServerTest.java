package com.epam.ships.server;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.Socket;

import static org.testng.Assert.assertEquals;

@Test
public class AppServerTest {

    public void shouldConnectTwoClients() throws IOException, InterruptedException {
        //given
        AppServer appServer = new AppServer(8989);

        //when
        connectClient();
        connectClient();
        appServer.connectClients();

        //then
        assertEquals(appServer.getClientSockets().size(), 2);
    }

    private void connectClient() throws IOException, InterruptedException {
        new Thread(() -> {


            try {
                Thread.sleep(10);
                new Socket("127.0.0.1", 8989);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }).start();
    }

}