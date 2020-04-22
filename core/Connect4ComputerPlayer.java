package core;

import java.util.Scanner;

import static core.Connect4.PLAYER_1_SYMBOL;
import static core.Connect4.PLAYER_2_SYMBOL;


public class Connect4ComputerPlayer {

    /**
     * Generates random column to play (computers turn)
     * @param board
     * @return int
     */
    public int randColGenerator(Connect4 board){
        int randomCol = (int)(Math.random() * ((7 - 1) + 1)) + 1;
        while (randomCol > 6 ||
                randomCol < 0 ||
                board.getBoardGame()[5][randomCol].equals(PLAYER_1_SYMBOL) ||
                board.getBoardGame()[5][randomCol].equals(PLAYER_2_SYMBOL))
        {
            randomCol = (int)(Math.random() * ((7 - 1) + 1)) + 1;
        }
        return randomCol;
    }

    /**
     * Determines if player wants to vs a computer or another player
     * @param scan
     * @return returns true if playing the computer, returns false if playing another player
     */
    public boolean isPlayingComputer(Scanner scan){
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Enter ‘P’ if you want to play against another player; enter ‘C’ to play against computer.");
            String choosingString = scan.next();
            if (choosingString.equals("C")) {
                validInput = true;
                return true;
            } else if (choosingString.equals("P")) {
                validInput = true;
                return false;
            }
            else{
                System.out.println("Invalid input.");
            }
        }
        System.out.println("Should not be printing this"); //if printed, something went wrong
        return false; //should never get to this point
    }
}

