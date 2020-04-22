package ui;
import core.Connect4;
//import core.Connect4Client;
import core.Connect4ComputerPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

/**
 * Description: GUI implementation for Connect 4
 * @author Sawyer Breitenbucher
 * @version java8
 *
 */
public class Connect4GUI extends Application implements EventHandler<ActionEvent> {
    boolean isPressed = false;
    private static final String PLAYER_1 = "Player 1";
    private static final String PLAYER_2 = "Player 2";
    private static final String PLAYER_COMPUTER = "COMPUTER";
    private static final int SIZE_OF_BORDER = 70;
    private static final int NUMBER_OF_COLUMNS = 7;
    private static final int NUMBER_OF_ROWS = 6;
    private boolean isComputer;
    String[][] boardGame = new String[6][7];
    Connect4 board = new Connect4(boardGame);
    Connect4TextConsole gameLogic = new Connect4TextConsole();
    Connect4ComputerPlayer connect4ComputerPlayerObject = new Connect4ComputerPlayer();
    boolean player1Turn = true;

    //Button initializations
    Button isComputerButton = new Button("CLICK TO PLAY COMPUTER");
    Button playerButton = new Button("CLICK TO PLAY VS PLAYER");
    Button col1 = new Button("Column 1");
    Button col2 = new Button("Column 2");
    Button col3 = new Button("Column 3");
    Button col4 = new Button("Column 4");
    Button col5 = new Button("Column 5");
    Button col6 = new Button("Column 6");
    Button col7 = new Button("Column 7");

    /**
     * determines what button is being pressed
     * @param source
     * @return int
     */
    private int getColButton(Button source) {
        if (source == col1) {
            return 0;
        } else if (source == col2) {
            return 1;
        } else if (source == col3) {
            return 2;
        } else if (source == col4) {
            return 3;
        } else if (source == col5) {
            return 4;
        } else if (source == col6) {
            return 5;
        } else if (source == col7) {
            return 6;
        }
        return 0;
    }

    /**
     * retrieves a button based on int value
     * @param button
     * @returns button
     */
    private Button getButton(int button) {
        if (button == 0) {
            return col1;
        } else if (button == 1) {
            return col2;
        } else if (button == 2) {
            return col3;
        } else if (button == 3) {
            return col4;
        } else if (button == 4) {
            return col5;
        } else if (button == 5) {
            return col6;
        } else if (button == 6) {
            return col7;
        }
        return null;
    }

    /**
     * true if button is a column button
     *
     * @param col
     * @returns boolean
     */
    public boolean isColButton(Button col) {
        if (col == col1 || col == col2 || col == col3 || col == col4 || col == col5 ||
                col == col6 || col == col7) {
            return true;
        }
        return false;
    }

    /**
     * if a player has won, display the winning message
     * @param player
     * @return
     */
    private Parent hasWon(String player){
        Pane root = new Pane();
        if (!player.equals("Computer")) {
            Button winDisplay = new Button(player + " has won the game!");
            winDisplay.setLayoutX(0);
            winDisplay.setMinWidth(600);
            winDisplay.setMinHeight(490);
            winDisplay.setStyle("-fx-background-color: #000000; ");
            winDisplay.setTextFill(Color.WHITE);
            root.getChildren().add(winDisplay);
        }
        else{
            Button winDisplay = new Button ("The "+player+" has won the game!");
            winDisplay.setLayoutX(0);
            winDisplay.setMinWidth(600);
            winDisplay.setMinHeight(490);
            winDisplay.setStyle("-fx-background-color: #000000; ");
            winDisplay.setTextFill(Color.WHITE);
            root.getChildren().add(winDisplay);
        }

        return root;
    }



    /**
     * creates the content that will be shown
     *
     * @returns Parent
     */
    private Parent playerOrComputer() {
        Pane root = new Pane();
        root.getChildren().add(isPlayerButton());
        root.getChildren().add(isComputerButton());


        return root;
    }

