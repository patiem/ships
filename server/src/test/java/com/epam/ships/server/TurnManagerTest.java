package com.epam.ships.server;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;

@Test
public class TurnManagerTest {

  public void shouldReturnFirstPlayerAsACurrentPlayerBeforeSwitchWasCalled() throws IOException {
    //given
    WrappedClient firstPlayer = mock(WrappedClient.class);
    WrappedClient secondPlayer = mock(WrappedClient.class);
    TurnManager turnManager = new TurnManager(firstPlayer, secondPlayer);
    //when
    WrappedClient resultClient = turnManager.getCurrentPlayer();
    //then
    Assert.assertEquals(resultClient, firstPlayer);
  }

  public void shouldReturnSecondPlayerAsOtherPlayerBeforeSwitchWasCalled() throws IOException {
    //given
    WrappedClient firstPlayer = mock(WrappedClient.class);
    WrappedClient secondPlayer = mock(WrappedClient.class);
    TurnManager turnManager = new TurnManager(firstPlayer, secondPlayer);
    //when
    WrappedClient resultClient = turnManager.getOtherPlayer();
    //then
    Assert.assertEquals(resultClient, secondPlayer);
  }

  public void shouldBeSecondClientAsCurrentClientAfterFirstInvoceSwitchMethod() {
    //given
    WrappedClient firstPlayer = mock(WrappedClient.class);
    WrappedClient secondPlayer = mock(WrappedClient.class);
    TurnManager turnManager = new TurnManager(firstPlayer, secondPlayer);
    //when
    turnManager.switchPlayer();
    WrappedClient resultClient = turnManager.getCurrentPlayer();

    //then
    Assert.assertEquals(resultClient, secondPlayer);
  }

  public void shouldReturnTrueIfBeforeSwitchingThePlayers() {
    //given
    WrappedClient firstPlayer = mock(WrappedClient.class);
    WrappedClient secondPlayer = mock(WrappedClient.class);
    TurnManager turnManager = new TurnManager(firstPlayer, secondPlayer);
    //when
    Assert.assertTrue(turnManager.isCurrentPlayerFirstPlayer());
  }

  public void shouldReturnFalseAfterSwitchingThePlayers() {
    //given
    WrappedClient firstPlayer = mock(WrappedClient.class);
    WrappedClient secondPlayer = mock(WrappedClient.class);
    TurnManager turnManager = new TurnManager(firstPlayer, secondPlayer);
    //when
    turnManager.switchPlayer();
    //then
    Assert.assertFalse(turnManager.isCurrentPlayerFirstPlayer());
  }

}