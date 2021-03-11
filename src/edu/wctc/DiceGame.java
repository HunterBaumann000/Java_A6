package edu.wctc;

import java.util.*;
import java.util.stream.Collectors;

public class DiceGame {

    private final List<Player> players = new ArrayList<>();
    private final List<Die> dice = new ArrayList<>();
    private final int maxRolls;
    private Player currentPlayers;

    public DiceGame(int countPlayers, int countDice, int maxRolls) {
        List<Player> players = this.players;
        List<Die> dice = this.dice;
        this.maxRolls = maxRolls;

        for(int i = 0; i < countPlayers; countPlayers++) {
            countDice++;
            maxRolls++;

            players.add(new Player());
            dice.add(new Die(6));
        }

        try {
            if(countPlayers < 2) {
                throw new IllegalArgumentException();
            }

        } catch(IllegalArgumentException e){
            //System.out.println("There needs at least two players");
        }
    }

    private boolean allDiceHeld() {
        boolean isHeld = dice.stream()
                .allMatch(Die::isBeingHeld);

        if (isHeld) {
            return true;
        } else {
            return false;
        }
    }

    public boolean autoHold(int faceValue) {
        boolean autoHold = dice.stream()
                .filter(Die::isBeingHeld)
                .findFirst(faceValue)
                .isPresent();

        if (autoHold) {
            if(!allDiceHeld()){
                return true;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean currentPlayerCanRoll() {
        if (maxRolls > 0 && !(allDiceHeld())) {
            return true;
        } else {
            return false;
        }
    }

    public int getCurrentPlayerNumber() {
        return players.size();
    }

    public int getCurrentPlayerScore() {

        return players.get(this.player).getScore();
    }

    public String getDiceResults() {
        String diceResults = dice.stream()
                .map(Die::toString)
                .collect(Collectors.joining());

        return diceResults;
    }

    public String getFinalWinner(){
        String finalWinner =
                Collections.max(players, Comparator.comparingInt(Player -> getCurrentPlayerScore())).toString();

        return "The winner is player " + finalWinner;

    }

    public String getGameResults() {
        var gameResults = players.stream()
                .max(Comparator.reverseOrder())
                .forEach(Player::getPlayerNumber)
                .map(String::valueOf)
                .collect(Collectors.joining(" | "));

        return "Game Results: " + gameResults;
    }

    private boolean isHoldingDie(int faceValue) {
        var holdingDie = dice.stream()
                .filter(Die::isBeingHeld)
                .findFirst(dice.equals(faceValue))
                .isPresent();

        if(holdingDie) {
            return true;
        } else {
            return false;
        }
    }

    public boolean nextPlayer(){
        if(players.size() > players.size()){
            this.currentPlayer = currentPlayer;
            return true;
        } else {
            return false;
        }
    }

    public void playerHold(char dieNum){
        boolean playerHold = dice.stream()
                .anyMatch(players -> isHoldingDie(dieNum));

        if (playerHold){
            autoHold(dieNum);
        }
    }

    public void resetDice(){
        resetDice = dice.stream()
                .forEach(Die::resetDie);

        dice.clear();
    }

    public void resetPlayers(){
        playerReset = players.stream()
                .forEach(Player::resetPlayer);

        players.clear();
    }

    public void rollDice(){
        roll = dice.stream()
                .forEach(Die::rollDie);
        //System.Out.PrintLn(roll)
    }

    public void scoreCurrentPlayer() {

        if(currentPlayers.getRollsUsed() == 6 ||
           currentPlayers.getRollsUsed() == 5 ||
           currentPlayers.getRollsUsed() == 4 )
        {
          currentPlayers.setScore(getCurrentPlayerScore() + currentPlayers.getRollsUsed());
        }

    }

    public void startNewGame() {
        players.sort(Comparator.comparing(Player::getScore));
        players.set(0,players.get(0));
        resetPlayers();
    }
}
