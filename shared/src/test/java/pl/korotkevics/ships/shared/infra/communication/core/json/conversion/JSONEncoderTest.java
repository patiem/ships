package pl.korotkevics.ships.shared.infra.communication.core.json.conversion;

import com.google.gson.JsonElement;
import org.testng.annotations.Test;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.conversion.Encoder;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;

import static org.testng.Assert.assertEquals;

public class JSONEncoderTest {
  @Test
  public void itEncodesAsExpected() {
    //given
    Encoder<JsonElement> baseEncoder = new JsonEncoder();
    Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.CONNECTION)
        .withStatus(Status.OK)
        .withStatement("The first real test in this project:-)")
        .build();
    //when
    JsonElement jsonElement = baseEncoder.encode(message);
    //then
    assertEquals(jsonElement.toString(), "{\"header\":\"CONNECTION\",\"status\":\"OK\",\"author\":\"SERVER\"," +
        "\"statement\":\"The first real test in this project:-)\"," +
        "\"fleet\":{\"fleet\":{}}}");
  }
}
