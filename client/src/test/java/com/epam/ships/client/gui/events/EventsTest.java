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
    //given
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(OpponentWithdrawEvent.OPPONENT_WITHDRAW, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    //when
    eventTestButton.fireEvent(new OpponentWithdrawEvent());
    //then
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewHitShotEvent() throws InterruptedException {
    //given
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(HitShotEvent.HIT_SHOT, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    //when
    eventTestButton.fireEvent(new HitShotEvent());
    //then
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewLooseEvent() throws InterruptedException {
    //given
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(LooseEvent.GAME_LOSE, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    //when
    eventTestButton.fireEvent(new LooseEvent());
    //then
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewMissShotEvent() throws InterruptedException {
    //given
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(MissShotEvent.MISS_SHOT, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    //when
    eventTestButton.fireEvent(new MissShotEvent());
    //then
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewOpponentConnectedEvent() throws InterruptedException {
    //given
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(OpponentConnectedEvent.OPPONENT_CONNECTED, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    //when
    eventTestButton.fireEvent(new OpponentConnectedEvent());
    //then
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewOpponentShotEvent() throws InterruptedException {
    //given
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(OpponentShotEvent.OPPONENT_SHOT, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    //when
    eventTestButton.fireEvent(new OpponentShotEvent(1));
    //then
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewTurnChangeEvent() throws InterruptedException {
    //given
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(TurnChangeEvent.TURN_EVENT, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    //when
    eventTestButton.fireEvent(new TurnChangeEvent());
    //then
    assertEquals(0.5, eventTestButton.getOpacity());
  }

  @Test
  public void shouldCreateNewWinEvent() throws InterruptedException {
    //given
    Button eventTestButton = new Button();
    eventTestButton.addEventHandler(WinEvent.GAME_WIN, opponentWithdrawEvent
        -> eventTestButton.setOpacity(0.5));

    //when
    eventTestButton.fireEvent(new WinEvent());
    //then
    assertEquals(0.5, eventTestButton.getOpacity());
  }
}