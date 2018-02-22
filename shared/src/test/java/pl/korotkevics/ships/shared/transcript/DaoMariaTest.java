package pl.korotkevics.ships.shared.transcript;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class DaoMariaTest {

  private Repository<GameEntity> repository;

  @BeforeMethod
  public void setUp() {
    repository = new GameRepository();
  }

  @Test(priority=1)
  public void addsGameEntityToDBandRetrieveWholeList() {

    //given

    GameEntity game = new GameEntity();
    game.setPlayerOne("lol_1");
    game.setPlayerTwo("bbb_1");
    game.setDate(new Date());

    GameEntity game2 = new GameEntity();
    game2.setPlayerOne("lol_2");
    game2.setPlayerTwo("bbb_2");
    game2.setDate(new Date());

    //when
    repository.add(game);
    repository.add(game2);
    List<GameEntity> games = repository.getAll();

    //then
    assertEquals(2, games.size());
    assertEquals("lol_1", games.get(0).getPlayerOne());
    assertEquals(game.getId(), 1);
    assertEquals(game2.getId(), 2);
  }

  @Test(priority=2)
  public void addsTranscriptEntityToDBandRetrieveWholeListWithPersists() {

    //given

    GameEntity game3 = new GameEntity();
    game3.setPlayerOne("lol_3");
    game3.setPlayerTwo("bbb_3");
    game3.setDate(new Date());

    //when
    Transcript transcript = new Transcript();
    transcript.setHeader("HEAD_3");

    Transcript transcript2 = new Transcript();
    transcript2.setHeader("TAIL_3");

    game3.addTranscript(transcript);
    game3.addTranscript(transcript2);

    repository.update(game3);

    List<GameEntity> games = repository.getAll();
    GameEntity gameEx = games.get(2);

    //then
    assertEquals(3, games.size());
    assertEquals(2, gameEx.getTranscripts().size());
    assertEquals("HEAD_3", gameEx.getTranscripts().get(0).getHeader());
  }

  @Test(priority=3)
  public void addsTranscriptEntityToDB() {

    //given

    GameEntity game4 = new GameEntity();
    game4.setPlayerOne("lol_4");
    game4.setPlayerTwo("bbb_4");
    game4.setDate(new Date());
    //when
    Transcript transcript = new Transcript();
    transcript.setHeader("HEAD_4");

    Transcript transcript2 = new Transcript();
    transcript2.setHeader("TAIL_4");

    game4.addTranscript(transcript);
    game4.addTranscript(transcript2);

    repository.add(game4);

    List<GameEntity> games = repository.getAll();
    GameEntity gameEx = games.get(3);

    //then
    assertEquals(4, games.size());
    assertEquals(2, gameEx.getTranscripts().size());
    assertEquals("HEAD_4", gameEx.getTranscripts().get(0).getHeader());
  }

  @Test(priority=4)
  public void addsTranscriptWhenGameIsAlreadyInDb() {

    //given

    GameEntity game5 = new GameEntity();
    game5.setPlayerOne("lol_5");
    game5.setPlayerTwo("bbb_5");
    game5.setDate(new Date());
    //when
    Transcript transcript = new Transcript();
    transcript.setHeader("HEAD_5");

    Transcript transcript2 = new Transcript();
    transcript2.setHeader("TAIL_5");

    game5.addTranscript(transcript);
    game5.addTranscript(transcript2);

    repository.add(game5);

    Transcript transcript3 = new Transcript();
    transcript3.setHeader("NEW");
    game5.addTranscript(transcript3);


    repository.add(game5);

    List<GameEntity> games = repository.getAll();
    GameEntity gameEx = games.get(4);

    //then
    assertEquals(5, games.size());
    assertEquals(3, gameEx.getTranscripts().size());
    assertEquals("NEW", gameEx.getTranscripts().get(2).getHeader());
  }

  @Test(priority=4)
  public void getsGameById() {

    //given

    //when
    GameEntity gameFromDB = repository.getById(1);
    GameEntity gameFromDB4 = repository.getById(5);

    //then
    assertEquals(gameFromDB.getPlayerOne(), "lol_1");
    assertEquals(gameFromDB4.getPlayerOne(), "lol_5");
  }
}