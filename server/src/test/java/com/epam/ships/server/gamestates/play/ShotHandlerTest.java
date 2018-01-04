package com.epam.ships.server.gamestates.play;

import com.epam.ships.shared.fleet.Fleet;
import com.epam.ships.shared.fleet.Mast;
import com.epam.ships.shared.fleet.Ship;
import com.epam.ships.shared.infra.communication.api.Message;
import com.epam.ships.shared.infra.communication.api.message.Author;
import com.epam.ships.shared.infra.communication.api.message.Header;
import com.epam.ships.shared.infra.communication.core.message.MessageBuilder;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.TurnManager;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

@Test
public class ShotHandlerTest {

  private List<Fleet> fleets;
  private ShotHandler shotHandler;

  @Mock
  private TurnManager turnManager;
  @Mock
  private CommunicationBus communicationBus;

  @BeforeMethod
  void before(){
    initMocks(this);
    Fleet fleet = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"), Mast.ofIndex("2"), Mast.ofIndex("1"))));
    Fleet fleet2 = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("11"), Mast.ofIndex("12"), Mast.ofIndex("13"))));
    fleets = Arrays.asList(fleet,fleet2);
    shotHandler = new ShotHandler(communicationBus, turnManager, fleets);
  }

  public void shouldHandleHitWhenFirstPlayerShots() {
    //given
    //when
    Message message = shotAtIndex("11");
    boolean result = shotHandler.handle(true, message);

    //then
    assertEquals(fleets.get(1).isDefeated(),result);
    assertFalse(result);
    verify(turnManager, times(0)).switchPlayer();
  }

  public void shouldHandleHitWhenSecondPlayerShots() {
    //given
    //when
    Message message = shotAtIndex("3");
    boolean result = shotHandler.handle(false, message);

    //then
    assertEquals(fleets.get(1).isDefeated(),result);
    assertFalse(result);
    verify(turnManager, times(0)).switchPlayer();
  }

  public void shouldHandleMiss() {
    //given
    //when
    Message message = shotAtIndex("112");
    boolean result = shotHandler.handle(true, message);

    //then
    assertEquals(fleets.get(1).isDefeated(),result);
    assertFalse(result);
    verify(turnManager, times(1)).switchPlayer();
  }

  public void shouldHandleFleetDefeat() {
    //given
    ShotHandler shotHandler = new ShotHandler(communicationBus, turnManager, fleets);
    Message firstMessage = shotAtIndex("11");
    Message secondMessage = shotAtIndex("12");
    Message thirdMessage = shotAtIndex("13");

    //when
    shotHandler.handle(true, firstMessage);
    shotHandler.handle(true, secondMessage);
    boolean result = shotHandler.handle(true, thirdMessage);

    //then
    assertEquals(fleets.get(1).isDefeated(),result);
    assertTrue(result);
    verify(turnManager, times(0)).switchPlayer();
  }

  private Message shotAtIndex(String mast) {
    return new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.SHOT)
        .withStatement(mast)
        .build();
  }

}