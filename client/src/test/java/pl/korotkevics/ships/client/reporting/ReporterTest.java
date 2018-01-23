package pl.korotkevics.ships.client.reporting;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "integration")
public class ReporterTest {
  public void shouldRecognizeActiveTargetAsFile() {
    //given
    Reporter reporter = new Reporter("reportingToFile");
    //when - then
    assertEquals(reporter.getCurrentDestination(), ReportingOption.FILE.toString());
  }
  
  public void shouldRecognizeConfiguredFileName() {
    //given
    Reporter reporter = new Reporter("reportingToFile");
    //when - then
    assertEquals(reporter.getDestinationFileName(), "reporting.log");
  }
}