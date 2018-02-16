package pl.korotkevics.ships.shared.persistence;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

@Test
public class DaoImplTest {
    private SingleTranscriptDao dao = new SingleTranscriptDao();

    @BeforeMethod
    public void setUp() {
        //should clear the table
    }

    @Test
    public void shouldSaveTranscript() {
        SingleTranscriptRecord singleTranscriptRecord = new SingleTranscriptRecord("examplsdsdse");
        dao.add(singleTranscriptRecord);

    }

    @Test(dependsOnMethods = "shouldSaveTranscript")
    public void shouldFindTranscriptInTable() {
        //given
        SingleTranscriptRecord firstAdded = new SingleTranscriptRecord("addeasdasdd");
        dao.add(firstAdded);
        //when
        SingleTranscriptRecord actual = dao.findById(1);
        assertNotNull(actual);
    }
}