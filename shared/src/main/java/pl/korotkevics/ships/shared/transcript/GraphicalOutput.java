package pl.korotkevics.ships.shared.transcript;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GraphicalOutput extends JFrame implements ActionListener {

  private JTextArea textArea;
  private JComboBox games;
  private Container contentPane;
  private GameService service;
  private JButton button;
  private int indexOfGame;


  public static void main(String[] args) {
    new GraphicalOutput();
  }

  public GraphicalOutput() {
    service = new GameService();
    init();
  }

  private void init() {
    contentPane = getContentPane();

    setupDropDown();
    setupButton();
    setupTextArea();

    this.setSize(500, 500);
    setVisible(true);
    setLayout(null);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void setupButton() {
    button = new JButton("Show trans");
    button.setSize(200, 50);
    button.addActionListener(e -> {
      getFleet().stream().forEach(t -> textArea.append("\n" + t));
      getTranscript().stream().forEach(t -> textArea.append("\n" + t));
    });
    contentPane.add(button, BorderLayout.AFTER_LAST_LINE);

  }

  private void setupTextArea() {
    textArea = new JTextArea();
    textArea.setSize(500, 450);
    textArea.append("Transcript for game");
    textArea.setEditable(false);
    JScrollPane scroll = new JScrollPane(textArea,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setSize(500, 500);
    contentPane.add(scroll, BorderLayout.CENTER);
  }

  private void setupDropDown() {
    games = new JComboBox<>(getGamesList());
    games.setSize(400, 50);
    games.addActionListener(this);
    contentPane.add(games, BorderLayout.NORTH);
  }

  public void transcript(String message) {
    textArea.append(message);
    textArea.append("\n");
  }

  private String[] getGamesList() {
    return service.getGameStrings();
  }

  private java.util.List<String> getTranscript() {
    return service.getTranscript(indexOfGame);
  }

  private java.util.List<String> getFleet() {
    return service.getFleets(indexOfGame);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JComboBox cb = (JComboBox) e.getSource();
    indexOfGame = cb.getSelectedIndex();
    String gameName = (String) cb.getSelectedItem();
    textArea.setText("");
    textArea.append("\n" + gameName);
  }
}
