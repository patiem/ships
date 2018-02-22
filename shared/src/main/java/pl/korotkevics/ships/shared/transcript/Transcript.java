package pl.korotkevics.ships.shared.transcript;

import pl.korotkevics.ships.shared.infra.communication.api.Message;

import javax.persistence.*;

@Entity
@Table(name = "transcripts")
public class Transcript {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "trans_id")
  private int id;

  @ManyToOne
  private GameEntity game;

  @Column(name = "player", nullable = false)
  private String playerName;

  @Column(name = "header", nullable = false)
  private String header;

  @Column(name = "body")
  private String body;


  public Transcript() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public void setGame(GameEntity game) {
    this.game = game;
  }

  public GameEntity getGame() {
    return game;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public String toString() {
    return String.format("%d. player: %s: %s - %s", getId(), getPlayerName(), getHeader(), getBody());
  }

  public static Transcript build(Message response, String name) {
    Transcript trans = new Transcript();
    trans.setHeader(response.getHeader().toString());
    trans.setBody(response.getStatement());
    trans.setPlayerName(name);
    return trans;
  }


}
