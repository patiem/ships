package pl.korotkevics.ships.shared.transcript;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DaoMaria implements Dao {

  private static Session session = HibernateUtil.getSessionFactory().openSession();


  @Override
  public void addGame(Game game) {
    session.beginTransaction();
    session.saveOrUpdate(game);
    session.getTransaction().commit();
  }

  @Override
  public List<Game> getGames() {
    session.beginTransaction();
    Query query = session.createQuery("from " + Game.class.getName());
    List<Game> games = query.list();
    session.getTransaction().commit();
    return games;
  }

  @Override
  public void update(Game game) {
    session.beginTransaction();
    session.persist(game);
    session.getTransaction().commit();

  }
}
