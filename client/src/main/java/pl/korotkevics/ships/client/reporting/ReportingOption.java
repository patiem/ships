package pl.korotkevics.ships.client.reporting;

enum ReportingOption {
  FILE, SOCKET, LOGGER;
  
  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}
