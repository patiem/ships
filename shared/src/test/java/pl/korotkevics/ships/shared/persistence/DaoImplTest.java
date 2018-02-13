package pl.korotkevics.ships.shared.persistence;

import org.testng.annotations.Test;

@Test
public class DaoImplTest {
    private DaoImpl dao = new DaoImpl();

    @Test
    public void shouldSaveTranscript() {
        SingleTranscriptRecord singleTranscriptRecord = new SingleTranscriptRecord("example");
        dao.add(singleTranscriptRecord);
    }


}