package pl.korotkevics.ships.shared.transcript;

import pl.korotkevics.ships.shared.fleet.Fleet;

import javax.persistence.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "games")
public class GameEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private int id;

  @Column(name = "one", nullable = false)
  private String playerOne;

  @Column(name = "two", nullable = false)
  private String playerTwo;

  @Column(name = "date", nullable = false)
  private Date date;

  @OneToMany(mappedBy = "game", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  List<Transcript> transcripts = new ArrayList<>();

  @OneToMany(mappedBy = "game", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  List<FleetEntity> fleets = new ArrayList<>();

  public GameEntity() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPlayerOne() {
    return playerOne;
  }

  public void setPlayerOne(String playerOne) {
    this.playerOne = playerOne;
  }

  public String getPlayerTwo() {
    return playerTwo;
  }

  public void setPlayerTwo(String playerTwo) {
    this.playerTwo = playerTwo;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public List<Transcript> getTranscripts() {
    return transcripts;
  }

  public void setTranscripts(List<Transcript> transcripts) {
    this.transcripts = transcripts;
  }

  public List<FleetEntity> getFleets() {
    return fleets;
  }

  public void setFleets(List<FleetEntity> fleets) {
    this.fleets = fleets;
  }

  public void addTranscript(Transcript transcript) {
    transcripts.add(transcript);
    transcript.setGame(this);
  }

  public void addFleet(FleetEntity fleet) {
    fleets.add(fleet);
    fleet.setGame(this);
  }

  @Override
  public String toString() {
    return String.format("%d. players: %s & %s, date: %s", id, playerOne, playerTwo, date);
  }

  public static GameEntity build(List<Socket> serverClients) {
     GameEntity newGame = new GameEntity();
     newGame.setPlayerOne(serverClients.get(0).getInetAddress().toString());
     newGame.setPlayerTwo(serverClients.get(1).getInetAddress().toString());
     newGame.setDate(new Date());
     return newGame;
  }
}
