package ui;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import core.Connect4;
import core.Connect4ComputerPlayer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
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
 * Creates GUI to be used for Connect4. The logic is used from Connect4.java
 * 
 * @author Brendan Brunelle
 * @version (version) 3/23/20
 */
public class Connect4GUI extends Application {

	public int columns = 7;
	public int rows = 6;
	Button button1,button2,button3,button4,buttonC1,buttonC2,buttonC3,buttonC4,buttonC5,buttonC6,buttonC7;
	Scene scene1, scene2, scene3;
	Stage window;
	public boolean computer;
	public boolean choice;
	public char player = 'R';
	public boolean win = false;
	Pane layout, layout2, layout3;
	public static int computerChoice;
	char[][] textBoard = new char[rows][columns];
	Shape gameBoard;
	public Shape[][] connect4Shapes = new Shape[rows][columns];
	Random rand = new Random();
	
	
	
	/**
	 * Creates the stage for the JavaFX GUI.
	 * 
	 * @param connect4 Stage for JavaFX
	 */
	@Override
	public void start(Stage connect4) throws Exception {
		window = connect4;
		window.setTitle("Connect 4");
		Label label = new Label("Would you like to use GUI or Text Console for Connect 4?");
		button1 = new Button("GUI");
		button2 = new Button("Text Console");
		button3 = new Button("AI");
		button4 = new Button("Player");
		buttonC1 = new Button("1");
		buttonC2 = new Button("2");
		buttonC3 = new Button("3");
		buttonC4 = new Button("4");
		buttonC5 = new Button("5");
		buttonC6 = new Button("6");
		buttonC7 = new Button("7");
		
		button1.setOnAction(e -> {
		window.setScene(scene2);
		});
	
		button2.setOnAction(e -> {
			Connect4TextConsole.main(null);
			//connect4.close();
		});
		
		button3.setOnAction(e -> {
			computer = true;
			fillTextBoard(textBoard);
			window.setScene(scene3);
		});
		
		button4.setOnAction(e -> {
			computer = false;
			fillTextBoard(textBoard);
			window.setScene(scene3);
		});
		
		
		
		buttonC1.setOnAction(e -> {
			if(Connect4.dropPiece(textBoard, 0, player)) {
				changeCircles(connect4Shapes);
				choice = true;
			}else {
				displayAlert("Error","Piece couldn't be dropped");
				choice = false;
			}
			if(choice==true) {
				if(Connect4.checkWin(textBoard)) {
					displayAlert("Winner!", "Player " + player + " won the game!");
					win = true;
					connect4.close();
				}else if(computer == false){
					if(player=='R')
						player = 'Y';
					else
						player = 'R';
						
				}else {
					computerMove();
					changeCircles(connect4Shapes);
					if(Connect4.checkWin(textBoard)) {
						displayAlert("Winner!", "Computer won the game!");
						connect4.close();
					}
				}
			}
		});
		
		buttonC2.setOnAction(e -> {
			if(Connect4.dropPiece(textBoard, 1, player)) {
				changeCircles(connect4Shapes);
				choice = true;
			}else {
				displayAlert("Error","Piece couldn't be dropped");
				choice = false;
			}
			if(choice==true) {
				if(Connect4.checkWin(textBoard)) {
					displayAlert("Winner!", "Player " + player + " won the game!");
					win = true;
					connect4.close();
				}else if(computer == false){
					if(player=='R')
						player = 'Y';
					else
						player = 'R';
						
				}else {
					computerMove();
					changeCircles(connect4Shapes);
					if(Connect4.checkWin(textBoard)) {
						displayAlert("Winner!", "Computer won the game!");
						connect4.close();
					}
				}
			}
		});
		
		buttonC3.setOnAction(e -> {
			if(Connect4.dropPiece(textBoard, 2, player)) {
				changeCircles(connect4Shapes);
				choice = true;
			}else {
				displayAlert("Error","Piece couldn't be dropped");
				choice = false;
			}
			if(choice==true) {
				if(Connect4.checkWin(textBoard)) {
					displayAlert("Winner!", "Player " + player + " won the game!");
					win = true;
					connect4.close();
				}else if(computer == false){
					if(player=='R')
						player = 'Y';
					else
						player = 'R';
						
				}else {
					computerMove();
					changeCircles(connect4Shapes);
					if(Connect4.checkWin(textBoard)) {
						displayAlert("Winner!", "Computer won the game!");
						connect4.close();
					}
				}
			}
		});
		
		buttonC4.setOnAction(e -> {
			if(Connect4.dropPiece(textBoard, 3, player)) {
				changeCircles(connect4Shapes);
				choice = true;
			}else {
				displayAlert("Error","Piece couldn't be dropped");
				choice = false;
			}
			if(choice==true) {
				if(Connect4.checkWin(textBoard)) {
					displayAlert("Winner!", "Player " + player + " won the game!");
					win = true;
					connect4.close();
				}else if(computer == false){
					if(player=='R')
						player = 'Y';
					else
						player = 'R';
						
				}else {
					computerMove();
					changeCircles(connect4Shapes);
					if(Connect4.checkWin(textBoard)) {
						displayAlert("Winner!", "Computer won the game!");
						connect4.close();
					}
				}
			}
		});
		
		buttonC5.setOnAction(e -> {
			if(Connect4.dropPiece(textBoard, 4, player)) {
				changeCircles(connect4Shapes);
				choice = true;
			}else {
				displayAlert("Error","Piece couldn't be dropped");
				choice = false;
			}
			if(choice==true) {
				if(Connect4.checkWin(textBoard)) {
					displayAlert("Winner!", "Player " + player + " won the game!");
					win = true;
					connect4.close();
				}else if(computer == false){
					if(player=='R')
						player = 'Y';
					else
						player = 'R';
						
				}else {
					computerMove();
					changeCircles(connect4Shapes);
					if(Connect4.checkWin(textBoard)) {
						displayAlert("Winner!", "Computer won the game!");
						connect4.close();
					}
				}
			}
		});
		
		buttonC6.setOnAction(e -> {
			if(Connect4.dropPiece(textBoard, 5, player)) {
				changeCircles(connect4Shapes);
				choice = true;
			}else {
				displayAlert("Error","Piece couldn't be dropped");
				choice = false;
			}
			if(choice==true) {
				if(Connect4.checkWin(textBoard)) {
					displayAlert("Winner!", "Player " + player + " won the game!");
					win = true;
					connect4.close();
				}else if(computer == false){
					if(player=='R')
						player = 'Y';
					else
						player = 'R';
						
				}else {
					computerMove();
					changeCircles(connect4Shapes);
					if(Connect4.checkWin(textBoard)) {
						displayAlert("Winner!", "Computer won the game!");
						connect4.close();
					}
				}
			}
		});
		
		buttonC7.setOnAction(e -> {
			if(Connect4.dropPiece(textBoard, 6, player)) {
				changeCircles(connect4Shapes);
				choice = true;
			}else {
				displayAlert("Error","Piece couldn't be dropped");
				choice = false;
			}
			if(choice==true) {
				if(Connect4.checkWin(textBoard)) {
					displayAlert("Winner!", "Player " + player + " won the game!");
					connect4.close();
				}else if(computer == false){
					if(player=='R')
						player = 'Y';
					else
						player = 'R';
						
				}else {
					computerMove();
					changeCircles(connect4Shapes);
					if(Connect4.checkWin(textBoard)) {
						displayAlert("Winner!", "Computer won the game!");
						connect4.close();
					}
				}
			}
		});
		
	
		layout = new Pane();
		label.setLayoutX(55);
		button1.setMinSize(50, 50);
		button2.setMinSize(50, 50);
		button1.setLayoutX(95);
		button1.setLayoutY(65);
		button2.setLayoutX(220);
		button2.setLayoutY(65);
		layout.getChildren().add(label);
		layout.getChildren().addAll(button1,button2);
		
		scene1 = new Scene(layout,400,200);
		window.setScene(scene1);
		window.show();
			
		layout2 = new Pane();
		Label label2 = new Label("Would you like to play an AI or another Player?");
		label2.setLayoutX(55);
		button3.setMinSize(50, 50);
		button4.setMinSize(50, 50);
		button3.setLayoutX(95);
		button3.setLayoutY(65);
		button4.setLayoutX(220);
		button4.setLayoutY(65);
		layout2.getChildren().add(label2);
		layout2.getChildren().addAll(button3,button4);		
		scene2 = new Scene(layout2,400,200);
		
				
		layout3 = new Pane();
		gameBoard = gameBoard();
		layout3.getChildren().add(gameBoard);
		circles(connect4Shapes);
		/*
		Label newLabel = new Label("Test");
		Label newerLabel = new Label("Test2");
		newLabel.setScaleX(1.5);
		newLabel.setScaleY(1.5);
		newLabel.setLayoutX(40);
		newLabel.setLayoutY(490);
		newerLabel.setScaleX(1.5);
		newerLabel.setScaleY(1.5);
		newerLabel.setLayoutX(40);
		newerLabel.setLayoutY(520);
		*/
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
		//layout3.getChildren().add(newLabel);
		//layout3.getChildren().add(newerLabel);
		layout3.getChildren().addAll(buttonC1,buttonC2,buttonC3,buttonC4,buttonC5,buttonC6,buttonC7);
		
		scene3 = new Scene(layout3,475,560);
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
	                layout3.getChildren().add(connect4Shapes[i][j]);
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
	            	if(textBoard[i][j]=='R') {
	            		connect4Shapes[i][j].setFill(Color.RED);
	            	}else if(textBoard[i][j]=='Y') {
	            		connect4Shapes[i][j].setFill(Color.YELLOW);
	            	}
	            }
			}
		}
		
		/**
		 * Displays alerts for the game such as a winner or full column
		 * 
		 * @param title The title of the Alert
		 * @param message What the alert will display
		 */
	    public static void displayAlert(String title, String message) {
	        Stage window = new Stage();

	        //Block events to other windows
	        window.initModality(Modality.APPLICATION_MODAL);
	        window.setTitle(title);
	        window.setMinWidth(250);

	        Label label = new Label();
	        label.setText(message);
	        Button closeButton = new Button("Close this Window");
	        closeButton.setOnAction(e -> window.close());

	        VBox layout = new VBox(10);
	        layout.getChildren().addAll(label, closeButton);
	        layout.setAlignment(Pos.CENTER);

	        //Display window and wait for it to be closed before returning
	        Scene scene = new Scene(layout);
	        window.setScene(scene);
	        window.showAndWait();
	    }

	    
	    /**
	     * Fills a text board of blank values
	     * 
	     * @param tb Passes in the textBoard global variable
	     */
	    public void fillTextBoard(char[][] tb) {
			for(int i=0;i<tb.length;i++) {
				for(int j=0; j<tb[i].length;j++) {
					tb[i][j] = ' ';
				}
			}
	    }
	    
	    /**
	     * Generates a random number
	     * 
	     * @return Integer The random number
	     */
		public int generateRandom() {
			computerChoice = rand.nextInt(7)+1;
			return computerChoice;
		}
		
		/**
		 * Computer logic for the GUI. It changes the textBoard based on the random
		 * number generated
		 * 
		 * @return void
		 */
		public void computerMove() {
			generateRandom();
			if(!Connect4.dropPiece(textBoard, computerChoice-1, 'Y')) {
				computerMove();
			}
			}
			
	    
	public static void main(String[] args) {
		launch(args);
	}

	
}
