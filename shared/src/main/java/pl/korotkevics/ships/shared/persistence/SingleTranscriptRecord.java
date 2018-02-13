package pl.korotkevics.ships.shared.persistence;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "transcript")
public class SingleTranscriptRecord implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "message")
    private String message;

    private SingleTranscriptRecord() {
    }

    public SingleTranscriptRecord(String message) {
        this.message = message;
    }
}
