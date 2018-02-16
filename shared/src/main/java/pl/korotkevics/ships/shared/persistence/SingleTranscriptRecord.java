package pl.korotkevics.ships.shared.persistence;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "transcript")
public class SingleTranscriptRecord implements Serializable {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Long id;
    @Column(name = "message")
    private String message;

    private SingleTranscriptRecord() {}

    public SingleTranscriptRecord(String message) {
        this.message = message;
    }
}
