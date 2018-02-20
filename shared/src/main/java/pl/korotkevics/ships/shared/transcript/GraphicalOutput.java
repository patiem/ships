package pl.korotkevics.ships.shared.transcript;

import javax.swing.*;
import java.awt.*;


public class GraphicalOutput extends JFrame {

  private JTextArea  textArea;
  private JComboBox games;
  private Container contentPane;
  private Controller controller;


  public static void main(String[] args) {
    new GraphicalOutput();
  }

  public GraphicalOutput() {
    controller = new Controller();
    init();
  }

  private void init() {
    contentPane = getContentPane();

    setupDropDown();
    setupTextArea();

    this.setSize(500, 500);
    setVisible (true);
    setLayout(null);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  private void setupTextArea() {
    textArea = new JTextArea();
    textArea.setSize(500, 450);
    textArea.setEditable(false);
    JScrollPane scroll = new JScrollPane (textArea,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setSize(500, 500);
    contentPane.add(scroll, BorderLayout.CENTER);
  }

  private void setupDropDown() {
    games = new JComboBox<>(getGamesList());
    games.setSize(400, 50);
    contentPane.add(games, BorderLayout.NORTH);
  }

  public void transcript(String message) {
    textArea.append(message);
    textArea.append("\n");
  }

  private String[] getGamesList() {

//    return new String[] {"G1", "G2", "G3", "G4"};
    return controller.getGameStrings();
  }
}
