package pl.korotkevics.ships.shared.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;

public class DaoImpl<T> implements Dao<T> {
    private SessionFactory factory;

    public DaoImpl() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public void add(T transcript) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(transcript);
            tx.commit();
        }
    }

    @Override
    public void delete(T t) {
    }

    @Override
    public List<T> readAll() {
        return null;
    }

    @Override
    public T find(T t) {
        return null;
    }

}
