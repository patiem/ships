package pl.korotkevics.ships.shared.transcript;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GameService {

  private Repository<GameEntity> repository;

  GameService() {
    this.repository = new GameRepository();
//    addToDB();
  }

  private void addToDB() {
    GameEntity game = new GameEntity();
    game.setPlayerOne("pl_1");
    game.setPlayerTwo("xl_1");
    game.setDate(new Date());

    GameEntity game2 = new GameEntity();
    game2.setPlayerOne("pl_2");
    game2.setPlayerTwo("xl_2");
    game2.setDate(new Date());

    GameEntity game3 = new GameEntity();
    game3.setPlayerOne("pl_3");
    game3.setPlayerTwo("xl_3");
    game3.setDate(new Date());

    repository.update(game);
    repository.update(game2);
    repository.update(game3);
  }

  String[] getGameStrings() {
    return repository.getAll().stream().map(GameEntity::toString).toArray(String[]::new);
  }

  List<String> getTranscript(int indexOfGame) {
    List<Transcript> transcripts = repository.getById(indexOfGame + 1).getTranscripts();
    return transcripts.stream().map(Transcript::toString).collect(Collectors.toList());
  }

  private List<FleetEntity> getFleets(int indexOfGame) {
    return repository.getById(indexOfGame + 1).getFleets();
  }

  List<String> getShips(int indexOfGame) {
    List<String> shipsSt = new ArrayList<>();
    getFleets(indexOfGame).stream().forEach(f-> {
      shipsSt.add("Fleet for " + f.getPlayerName());
      f.getFleet().forEach(s -> shipsSt.add(s.getShip()));
    });
    return shipsSt;
  }
}
