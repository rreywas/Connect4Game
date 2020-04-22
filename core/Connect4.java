package core;
import java.io.IOException;
import java.util.Scanner;

import jdk.internal.org.objectweb.asm.Handle;
import ui.Connect4TextConsole;
import java.net.*;
/**
 *Connect 4 Game
 * Author: Sawyer Breitenbucher
 * Date: 2-25-2020
 *
 */
public class Connect4 {

    public static final String PLAYER_1_SYMBOL = "O";
    public static final String PLAYER_2_SYMBOL = "X";
    String[][] boardGame;
    private int rowPosition;
    public boolean gameOver;
    public boolean gameWon;
    private boolean isComputer;

    public void setIsComputer(boolean isComputer){
        this.isComputer = isComputer;
    }


    public Connect4(String[][] boardGame){
        this.boardGame = boardGame;
    }

    public String[][] getBoardGame(){
        return boardGame;
    }

    /**
     * Checks if the most recent move done by the player allows them to win the game
     * @param row input of row value
     * @param col input of column value
     * @param symbol input of what piece the player is playing with
     * @return returns true or false depending on if the player has won the game
     */
    public boolean checkWinningMove(int row, int col, String symbol) {
        int leftCounter = 0;
        int rightCounter = 0;
        final int MAXIMUM_COL = 6;

        //check horizontal win condition
        int increment = col;
        while (boardGame[row][increment].equals(symbol) && increment < MAXIMUM_COL){
            rightCounter++;
            increment++;
        }
        increment = col;
        while (boardGame[row][increment].equals(symbol) && increment < MAXIMUM_COL){
            leftCounter++;
            if (increment == 0){
                break;
            }
            increment--;
        }
           if (leftCounter + rightCounter > 4){
               gameWon = true;
               return true;
           }

           //Check vertical win condition
           leftCounter = 0;
           rightCounter = 0;

           increment = row;
           while (boardGame[increment][col].equals(symbol)){
               rightCounter++;
               if (increment == 5){
                   break;
               }
               increment++;

        }
           increment = row;
            while (boardGame[increment ][col].equals(symbol)){
                leftCounter++;
                if (increment == 0){
                    break;
                }
                increment--;
            }
        if (leftCounter + rightCounter > 4){
            gameWon = true;
            return true;
        }

        //Check the diagonal win condition (right diagonal)
        leftCounter = 0;
        rightCounter = 0;
        increment = col;
        int incrementRow = row;
        while (boardGame[incrementRow][increment].equals(symbol)){
            rightCounter++;
            if (increment == 0 || incrementRow == 0){
                break;
            }
            increment--;
            incrementRow--;
        }
        increment = col;
        incrementRow = row;
        while (boardGame[incrementRow][increment].equals(symbol)){
            leftCounter++;
            if (increment == 6 || incrementRow == 5){
                break;
            }
            incrementRow++;
            increment++;
        }
        if (leftCounter + rightCounter > 4){
            gameWon = true;
            return true;
        }

        leftCounter = 0;
        rightCounter = 0;
        increment = col;
        incrementRow = row;

        while (boardGame[incrementRow][increment].equals(symbol)){
            rightCounter++;
            if (increment == 0){
                break;
            }
            if (incrementRow == 5){
                break;
            }
            increment--;
            incrementRow++;
        }
        increment = col;
        incrementRow = row;
        while (boardGame[incrementRow][increment].equals(symbol)){
            leftCounter++;
            if (incrementRow == 0){
                break;
            }
            if (increment == 6){
                break;
            }
            incrementRow--;
            increment++;
        }
        if (leftCounter + rightCounter > 4){
            gameWon = true;
            return true;
        }

        return false;

        }

    /**
     * returns true if game is won; false if game is still active
     * @return boolean
     */
    public boolean isGameWon(){
        return gameWon;
    }



    /**
     * Getter for rowPosition
     * @return int
     */
    public int getRowPosition(){
        return rowPosition;
    }

    //find next slot that is open

    /**
     * Checks to see what the next open space is
     * @param col (input column value)
     * @return integer of the next open row value
     * @throws IllegalArgumentException (should not reach this)
     */
    public int nextOpenSpace(int col) {

            for (int i = 5; i >= 0; i--) {
                if (boardGame[i][col].equals(PLAYER_1_SYMBOL) || boardGame[i][col].equals(PLAYER_2_SYMBOL)) {
                    rowPosition = i + 1;
                    return i + 1;
                } else if (i == 0 && (!boardGame[i][col].equals(PLAYER_1_SYMBOL) || !boardGame[i][col].equals(PLAYER_2_SYMBOL))) {
                    rowPosition = 0;
                    return 0;
                }
            }
        throw new IllegalArgumentException();
    }



    /**
     * Allows the player to play a move whilst checking if it is valid
     * @param col column value
     * @param symbol what piece the player is playing with
     */
    public void playMove(int col, String symbol) throws IOException {

        boardGame[nextOpenSpace(col)][col] = symbol;
    }


}
