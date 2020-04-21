package core;
import java.util.Random;
import ui.Connect4TextConsole;
/**
 * Plays a token in a random column from 1 to 7 to simulate an AI. 
 * This class also will display if the computer won or not.
 * 
 * @author Brendan Brunelle
 * @version (version) 3/4/2020
 */

public class Connect4ComputerPlayer {
	public static int computerChoice = 0;
	static Random rand = new Random();
	
	/**
	 * Generates a random number from 1 to 7
	 */
	public static void generateRandom() {
		computerChoice = rand.nextInt(7)+1;
	}
	
	/**
	 * Attempts to play a computer move and will use recursion to 
	 * play another column if one can't be played.
	 */
	public static void playComputerMove() {
		generateRandom();
		if(!Connect4.dropPiece(Connect4TextConsole.board, computerChoice-1, 'O')) {
			playComputerMove();
		}else {
			if(Connect4.checkWin(Connect4TextConsole.board)) {
				System.out.println("The computer has won.");
				Connect4TextConsole.printBoard(Connect4TextConsole.board);
				Connect4TextConsole.win = true;
			}else {
				Connect4TextConsole.player = 'X';
			}
		}
	}

}

