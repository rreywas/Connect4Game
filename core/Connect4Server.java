package core;
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.Connect4TextConsole;

/**
 * Creates Server to be used for Connect4. Allows clients
 * to connect and creates a game on a new thread. Server
 * holds all game logic as well.
 * 
 * @author Brendan Brunelle
 * @version (version) 4/9/20
 */

public class Connect4Server extends Application implements Connect4Constants{
	public int p1 = 1;
	public int p2 = 2;
	Stage window;
	Scene scene1,scene2;
	Button button1, button2;
	Pane layout, layout2, layout3;
	public final static int rows = 6;
	public final static int columns = 7;
	public static char[][] textBoard = new char[rows][columns];
	public boolean guiGame;
	public boolean winner;
	private int sessionNum = 1;
	private ServerSocket server;
	
	/**Creates the stage for the server info to be displayed.
	 * @param firstStage Stage for Server info
	 */
	@Override
	public void start(Stage firstStage) throws Exception {
		
		TextArea taLog = new TextArea();
		scene2 = new Scene(new ScrollPane(taLog), 450, 200);
		window = firstStage;
		window.setTitle("Connect4 Server");
		window.setScene(scene2);
		window.show();
		
		new Thread( () -> {
			try {
			server = new ServerSocket(8000);
			Platform.runLater(() -> taLog.appendText(new Date() + ": Server started at socket 8000\n"));
			
			while(true) {
				Platform.runLater(() -> taLog.appendText(new Date() + ": Wait for players to join game session " + sessionNum + '\n'));
				
				//Creating socket for Player 1 to join
				Socket player1 = server.accept();
				
				Platform.runLater(() -> {
					taLog.appendText(new Date() + ": Player 1 has joined the session " + sessionNum + '\n');
					taLog.appendText("Player 1's IP address" + player1.getInetAddress().getHostAddress() + '\n');
				});

				new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER1);
				
				//Creating socket for Player 2 to join
				Socket player2 = server.accept();
				
				Platform.runLater(() ->{
					taLog.appendText(new Date() + ": Player 2 joined the game session " + sessionNum + '\n');
					taLog.appendText("Player 2's IP address "+ player2.getInetAddress().getHostAddress() + '\n');
				});

				new DataOutputStream(player2.getOutputStream()).writeInt(PLAYER2);
				
				Platform.runLater(() -> taLog.appendText(new Date() + ": Start a thread for session " + sessionNum++ + '\n'));
				
				//New thread that's created for every game session
				new Thread(new HandleSession(player1,player2)).start();
			}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}).start();

	}
	
	/**Handles a game session between two players */
	public class HandleSession implements Runnable{
		private Socket player1;
		private Socket player2;
		
		public final int rows = 6;
		public final int columns = 7;
		char[][] textBoard = new char[rows][columns];
		
	    private DataInputStream fromPlayer1;
	    private DataOutputStream toPlayer1;
	    private DataInputStream fromPlayer2;
	    private DataOutputStream toPlayer2;
	    
	    public boolean continueToPlay = true;
	    
	    //Constructor for HandleSession
	    public HandleSession(Socket player1, Socket player2) {
	        this.player1 = player1;
	        this.player2 = player2;
	    
	        //Initialize textBoard
			for(int i=0;i<textBoard.length;i++) {
				for(int j=0; j<textBoard[i].length;j++) {
					textBoard[i][j] = ' ';
				}
			}
	    }
		
	    /**Notifies players if either player has won or 
	     * if the game continues. */
		@Override
		public void run() {
			try {
				DataInputStream fromPlayer1 = new DataInputStream(player1.getInputStream());
				DataOutputStream toPlayer1 = new DataOutputStream(player1.getOutputStream());
				DataInputStream fromPlayer2 = new DataInputStream(player2.getInputStream());
				DataOutputStream toPlayer2 = new DataOutputStream(player2.getOutputStream());
				
				toPlayer1.writeInt(1);
				
				while(true) {
					int column = fromPlayer1.readInt();
					if(dropPiece(textBoard,column,'X')) {
						if(checkWin(textBoard)) {
							toPlayer1.writeInt(PLAYER1_WON);
							toPlayer2.writeInt(PLAYER1_WON);
							sendMove(toPlayer2,column);
							break;
						}else {
							toPlayer2.writeInt(CONTINUE);
							sendMove(toPlayer2,column);
						}
					}else {
						toPlayer1.writeInt(ERROR);
					}
					
					column = fromPlayer2.readInt();
					
					if(dropPiece(textBoard,column,'O')) {
						if(checkWin(textBoard)) {
							toPlayer2.writeInt(PLAYER2_WON);
							toPlayer1.writeInt(PLAYER2_WON);
							sendMove(toPlayer1,column);
							break;
						}else if(draw()) {
							toPlayer1.writeInt(DRAW);
							toPlayer2.writeInt(DRAW);
							sendMove(toPlayer1,column);
							break;
						}else {
							toPlayer1.writeInt(CONTINUE);
							sendMove(toPlayer1,column);
						}
					}else {
						toPlayer2.writeInt(ERROR);
					}
				}			
			}catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		
		/**Sends move to player */
		public void sendMove(DataOutputStream out, int column) throws IOException{
			out.writeInt(column);			
		}
		
		/**Determines if there was a draw */
	    private boolean draw() {
	        for (int i = 0; i < textBoard.length; i++)
	          for (int j = 0; j < textBoard[i].length; j++)
	            if (textBoard[i][j] == ' ')
	              return false;
	        
	        // All cells are filled
	        return true;
	      }
		
	    /**Drops piece for Server board */
		public boolean dropPiece(char[][]board, int column, char player) {		
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
		
		/**Checks if there was a win in the game
		 * 
		 * @param board Game board
		 * @return boolean Sends boolean for win (true if win is found).
		 */
		public boolean checkWin(char[][]board) {
	
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
	
	/**Main method to launch the GUI server info */
	public static void main(String[] args) {
		launch(args);
	}
}