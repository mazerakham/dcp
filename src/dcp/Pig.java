package dcp;

import java.util.Random;
import java.util.Scanner;

import ox.Log;
import ox.util.Utils;

public class Pig {

  private static final long PAUSE_AFTER_INPUT_MILLIS = 250L;
  
  private String player1;
  private String player2;
  
  private int currentPlayer = 1;
  private int currentHeldScore = 0;
  private int player1Score = 0;
  private int player2Score = 0;
  
  private final Scanner scanner = new Scanner(System.in);
  
  public void play() {
    getPlayerNames();
    while (true) {
      doTurn();
      boolean playerWon = checkForWin();
      if (playerWon) {
        Log.debug("Congratulations " + getCurrentPlayerName() + ", you won!");
        break;
      } else {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        continue;
      }
    }
  }
  
  private boolean checkForWin() {
    return getCurrentPlayerScore() >= 100;
  }
  
  private void doTurn() {
    currentHeldScore = 0;
    Log.debug("");
    Log.debug(getCurrentPlayerName() + "'s turn.");
    Log.debug("The current scores are: " + player1 + ": " + player1Score + ", " + player2 + ": " + player2Score);
    while (true) {
      Log.debug("Current held score for " + getCurrentPlayerName() + ": " + currentHeldScore);
      Log.debug("Hold (H) or Roll (R)?");
      String choice = getNextString();
      if (choice.toLowerCase().equals("h")) {
        setCurrentPlayerScore(getCurrentPlayerScore() + currentHeldScore);
        Log.debug("Score held.  Your new score is " + getCurrentPlayerScore());
        break;
      } else {
        int roll = rollDie();
        Log.debug(getCurrentPlayerName() + " rolled a " + roll + ".");
        if (roll == 1) {
          Log.debug("Sorry " + getCurrentPlayerName() + ", that means you lost your held points and it goes to the next player.");
          break;
        } else {
          currentHeldScore += roll;
        }
      }
      Utils.sleep(PAUSE_AFTER_INPUT_MILLIS);
    }
  }
  
  private int getCurrentPlayerScore() {
    return currentPlayer == 1 ? player1Score : player2Score;
  }
  
  private void setCurrentPlayerScore(int newScore) {
    if (currentPlayer == 1) {
      player1Score = newScore;
    } else {
      player2Score = newScore;
    }
  }
  
  private int rollDie() {
    Random r = new Random();
    return (r.nextInt() % 6 + 6) % 6 + 1;
  }
  
  private void getPlayerNames() { 
    Log.debug("Player 1 enter your name:");
    player1 = getNextString();
    Log.debug("Player 2 enter your name:");
    player2 = getNextString();
  }
  
  private String getNextString() {
    return scanner.next();
  }
  
  private String getCurrentPlayerName() {
    return (currentPlayer == 1) ? player1 : player2;
  }
  
  public static void main(String... args) {
    new Pig().play();
  }
}