    private Parent playerBoard(Connect4 board) throws IOException, InterruptedException {
        Pane root = new Pane();
        col1.setLayoutX(19);
        col1.setLayoutY(465);
        col2.setLayoutX(94);
        col2.setLayoutY(465);
        col3.setLayoutX(170);
        col3.setLayoutY(465);
        col4.setLayoutX(245);
        col4.setLayoutY(465);
        col5.setLayoutX(320);
        col5.setLayoutY(465);
        col6.setLayoutX(395);
        col6.setLayoutY(465);
        col7.setLayoutX(472);
        col7.setLayoutY(465);

        Shape gridShape = populateBoard();
        root.getChildren().add(gridShape);

        root.getChildren().add(col1);
        root.getChildren().add(col2);
        root.getChildren().add(col3);
        root.getChildren().add(col4);
        root.getChildren().add(col5);
        root.getChildren().add(col6);
        root.getChildren().add(col7);

        Circle[][] circleArray = new Circle[6][7];

        //POPULATE BOARD FROM SERVER HERE
        //Server -> Client
        //Updates board accordingly
//        client.waitForPlayerAction();
//        client.receiveInfoFromServer(board);

        for (int row = 0; row < boardGame.length; row++) {
            for (int col = 0; col < boardGame[row].length; col++) {
                circleArray[row][col] = new Circle(SIZE_OF_BORDER / 2 - 3);
                circleArray[row][col].setCenterX(SIZE_OF_BORDER / 2 - 2);
                circleArray[row][col].setCenterY(SIZE_OF_BORDER / 2 - 2);
                circleArray[row][col].setTranslateX(col * (SIZE_OF_BORDER + 5) + SIZE_OF_BORDER / 4);
                circleArray[row][col].setTranslateY(row * (SIZE_OF_BORDER + 5) + SIZE_OF_BORDER / 4);
                switch (boardGame[row][col]) {
                    case " ":
                        circleArray[row][col].setFill(Color.WHITE);
                        break;
                    case "X":
                        circleArray[row][col].setFill(Color.DARKRED);
                        break;
                    case "O":
                        circleArray[row][col].setFill(Color.DARKBLUE);
                        break;
                }
                root.getChildren().add(circleArray[row][col]);
            }
        }

        return root;
    }


    /**
     * button that determines if the player is playing
     *
     * @returns button
     */
    private Button isPlayerButton() {
        playerButton.setLayoutX(300);
        playerButton.setMinWidth(300);
        playerButton.setMinHeight(490);
        playerButton.setStyle("-fx-background-color: #129999; ");
        playerButton.setTextFill(Color.WHITE);
        return playerButton;
    }

    /**
     * button that determines if the computer is playing
     *
     * @returns button
     */
    private Button isComputerButton() {
        isComputerButton.setMinWidth(300);
        isComputerButton.setMinHeight(490);
        isComputerButton.setStyle("-fx-background-color: #ff6848; ");
        isComputerButton.setTextFill(Color.WHITE);
        return isComputerButton;
    }

    /**
     * makes basic shape for board
     *
     * @returns shape
     */

    public Shape populateBoard(){
        Shape shape = new Rectangle((NUMBER_OF_COLUMNS + 1) * (SIZE_OF_BORDER), (NUMBER_OF_ROWS + 1) * SIZE_OF_BORDER);

        shape.setFill(Color.DARKGREY);

        return shape;
    }




    @Override
    /**
     * void method
     * starts the functionality
     */
    public void start(Stage primaryStage) {
        //Populate the array with spaces to avoid null
        for (int row = 0; row < boardGame.length; row++) {
            for (int col = 0; col < boardGame[row].length; col++) {
                boardGame[row][col] = " ";
            }
        }

        primaryStage.setTitle("Connect 4 Game");
        primaryStage.setScene(new Scene(playerOrComputer()));

        isComputerButton().setOnAction(this);
        isPlayerButton().setOnAction(this);
        col1.setOnAction(this);
        col2.setOnAction(this);
        col3.setOnAction(this);
        col4.setOnAction(this);
        col5.setOnAction(this);
        col6.setOnAction(this);
        col7.setOnAction(this);

        primaryStage.show();



    }

