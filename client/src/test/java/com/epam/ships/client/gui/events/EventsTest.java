package com.epam.ships.client.gui.events;

import com.epam.ships.client.JavaFxInitializer;
import javafx.scene.control.Button;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EventsTest {

  @BeforeClass
  public void setup() throws InterruptedException {
    if(!JavaFxInitializer.launched) {
      JavaFxInitializer.initialize();
    }
  }

  @Test
  public void shouldCreateNewOpponentWithdrawEvent() throws InterruptedException {
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(OpponentWithdrawEvent.OPPONENT_WITHDRAW, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    eventTestButton.fireEvent(new OpponentWithdrawEvent());
    //when
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewHitShotEvent() throws InterruptedException {
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(HitShotEvent.HIT_SHOT, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    eventTestButton.fireEvent(new HitShotEvent());
    //when
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewLooseEvent() throws InterruptedException {
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(LooseEvent.GAME_LOSE, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    eventTestButton.fireEvent(new LooseEvent());
    //when
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewMissShotEvent() throws InterruptedException {
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(MissShotEvent.MISS_SHOT, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    eventTestButton.fireEvent(new MissShotEvent());
    //when
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewOpponentConnectedEvent() throws InterruptedException {
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(OpponentConnectedEvent.OPPONENT_CONNECTED, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    eventTestButton.fireEvent(new OpponentConnectedEvent());
    //when
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewOpponentShotEvent() throws InterruptedException {
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(OpponentShotEvent.OPPONENT_SHOT, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    eventTestButton.fireEvent(new OpponentShotEvent(1));
    //when
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewTurnChangeEvent() throws InterruptedException {
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(TurnChangeEvent.TURN_EVENT, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    eventTestButton.fireEvent(new TurnChangeEvent());
    //when
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewWinEvent() throws InterruptedException {
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(WinEvent.GAME_WIN, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    eventTestButton.fireEvent(new WinEvent());
    //when
    assertEquals(0.5, eventTestButton.getOpacity());
  }
}