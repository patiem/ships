package pl.korotkevics.ships.shared.persistence;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Test
public class SingleTranscriptRecordTest {
    private EntityManagerFactory entityManagerFactory;

    @BeforeMethod
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("utrwalacz");
    }

    @Test
    public void testSaveMessage() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        SingleTranscriptRecord singleTranscriptRecord = new SingleTranscriptRecord("wiadomosc");
        entityManager.persist(singleTranscriptRecord);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}