package pl.korotkevics.ships.shared.transcript;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

  @Id
  @GenericGenerator(name = "increment", strategy = "increment")
  @GeneratedValue(generator = "increment")
  @Column
  private int id;

  @Column(name = "one", nullable = false)
  private String playerOne;

  @Column(name = "two", nullable = false)
  private String playerTwo;

  @Column(name = "date", nullable = false)
  private Date date;

  @OneToMany(cascade=CascadeType.PERSIST)
  @JoinTable(
      name="transcripts",
      joinColumns = @JoinColumn( name="game_id"),
      inverseJoinColumns = @JoinColumn( name="trans_id")
  )
  List<Transcript> transcripts = new ArrayList<>();

  public Game() {
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

  public void addTranscript(Transcript transcript) {
    transcripts.add(transcript);
    transcript.setGame(this);
  }
}
