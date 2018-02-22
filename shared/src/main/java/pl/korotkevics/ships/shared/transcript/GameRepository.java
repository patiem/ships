package pl.korotkevics.ships.shared.transcript;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class GameRepository implements Repository<GameEntity> {


  public GameRepository() {
  }

  @Override
  public void add(GameEntity game) {
    Session session = getSession();
    session.beginTransaction();
    session.saveOrUpdate(game);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public GameEntity getById(int index) {
    Session session = getSession();

    session.beginTransaction();
    GameEntity game =  session.get(GameEntity.class, index);
    game.getFleets().stream().forEach(a -> {});
    session.getTransaction().commit();
    session.close();
    return game;
  }

  @Override
  public List<GameEntity> getAll() {
    Session session = getSession();
    Query query = session.createQuery("from " + GameEntity.class.getName());
    List<GameEntity> games = query.list();
    session.close();
    return games;
  }

  @Override
  public void update(GameEntity game) {
    Session session = getSession();
    session.beginTransaction();
    session.persist(game);
    session.getTransaction().commit();
    session.close();
  }

  private Session getSession() {
    return HibernateUtil.getSessionFactory().openSession();
  }

  private void close() {
    HibernateUtil.getSessionFactory().close();
  }
}
