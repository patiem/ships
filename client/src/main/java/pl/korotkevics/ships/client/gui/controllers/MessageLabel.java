package pl.korotkevics.ships.client.gui.controllers;

import javafx.scene.control.Label;

class MessageLabel extends Label {
  
  public void setMessage(String message) {
    this.setText(message);
    this.store(message);
  }
  
  private void store(final String message) {
    System.out.println("stored: " + message);
  }
  
  public MessageLabel() {
  }
}
