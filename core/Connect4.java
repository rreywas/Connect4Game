package core;
import ui.Connect4TextConsole;

/**
 * Attempts to drop a players game piece into the board
 * and checks every way that a player can win.
 * 
 * @author Brendan Brunelle
 * @version (version) 2/23/20
 */
public class Connect4 {

	
/**
 * Attempts to drop a piece(player) into the current board in the desired column.
 * 
 * @param board The game board
 * @param column The inputed column
 * @param player The current player and also the piece that will be dropped.
 * @return boolean to tell if the piece was able to be dropped.
 */
public static boolean dropPiece(char[][]board, int column, char player) {
	
	if(board[0][column]!= ' ') {
		System.out.println("Column is full");
		return false;
	}
	
	for(int row=board.length-1;row>=0;row--) {
		if(board[row][column]==' ') {
			board[row][column] = player;
			return true;
		}
	}
	return false;
}

/**
 * Checks if there is a win condition in the parameter board.
 * 
 * @param board The game board
 * @return boolean Tells if there was a winning move.
 */
public static boolean checkWin(char[][]board) {
	
	
	//Horizontal check
	for(int row=0; row<board.length;row++) {
		for(int col=0;col<board[row].length-3;col++) {
			if(board[row][col]!= ' ' && board[row][col]==board[row][col+1] && 
					board[row][col]==board[row][col+2] && board[row][col]==board[row][col+3]) {
				return true;
			}
		}
	}
	
	//Vertical check
	for(int col=0; col<board[0].length;col++) {
		for(int row=0;row<board.length-3;row++) {
			if(board[row][col]!= ' ' && board[row][col]==board[row+1][col] && 
					board[row][col]==board[row+2][col] && board[row][col]==board[row+3][col]) {
				return true;
			}
		}
	}

	//Diagonal from top left to bottom right
	for(int row=0; row<board.length-3;row++) {
		for(int col=0;col<board[0].length-3;col++) {
			if(board[row][col]!= ' ' && board[row][col]==board[row+1][col+1] && 
					board[row][col]==board[row+2][col+2] && board[row][col]==board[row+3][col+3]) {
				return true;
			}
		}
	}
	
	//Diagonal from bottom left to top right
	for(int row=3; row<board.length;row++) {
		for(int col=0;col<board[0].length-3;col++) {
			if(board[row][col]!= ' ' && board[row][col]==board[row-1][col+1] && 
					board[row][col]==board[row-2][col+2] && board[row][col]==board[row-3][col+3]) {
				return true;
			}
		}
	}
	return false;
}
}
