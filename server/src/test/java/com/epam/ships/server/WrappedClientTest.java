package com.epam.ships.server;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@Test
public class WrappedClientTest {


    public void wrappedClientShouldBeAbleToSendMessageToServer() throws IOException{
        //given
        int port = 9090;
        AppServer appServer = new AppServer(port);
        sendClient(port);
        dumpClient(port);
        //when
        appServer.connectClients();
        Scanner scanner = new Scanner(appServer.getClientSockets().get(0).getInputStream());
        String receivedMessage = scanner.nextLine();
        String expectedMessage = "{\"header\":\"\",\"status\":\"\",\"author\":\"testMessage\",\"statement\":\"\"}";
        //then
        Assert.assertEquals(receivedMessage, expectedMessage);
    }

    public void shouldBeAbleToReceiveMessageFromServer() throws IOException {
        //given
        int port = 10990;
        this.appServer(port);
        WrappedClient receiver = new WrappedClient(new Socket("127.0.0.1", port));
        dumpClient(port);
        Message expectedMessage = new MessageBuilder().withAuthor("testMessage").build();
        Message receivedMessage = receiver.receive();
        //then
        Assert.assertEquals(receivedMessage, expectedMessage);

    }

    private void appServer(int port) {
        new Thread(() -> {
            try {
                AppServer appServer = new AppServer(port);
                System.out.println("Client can connect");
                appServer.connectClients();

                OutputStream outputStream = appServer.getClientSockets().get(0).getOutputStream();
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
                printWriter.println("{\"header\":\"\",\"status\":\"\",\"author\":\"testMessage\",\"statement\":\"\"}");
                while (true){
                    System.out.println("alive");
                }
            } catch (IOException /*| InterruptedException*/ e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void dumpClient(int port){
        new Thread(() -> {
            try {
                Thread.sleep(20);
                new WrappedClient(new Socket("127.0.0.1", port));
            } catch (final InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendClient(int testPort) {
        new Thread(() -> {
            try {
                Thread.sleep(10);
                WrappedClient wrappedClient = new WrappedClient(new Socket("127.0.0.1", testPort));
                Thread.sleep(10);
                wrappedClient.send(new MessageBuilder().withAuthor("testMessage").build());
            } catch (final InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


}