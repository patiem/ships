package pl.korotkevics.ships.shared.transcript;

import java.util.List;

public interface Dao {

  void addGame(Game game);

  List<Game> getGames();

  void update(Game game);
}
