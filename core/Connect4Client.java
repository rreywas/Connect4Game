package core;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Scanner;

import core.Connect4;
import core.Connect4ComputerPlayer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;

/**
 * Creates client to be used for Connect4. Connects to server and 
 * displays the game as either GUI or Text depending on user
 * input.
 * 
 * @author Brendan Brunelle
 * @version (version) 4/9/20
 */

public class Connect4Client extends Application implements Connect4Constants{

	public boolean myTurn;
	public static char myToken = ' ';
	public static char enemyToken = ' ';
	public static int selectedColumn;
	public static Scanner scan = new Scanner(System.in);
	public static int choice;
	
	public DataInputStream fromServer;
	public DataOutputStream toServer;
	
	public boolean continuePlay = true;
	public boolean wait = true;
	public boolean win = false;
	public static boolean guiGame;
	public String host = "localhost";
	
	public final int columns = 7;
	public final int rows = 6;
	static char[][] textBoard = {{' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' '},
			 {' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' '},
			 {' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' '}};
	public Shape[][] connect4Shapes = new Shape[rows][columns];
	Shape gameBoard;
	
	Pane firstLayout, layout;
	Label label,newLabel,firstLabel;
	Button button1, button2, buttonC1,buttonC2,buttonC3,buttonC4,buttonC5,buttonC6,buttonC7;
	Stage window;
	Scene scene1, scene2;
	
	
	/**
	 * Creates the stage for the initial GUI message then possibly the game 
	 * if that option is picked.
	 * 
	 * @param connect4 Stage for client
	 */
	@Override
	public void start(Stage connect4) throws Exception {
		window = connect4;
		window.setTitle("Connect4 Client");

		button1 = new Button("GUI");
		button2 = new Button("Text Console");
		buttonC1 = new Button("1");
		buttonC2 = new Button("2");
		buttonC3 = new Button("3");
		buttonC4 = new Button("4");
		buttonC5 = new Button("5");
		buttonC6 = new Button("6");
		buttonC7 = new Button("7");
	
		
		button1.setOnAction(e -> {
			guiGame = true;
			window.setScene(scene2);
			connectToServer();
		});
	
		button2.setOnAction(e -> {
			guiGame = false;
			connectToServer();
			window.close();
		});
		
		
		
		buttonC1.setOnAction(e -> {
			if(myTurn) {
				if(dropPiece(textBoard,0,myToken)) {
					changeCircles(connect4Shapes);
					myTurn = false;
					selectedColumn = 0;
					newLabel.setText("Waiting for other player to move");
					wait = false;
				}
			}
		});
		
		buttonC2.setOnAction(e -> {
			if(myTurn) {
				if(dropPiece(textBoard,1,myToken)) {
					changeCircles(connect4Shapes);
					myTurn = false;
					selectedColumn = 1;
					newLabel.setText("Waiting for other player to move");
					wait = false;
				}
			}
		});
		
		buttonC3.setOnAction(e -> {
			if(myTurn) {
				if(dropPiece(textBoard,2,myToken)) {
					changeCircles(connect4Shapes);
					myTurn = false;
					selectedColumn = 2;
					newLabel.setText("Waiting for other player to move");
					wait = false;
				}
			}
		});
		
		buttonC4.setOnAction(e -> {
			if(myTurn) {
				if(dropPiece(textBoard,3,myToken)) {
					changeCircles(connect4Shapes);
					myTurn = false;
					selectedColumn = 3;
					newLabel.setText("Waiting for other player to move");
					wait = false;
				}
			}
		});
		
		buttonC5.setOnAction(e -> {
			if(myTurn) {
				if(dropPiece(textBoard,4,myToken)) {
					changeCircles(connect4Shapes);
					myTurn = false;
					selectedColumn = 4;
					newLabel.setText("Waiting for other player to move");
					wait = false;
				}
			}
		});
		
		buttonC6.setOnAction(e -> {
			if(myTurn) {
				if(dropPiece(textBoard,5,myToken)) {
					changeCircles(connect4Shapes);
					myTurn = false;
					selectedColumn = 5;
					newLabel.setText("Waiting for other player to move");
					wait = false;
				}
			}
		});
		
		buttonC7.setOnAction(e -> {
			if(myTurn) {
				if(dropPiece(textBoard,6,myToken)) {
					changeCircles(connect4Shapes);
					myTurn = false;
					selectedColumn = 6;
					newLabel.setText("Waiting for other player to move");
					wait = false;
				}
			}
		});
		
		//Sets up first 
		firstLabel = new Label("Would you like to use GUI or Text Console for Connect 4?");
		
		firstLayout = new Pane();
		firstLabel.setLayoutX(55);
		button1.setMinSize(50, 50);
		button2.setMinSize(50, 50);
		button1.setLayoutX(95);
		button1.setLayoutY(65);
		button2.setLayoutX(220);
		button2.setLayoutY(65);
		firstLayout.getChildren().add(firstLabel);
		firstLayout.getChildren().addAll(button1,button2);
		
		scene1 = new Scene(firstLayout,400,200);
		window.setScene(scene1);
		window.show();
		
		//Making GUI game board
		layout = new Pane();
		label = new Label(" ");
		newLabel = new Label(" ");
		gameBoard = gameBoard();
		layout.getChildren().add(gameBoard);
		circles(connect4Shapes);
		label.setScaleX(1.5);
		label.setScaleY(1.5);
		label.setLayoutX(40);
		label.setLayoutY(490);
		newLabel.setScaleX(1.5);
		newLabel.setScaleY(1.5);
		newLabel.setLayoutX(40);
		newLabel.setLayoutY(520);
		buttonC1.setLayoutX(60-25);
		buttonC1.setLayoutY(450);
		buttonC2.setLayoutX(60*2-20);
		buttonC2.setLayoutY(450);
		buttonC3.setLayoutX(60*3-15);
		buttonC3.setLayoutY(450);
		buttonC4.setLayoutX(60*4-10);
		buttonC4.setLayoutY(450);
		buttonC5.setLayoutX(60*5-6);
		buttonC5.setLayoutY(450);
		buttonC6.setLayoutX(60*6);
		buttonC6.setLayoutY(450);
		buttonC7.setLayoutX(60*7+5);
		buttonC7.setLayoutY(450);
		layout.getChildren().add(label);
		layout.getChildren().add(newLabel);
		layout.getChildren().addAll(buttonC1,buttonC2,buttonC3,buttonC4,buttonC5,buttonC6,buttonC7);
		
		scene2 = new Scene(layout,475,560);
	}
	
	
	 /** Method to connect to the created server. */
	 private void connectToServer() {
		    try {
		      Socket socket = new Socket(host, 8000);
		      fromServer = new DataInputStream(socket.getInputStream());
		      toServer = new DataOutputStream(socket.getOutputStream());
		    }
		    catch (Exception ex) {
		      ex.printStackTrace();
		    }
		    //Controlling the game on a separate thread
		    new Thread(() -> {
		      try {    	  
		       
		        int player = fromServer.readInt();
		  
		        //Player 1 or 2?
		        if (player == PLAYER1) {
		          myToken = 'X';
		          enemyToken = 'O';
		          Platform.runLater(() -> {
		        	if(guiGame == false && myToken == 'X') {
		        		System.out.println("Player 1 with X token");
		        		System.out.println("Waiting for player 2 to join");
		        	}else {
		            label.setText("Player 1 with RED Token");
		            newLabel.setText("Waiting for player 2 to join");
		        	}
		          });
		  
		          // Receive startup notification from the server
		          fromServer.readInt(); //Whatever read is ignored
		  
		          // The other player joined
		          if(guiGame==true)
		          Platform.runLater(() -> newLabel.setText("Player 2 has joined. I start first"));
		          else
		          Platform.runLater(() -> System.out.println("Player 2 has joined. I start first"));
		  
		          myTurn = true;
		        }
		        else if (player == PLAYER2) {
		          myToken = 'O';
		          enemyToken = 'X';
		          Platform.runLater(() -> {
			        	if(guiGame == false && myToken == 'O') {
			        		System.out.println("Player 2 with O token");
			        		System.out.println("Waiting for player 1 to move");
			        	}
			        	else {
			        		label.setText("Player 2 with YELLOW Token");
			        		newLabel.setText("Waiting for player 1 to move");
			        	}
		          });
		        }
		  
		        while (continuePlay) {         	
		          if (player == PLAYER1) {	
			        	if(guiGame == false && myTurn) {
			        		textConsole();
			        	}	
		        	if(guiGame == true) {
		            waitForPlayerAction(); //Waiting for player 1 to move
		        	}
		            sendMove();
		            receiveInfoFromServer();
		          }else if (player == PLAYER2) {
		            receiveInfoFromServer();
		            
		        	if(guiGame == false && myTurn) {
		        		textConsole();
		        	}
		        	
		            waitForPlayerAction(); // Wait for player 2 to move
		            sendMove(); // Send player 2's move to the server
		          }
		        }
		      }
		      catch (Exception ex) {
		        ex.printStackTrace();
		      }
		    }).start();
		  }
	 
	  /** Wait for the player to make a move */
	  private void waitForPlayerAction() throws InterruptedException {
	    while (wait) {
	      Thread.sleep(100);
	    }
	    wait = true;
	  }
	  
	  /** Send player move to the server */
	  private void sendMove() throws IOException {
	    toServer.writeInt(selectedColumn);
	  }
	  
	  /** Receives info from server about winning conditions */
	  private void receiveInfoFromServer() throws IOException {
		    // Receiving game status
		    int status = fromServer.readInt();

		    if (status == PLAYER1_WON) {
		      continuePlay = false;
		      win = true;
		      if (myToken == 'X') {
		    	  if(guiGame == false) {
		    	  System.out.println("I won! (X)");
		    	  }else
		    	  Platform.runLater(() -> newLabel.setText("I won! (RED)"));
		      }
		      else if (myToken == 'O') {
		          if(guiGame == false) {
		          printBoard(textBoard);
		          System.out.println("Player 1 (X) has won!");  
		          }else {
		    	  Platform.runLater(() -> newLabel.setText("Player 1 (RED) has won!"));
		          }
		        receiveMove();
		      }
		    }
		    else if (status == PLAYER2_WON) {
		      continuePlay = false;
		      win = true;
		      if (myToken == 'O') {
		        if(guiGame == false) {
		          System.out.println("I won! (O)");
		        }else {
		    	  Platform.runLater(() -> newLabel.setText("I won! (YELLOW)"));
		        }
		      }
		      else if (myToken == 'X') {
		        if(guiGame == false) {
		        	System.out.println("Player 2 (O) has won!");
		        }else {
		        	Platform.runLater(() -> newLabel.setText("Player 2 (YELLOW) has won!"));
		        }
		        receiveMove();
		      }
		    }
		    else if (status == DRAW) {
		      continuePlay = false;
		      if(guiGame == false) {
		    	  System.out.println("Game is over, no winner!");
		      }else {
		    	  Platform.runLater(() -> newLabel.setText("Game is over, no winner!"));
		      }
		      if (myToken == 'O') {
		        receiveMove();
		      }
		    }
		    else {
		      receiveMove();
		      if(guiGame == false) {
		    	  Platform.runLater(() -> {
		    		  System.out.println("My turn");});
		      }else {
		      Platform.runLater(() -> newLabel.setText("My turn"));
		      }
		      myTurn = true;
		    }
		  }
	  
	  /**Receives move from server to update board. */
	  private void receiveMove() throws IOException {
		    int column = fromServer.readInt();
		    if(guiGame == true) {
		    Platform.runLater(() -> updateBoard(textBoard,column,enemyToken));
		    }else {
		    	updateBoard(textBoard,column,enemyToken);
		    	if(win == true) {
		    		printBoard(textBoard);
		    	}
		    }
		  }
	  /**Method to run the Text Console version of the game */
	  public void textConsole(){
		  				printBoard(textBoard);
						System.out.println("It is your turn. Choose a column 1-7.");
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
						if(dropPiece(textBoard,choice-1,myToken)) {
							printBoard(textBoard);
							myTurn = false;
							selectedColumn = choice-1;
							System.out.println("Waiting for other player to move");
							wait = false;
						}
					}else {
						textConsole();
					}
		  
	  }
	  
	  /**Updates the game board */
	  public void updateBoard(char[][]board, int column, char player) {
			if(dropPiece(board,column,player)) {
			if(guiGame == true) {
				changeCircles(connect4Shapes);
			}
			}
		}
	  
	  /**Attempts to drop the piece into the game board 
	   * 
	   * @return boolean Boolean value to say if the drop was successful.
	   */
	  public static boolean dropPiece(char[][]board, int column, char player) {
		    boolean dropable = false;
			if(board[0][column]!= ' ') {
				System.out.println("Column is full");
				dropable = false;
			}
			for(int row=board.length-1;row>=0;row--) {
				if(board[row][column]==' ') {
					board[row][column] = player;
					dropable = true;
					break;
				}
			}
			return dropable;
		}

	/**
	 * Creates a rectangle for the background of the Connect 4 game.
	 * 
	 * @return Shape Returns the rectangle
	 */
	public Shape gameBoard() {
        Shape board = new Rectangle((columns + 1) * 60, (rows + 1) * 60);
        board.setFill(Color.LIGHTBLUE);
        return board;
        
}
	/**
	 * Creates a grid of circles to be placed onto the background.
	 * 
	 * @param c4 The 2D array of circles that represent the grid of holes within
	 * the game board
	 */
	public void circles(Shape[][] c4) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Circle circle = new Circle(30);
                circle.setCenterX(30);
                circle.setCenterY(30);
                circle.setTranslateX(j * (65) + 15);
                circle.setTranslateY(i * (65) + 15);
                circle.setFill(Color.WHITE);
                connect4Shapes[i][j] = circle;
                layout.getChildren().add(connect4Shapes[i][j]);
            }
        }
	}
	
	/**
	 * Changes circle colors based on user inputs
	 * 
	 * @param circles The 2D array of circles from the game board is passed in
	 */
	public void changeCircles(Shape[][] circles){
		for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
            	if(textBoard[i][j]=='X') {
            		connect4Shapes[i][j].setFill(Color.RED);
            	}else if(textBoard[i][j]=='O') {
            		connect4Shapes[i][j].setFill(Color.YELLOW);
            	}
            }
		}
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
	
	  /**
	   * The main method is only needed for the IDE with limited
	   * JavaFX support. Not needed for running from the command line.
	   */
	  public static void main(String[] args) {
		launch(args);
}
}