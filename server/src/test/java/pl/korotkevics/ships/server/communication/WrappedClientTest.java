package pl.korotkevics.ships.server.communication;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

@Test
public class WrappedClientTest {

  @Test(groups = "integration")
  public void wrappedClientShouldBeAbleToSendMessageToServer() throws IOException {
    //given
    int port = 9090;
    AppServer appServer = new AppServer(port);
    sendClient(port);
    dumpClient(port);
    //when
    Scanner scanner = new Scanner(appServer.connectClients().get(0).getInputStream());
    String receivedMessage = scanner.nextLine();
    String expectedMessage = "{\"header\":\"DEFAULT\",\"status\":\"OK\",\"author\":\"SERVER\",\"statement\":\"\",\"fleet\":{\"fleet\":{}}}";
    //then
    Assert.assertEquals(receivedMessage, expectedMessage);
  }

  private void dumpClient(int port) {
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
        wrappedClient.send(new MessageBuilder().withAuthor(Author.SERVER).build());
      } catch (final InterruptedException | IOException e) {
        e.printStackTrace();
      }
    }).start();
  }


}