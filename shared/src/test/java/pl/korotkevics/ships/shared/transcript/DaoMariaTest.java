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
    game.setPlayerOne("lol_1");
    game.setPlayerTwo("bbb_1");
    game.setDate(new Date());

    game2 = new Game();
    game2.setPlayerOne("lol_2");
    game2.setPlayerTwo("bbb_2");
    game2.setDate(new Date());

    dao = new DaoMaria();

  }

  @Test(priority=1)
  public void addsGameEntityToDBandRetrieveWholeList() {

    //given

    //when
    dao.addGame(game);
    dao.addGame(game2);
    List<Game> games = dao.getGames();

    //then
    assertEquals(2, games.size());
    assertEquals("lol_1", games.get(0).getPlayerOne());
  }

  @Test(priority=2)
  public void addsTrascriptEntityToDBandRetrieveWholeListWithPersists() {

    //given

    //when
    Transcript transcript = new Transcript();
    transcript.setHeader("HEAD_3");

    Transcript transcript2 = new Transcript();
    transcript2.setHeader("TAIL_3");

    game.addTranscript(transcript);
    game.addTranscript(transcript2);

    dao.update(game);

    Game gameEx = dao.getGames().get(2);

    //then
    assertEquals(2, gameEx.getTranscripts().size());
    assertEquals("HEAD_3", gameEx.getTranscripts().get(0).getHeader());
  }

  @Test(priority=3)
  public void addsTrascriptEntityToDBandRetrieveWholeList() {

    //given

    Game game3 = new Game();
    game3.setPlayerOne("lol_3");
    game3.setPlayerTwo("bbb_3");
    game3.setDate(new Date());
    //when
    Transcript transcript = new Transcript();
    transcript.setHeader("HEAD_4");

    Transcript transcript2 = new Transcript();
    transcript2.setHeader("TAIL_4");

    game3.addTranscript(transcript);
    game3.addTranscript(transcript2);

    dao.addGame(game3);

    Game gameEx = dao.getGames().get(3);

    //then
    assertEquals(2, gameEx.getTranscripts().size());
    assertEquals("HEAD_4", gameEx.getTranscripts().get(0).getHeader());
  }

  @Test(priority=4)
  public void addsTrascriptWhenGameIsAlreadyInDbEntityToDBandRetrieveWholeList() {

    //given

    Game game4 = new Game();
    game4.setPlayerOne("lol_4");
    game4.setPlayerTwo("bbb_4");
    game4.setDate(new Date());
    //when
    Transcript transcript = new Transcript();
    transcript.setHeader("HEAD_5");

    Transcript transcript2 = new Transcript();
    transcript2.setHeader("TAIL_5");

    game4.addTranscript(transcript);
    game4.addTranscript(transcript2);

    dao.addGame(game4);

    Transcript transcript3 = new Transcript();
    transcript3.setHeader("NEW");
    game4.addTranscript(transcript3);


    dao.addGame(game4);

    Game gameEx = dao.getGames().get(4);

    //then
    assertEquals(3, gameEx.getTranscripts().size());
    assertEquals("NEW", gameEx.getTranscripts().get(2).getHeader());
  }
}