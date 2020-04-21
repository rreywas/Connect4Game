package ui;
import java.util.InputMismatchException;
import java.util.Scanner;
import core.Connect4;
import core.Connect4ComputerPlayer;

/**
 * Prints the board and asks the user to input the desired column.
 * 
 * @author Brendan Brunelle
 * @version (version) 2/23
 */
public class Connect4TextConsole {
public static char[][] board = {{' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' '},
						 {' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' '},
						 {' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' '}
};
static Scanner scan = new Scanner(System.in);
public static char player = 'X';
public static boolean win = false;
public static int choice = -1;
public static char computerChoice;
public static boolean computer = false;

/**
 * Main method to ask user for their desired column, will loop if the choice is not between 1 and 7.
 * Also calls dropPiece and checkWin within GameLogic to see if the move is playable and
 * if it's a winning move.
 * 
 * @param args Main method
 */

	public static void main(String[] args) {
		System.out.println("Begin Game. Enter 'P' if you would like to play against a player or"
				+ " 'C' to play against computer.");
		do {
		computerChoice = scan.next().charAt(0);		
		if(computerChoice == 'p' || computerChoice == 'P') {
			computer = false;
		}else if(computerChoice == 'c'|| computerChoice == 'C') {
			computer = true;
		}else {
			System.out.println("Choice must be a 'P' or 'C'.");
		}
		}while((computerChoice!='c'&&computerChoice!='C')&&(computerChoice!='p'&&computerChoice!='P'));
		
		while(win==false) {
			printBoard(board);
			do {
				if(computer == false) {
				System.out.println("Player " + player + " -your turn. Choose a column 1-7.");
				}else {
					System.out.println("It is your turn. Choose a column 1-7.");
				}
				boolean notValidChoice = true;
				do {
					try {	
						choice = scan.nextInt();
						if(1>choice||choice>7) {
							throw new InputMismatchException();
						}
						notValidChoice = false;
					}
					catch(InputMismatchException ex) {
						System.out.println("Column has to be between 1 and 7.");
						scan.nextLine();
					}
				}while(notValidChoice);
				if(1<=choice&&choice<=7) {
					if(!core.Connect4.dropPiece(board,choice-1,player)) {
						choice = -1;
					}
				}else {
					System.out.println("Column has to be between 1 and 7.");
				}
			}while(choice<1||choice>7 && win==false);

		if(core.Connect4.checkWin(board)) {
			System.out.println("Player " + player + " Won the Game");
			printBoard(board);
			win = true;
		}
		if(computer==false)
		player = switchPlayers(player);
		if(computer==true && win==false)
			core.Connect4ComputerPlayer.playComputerMove();
	}
	}
	/**
	 * Switches players after the move has been played.
	 * 
	 * @param player Current Player
	 * @return char To change to the other player
	 */
	private static char switchPlayers(char player) {
		if(player == 'X')
			return 'O';
		else
			return 'X';
	}
	/**
	 * Prints the current layout of the game board.
	 * 
	 * @param board The game board
	 */
	public static void printBoard(char[][] board) {
		for(int row=0;row<board.length;row++) {
			System.out.print("|");
			for(int col=0; col<board[row].length;col++) {
				System.out.print(board[row][col] + "|");
			}
			System.out.println();
		}
		
	}

}

