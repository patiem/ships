package pl.korotkevics.ships.shared.transcript;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {

  private Dao dao;
  //private List<Game> games;

  public Controller() {
    this.dao = new DaoMaria();
    addToDB();
  }

  private void addToDB() {
    Game game = new Game();
    game.setPlayerOne("pl_1");
    game.setPlayerTwo("xl_1");
    game.setDate(new Date());

    Game game2 = new Game();
    game2.setPlayerOne("pl_2");
    game2.setPlayerTwo("xl_2");
    game2.setDate(new Date());

    Game game3 = new Game();
    game3.setPlayerOne("pl_3");
    game3.setPlayerTwo("xl_3");
    game3.setDate(new Date());

    dao.update(game);
    dao.update(game2);
    dao.update(game3);
  }

  public String[] getGameStrings() {
    List<Game> games = dao.getGames();
    System.out.println(games.size());
    return dao.getGames().stream().map(Game::toString).toArray(String[]::new);
  }
}
