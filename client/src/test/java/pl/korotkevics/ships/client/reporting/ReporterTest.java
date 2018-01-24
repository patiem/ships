package pl.korotkevics.ships.client.reporting;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

@Test(groups = "integration")
public class ReporterTest {
  
  private final String REPORTING_TO_FILE_CONFIG = "reportingToFile";
  
  private final String REPORTING_TO_SOCKET_CONFIG = "reportingToSocket";
  
  private final String REPORTING_TO_LOGGER_CONFIG = "reportingToLogger";
  
  private final String REPORTING_TO_WINDOW_CONFIG = "reportingToWindow";
  
  private final int PORT = 9919;
  
  private final String LOG_FILE = "../target/test-reporting.log";
  
  public void shouldRecognizeActiveTargetAsFile() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_FILE_CONFIG);
    //when - then
    assertEquals(reporter.getCurrentDestination(), ReportingOption.FILE.toString());
  }
  
  public void shouldRecognizeConfiguredFileName() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_FILE_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationFileName(), LOG_FILE);
  }
  
  public void shouldReportToFile() throws IOException {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_FILE_CONFIG);
    //when
    reporter.report("Just a casual message");
    //then
    assertEquals(Files.readFile(new File(LOG_FILE)), "Just a casual message" + System
                                                                                   .lineSeparator
                                                                                        ());
  }
  
  public void shouldAppendLinesWhenReportingToFile() throws IOException {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_FILE_CONFIG);
    this.makeSureLogFileExistsAndIsEmpty();
    //when
    reporter.report("First message");
    reporter.report("Second message");
    //then
    assertEquals(Files.readFile(new File(LOG_FILE)), "First message" + System.lineSeparator() +
                                                         "Second message" + System.lineSeparator());
  }
  
  public void shouldNotReportToFile() throws IOException {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_WINDOW_CONFIG);
    this.makeSureLogFileExistsAndIsEmpty();
    //when
    reporter.report("A message which should not be reported int" + "o a file since it is not the " +
                        "" + "" + "" + "" + "" + "" + "" + "active destination.");
    //then
    assertEquals(Files.readFile(new File(LOG_FILE)), StringUtils.EMPTY);
  }
  
  public void shouldRecognizeActiveTargetAsSocket() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_SOCKET_CONFIG);
    //when - then
    assertEquals(reporter.getCurrentDestination(), ReportingOption.SOCKET.toString());
  }
  
  public void shouldRecognizeHost() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_SOCKET_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationHost(), "127.0.0.1");
  }
  
  public void shouldRecognizePort() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_SOCKET_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationPort(), PORT);
  }
  
  public void shouldReportToSocket() throws Exception {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_SOCKET_CONFIG);
    Future<String> future = Executors.newSingleThreadExecutor().submit(() -> {
      try (ServerSocket serverSocket = new ServerSocket(PORT); Socket clientSocket = serverSocket
                                                                                         .accept
                                                                                              ()) {
        Scanner scanner = new Scanner(clientSocket.getInputStream(), "UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(scanner.nextLine());
        //then
        return stringBuilder.toString();
      } catch (final IOException e) {
        throw new RuntimeException();
      }
    });
    
    //when
    reporter.report("Hey Sandor");
    String receivedString = future.get(5, TimeUnit.SECONDS);// timeout is optional here
    
    //then
    assertEquals(receivedString, "Hey Sandor");
  }
  
  public void shouldRecognizeActiveTargetAsLogger() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_LOGGER_CONFIG);
    //when - then
    assertEquals(reporter.getCurrentDestination(), ReportingOption.LOGGER.toString());
  }
  
  public void shouldRecognizeLoggerName() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_LOGGER_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationLoggerName(), "Snarky Reporter");
  }
  
  public void shouldRecognizeDestinationWindowTitle() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_WINDOW_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationWindowTitle(), "Snarky Reporter");
  }
  
  public void shouldRecognizeDestinationWindowHeight() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_WINDOW_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationWindowHeight(), 600);
  }
  
  public void shouldRecognizeDestinationWindowWidth() {
    //given
    Reporter reporter = new Reporter(REPORTING_TO_WINDOW_CONFIG);
    //when - then
    assertEquals(reporter.getDestinationWindowWidth(), 400);
  }
  
  private void makeSureLogFileExistsAndIsEmpty() throws IOException {
    FileOutputStream writer = new FileOutputStream(LOG_FILE);
    writer.write(("").getBytes());
    writer.close();
  }
}