package com.epam.ships.fleet;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.conversion.Decoder;
import com.epam.ships.infra.communication.api.conversion.Encoder;
import com.epam.ships.infra.communication.core.json.conversion.JSONDecoder;
import com.epam.ships.infra.communication.core.json.conversion.JSONEncoder;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.google.gson.JsonElement;
import org.testng.annotations.Test;

public class FleetTest {
    /*@Test
    public void itShouldCreateNonEmptyFleetWhenGivenWithShips() {
        //given
        Ship firstShip = Ship.ofMasts(Mast.ofIndex(3), Mast.ofIndex(2));
        Ship secondShip = Ship.ofMasts(Mast.ofIndex(4), Mast.ofIndex(1));
        //when
        Fleet fleet = Fleet.ofShips(firstShip, secondShip);
        //then
        assertFalse(fleet.isEmpty());
    }
    */
    @Test
    public void temp() {
        Ship firstShip = Ship.ofMasts(Mast.ofIndex("3"), Mast.ofIndex("2"), Mast.ofIndex("1"));
        Ship secondShip = Ship.ofMasts(Mast.ofIndex("4"), Mast.ofIndex("5"), Mast.ofIndex("6"));
        Fleet fleetSent = Fleet.ofShips(firstShip, secondShip);
        
        Message messageSent = new MessageBuilder().withFleet(fleetSent).build();
        Encoder<JsonElement> baseEncoder = new JSONEncoder();
        JsonElement jsonObject = baseEncoder.encode(messageSent);
        System.out.println(jsonObject);
        
        Decoder<JsonElement> baseDecoder = new JSONDecoder();
        Message messageReceived = baseDecoder.decode(jsonObject);
        
        Fleet fleet = messageReceived.getFleet();
        Mast firstHit = Mast.ofIndex("3");
        Mast secondHit = Mast.ofIndex("2");
        Mast thirdHit = Mast.ofIndex("1");
        System.out.println(fleet.handleDamage(firstHit));
        System.out.println(fleet.handleDamage(secondHit));
        System.out.println(fleet.handleDamage(thirdHit));
    }
}
