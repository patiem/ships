package pl.korotkevics.ships.client.reporting;

enum ReportingOption {
  FILE;
  
  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}