    /**
     * main functionality
     * board logic
     * @param event
     * catches indexOutOfBoundsException
     */
    @Override
    public void handle(ActionEvent event) {
        try{

        if (event.getSource() == isComputerButton()) {
            Stage stage = (Stage) isComputerButton().getScene().getWindow();
            isComputer = true;
            board.setIsComputer(true);
            isPressed = true;
            stage.hide();
            //launch computer scene?
            Scene playerBoard = new Scene(playerBoard(board));
            stage.setScene(playerBoard);
            stage.show();

        } else if (event.getSource() == isPlayerButton()) {
            Stage stage = (Stage) isPlayerButton().getScene().getWindow();
            isComputer = false;
            isPressed = true;
            stage.hide();
            //launch pvp scene?
            //FOR EVERY BOARD REFERENCE, DO A GETBOARD ON SERVER CLASS TO GET UPDATED BOARD
            // CALL ALL METHODS TO MAKE SERVER AND SOCKETS ETC.

            Scene playerBoard = null;
            playerBoard = new Scene(playerBoard(board));
            stage.setScene(playerBoard);
            stage.show();
        }
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }

        try {
        if (!isComputer) {
            boolean buttonPress = true;
                //CHECK TO SEE IF TWO PLAYERS (while isTwoPlayersConnected())

                //if it is player turn and the button is a column button
                if (player1Turn && isColButton((Button) event.getSource()) && buttonPress) {
                    board.playMove(getColButton((Button) event.getSource()), "X");
                    player1Turn = false;
                    buttonPress = false;
                    Stage stage = (Stage) col1.getScene().getWindow();
                    Scene playerBoard = new Scene(playerBoard(board));
                    stage.setScene(playerBoard);
                    stage.show();

                    if(board.checkWinningMove(board.getRowPosition(),getColButton((Button) event.getSource()), "X")){
                        Scene hasWon = new Scene(hasWon(PLAYER_1));
                        stage.setScene(hasWon);
                        stage.show();
                        //DISPLAY PLAYER 1 HAS WON
                    }

                }
                if (!player1Turn && isColButton((Button) event.getSource()) && buttonPress) {
                    board.playMove(getColButton((Button) event.getSource()), "O");
                    player1Turn = true;
                    buttonPress = false;
                    Stage stage = (Stage) col1.getScene().getWindow();
                    Scene playerBoard = new Scene(playerBoard(board));
                    stage.setScene(playerBoard);
                    stage.show();

                    if(board.checkWinningMove(board.getRowPosition(),getColButton((Button) event.getSource()), "O")){
                        Scene hasWon = new Scene(hasWon(PLAYER_2));
                        stage.setScene(hasWon);
                        stage.show();
                        //DISPLAY PLAYER 2 HAS WON
                    }
                }
            }

                if (isComputer) {
                    //if it is player turn and the button is a column button
                    if (player1Turn && isColButton((Button) event.getSource())) {
                        Stage stage = (Stage) col1.getScene().getWindow();

                        board.playMove(getColButton((Button) event.getSource()), "X");
                        if(board.checkWinningMove(board.getRowPosition(),getColButton((Button) event.getSource()), "X")){
                            Scene hasWon = new Scene(hasWon(PLAYER_1));
                            stage.setScene(hasWon);
                            stage.show();
                            //DISPLAY PLAYER 1 HAS WON
                        }

                        //then does computer turn after
                        int randomMove = connect4ComputerPlayerObject.randColGenerator(board);
                        board.playMove(randomMove, "O");

                        Scene playerBoard = new Scene(playerBoard(board));
                        stage.setScene(playerBoard);
                        stage.show();

                        if(board.checkWinningMove(board.getRowPosition(),randomMove, "O")){
                            Scene hasWon = new Scene(hasWon(PLAYER_COMPUTER));
                            stage.setScene(hasWon);
                            stage.show();
                            //DISPLAY PLAYER COMPUTER HAS WON
                        }

                    }
                }

            } catch (IndexOutOfBoundsException | IOException | InterruptedException exception) {
                //disable button
                Objects.requireNonNull(getButton(getColButton((Button) event.getSource()))).setDisable(true);
            }
        }
    }


