package pl.korotkevics.ships.shared.transcript;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class DaoMariaTest {

  Game game;
  private Game game2;
  private Dao dao;

  @BeforeMethod
  public void setUp() {

    game = new Game();
    game.setPlayerOne("lol");
    game.setPlayerTwo("bbb");
    game.setDate(new Date());

    game2 = new Game();
    game2.setPlayerOne("lol");
    game2.setPlayerTwo("bbb");
    game2.setDate(new Date());

    dao = new DaoMaria();
  }

  @Test
  public void addsGameEntityToDBandRetrieveWholeList() {

    //given
    //when
    dao.addGame(game);
    dao.addGame(game2);
    List<Game> games = dao.getGames();

    //then
    assertEquals(2, games.size());
    assertEquals("lol", games.get(0).getPlayerOne());
  }

  @Test
  public void addsTrascriptEntityToDBandRetrieveWholeList() {

    //given
    //when
    Transcript transcript = new Transcript();
    transcript.setHeader("HEAD");

    Transcript transcript2 = new Transcript();
    transcript2.setHeader("TAIL");
    game.addTranscript(transcript2);

    dao.update(game);

    Game game = dao.getGames().get(0);

    //then
    assertEquals(2, game.getTranscripts().size());
    assertEquals("HEAD", game.getTranscripts().get(0).getHeader());
  }
}