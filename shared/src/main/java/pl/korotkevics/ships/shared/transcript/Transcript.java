package pl.korotkevics.ships.shared.transcript;

import javax.persistence.*;

@Entity
@Table(name = "transcripts")
public class Transcript {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "trans_id")
  private int id;

  @ManyToOne
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
