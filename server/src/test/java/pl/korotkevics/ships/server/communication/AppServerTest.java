package pl.korotkevics.ships.server.communication;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test(groups = {"integration"})
public class AppServerTest {

  public void shouldConnectTwoClients() throws IOException, InterruptedException {
    //given
    AppServer appServer = new AppServer(8989);

    //when
    connectClient();
    connectClient();
    List<Socket> pairOfClients = appServer.connectClients();

    //then
    assertEquals(pairOfClients.size(), 2);
  }

  private void connectClient() throws IOException, InterruptedException {
    new Thread(() -> {
      try {
        Thread.sleep(10);
        new Socket("127.0.0.1", 8989);
      } catch (final InterruptedException | IOException e) {
        e.printStackTrace();
      }
    }).start();
  }
}