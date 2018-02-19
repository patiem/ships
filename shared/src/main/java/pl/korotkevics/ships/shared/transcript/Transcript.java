package pl.korotkevics.ships.shared.transcript;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "transcripts")
public class Transcript {

  @Id
  @GenericGenerator(name = "increment", strategy = "increment")
  @GeneratedValue(generator = "increment")
  @Column(name = "trans_id")
  private int id;

  @Column(name = "game_id", nullable = false)
  private Game game;


  @Column(name = "header", nullable = false)
  private String header;


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

  public void setGame(Game game) {
    this.game = game;
  }

  public Game getGame() {
    return game;
  }
}
