package pl.korotkevics.ships.shared.transcript;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

  private static final SessionFactory sessionFactory;

  static
  {
    try {
      sessionFactory = new Configuration().configure().buildSessionFactory();
    } catch (HibernateException ex) {
      System.out.println("Creation of session factory failed!");
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static void shutdown() {
    getSessionFactory().close();
  }
}