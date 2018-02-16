package pl.korotkevics.ships.shared.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class SingleTranscriptDao implements Dao<SingleTranscriptRecord> {
    private SessionFactory factory;

    //TODO:
    public SingleTranscriptDao() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    //TODO:
    @Override
    public void add(SingleTranscriptRecord transcript) {
        try (Session session = factory.getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            session.save(transcript);
            tx.commit();
        }
    }

    //TODO:
    @Override
    public void delete(SingleTranscriptRecord t) {
    }

    //TODO:
    @Override
    public List<SingleTranscriptRecord> readAll() {
        return null;
    }

    @Override
    public SingleTranscriptRecord findById(long id) {
        SingleTranscriptRecord toFind;
        try (Session session = factory.getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            toFind = session.find(SingleTranscriptRecord.class, id);
            tx.commit();
        }
        return toFind;
    }

    //TODO:
    @Override
    public void cleanTable(String tableName) {
    }

}
