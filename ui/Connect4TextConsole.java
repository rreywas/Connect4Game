package ui;

import core.Connect4;
//import core.Connect4Client;
import core.Connect4ComputerPlayer;
//import core.Connect4Server;
import javafx.application.Application;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static core.Connect4.PLAYER_1_SYMBOL;
import static core.Connect4.PLAYER_2_SYMBOL;
import static javafx.application.Application.launch;


public class Connect4TextConsole {


    /**
     * inputMove will take in input from the player, and ensure it is valid
     * @param scan
     * @param board
     * @return will return a valid input move
     */
   public int inputMove(Scanner scan, Connect4 board){
       int colInput = -1;
       do {
           try {
               colInput = scan.nextInt() - 1;

               if (colInput > 6 ||
                       colInput < 0 ||
                       board.getBoardGame()[5][colInput].equals(PLAYER_1_SYMBOL) ||
                       board.getBoardGame()[5][colInput].equals(PLAYER_2_SYMBOL))
               {
                   System.out.println("Invalid input, please enter a column number from 1-7.");
               }

               } catch (InputMismatchException exception) {
                   //will re-enter the while loop
                  System.out.println("Invalid input, please enter a column number from 1-7.");
                  scan.nextLine();
               }

           }
       while (colInput > 6 ||
               colInput < 0 ||
               board.getBoardGame()[5][colInput].equals(PLAYER_1_SYMBOL) ||
               board.getBoardGame()[5][colInput].equals(PLAYER_2_SYMBOL));

       return colInput;
   }

    /**
     * true if the board is full, false if the board is not full
     * @param board
     * @return
     *
     */
    public static boolean isBoardFull(Connect4 board){
        int i = 0;
        while((board.getBoardGame()[5][i].equals(PLAYER_1_SYMBOL) ||
                board.getBoardGame()[5][i].equals(PLAYER_2_SYMBOL)) && i < 7){
            i++;
            if (i == 6){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param boardGame
     * @returns board game
     */
    public static String[][] getBoardGame(String[][] boardGame){
        return boardGame;
    }
    /**
     * populates the board with the spaces and prints it out when called
     * @param boardGame
     * @return String
     */
    public static String printGame(String[][] boardGame){
    StringBuilder printer = new StringBuilder();

        for (int i = 5; i >= 0; i--){
            for (int j = 0; j <= 6; j++){
                printer.append("|").append(boardGame[i][j]);
            }
            printer.append("|");
            printer.append("\n");
        }
        return printer.toString();

    }

    /**
     *  Functionality to play player vs another player with Text Console
     * @param scan Scanner object
     * @param boardGame 2D String Array
     * @param board Connect4 object
     * @param text Connect4TextConsole object
     */
    public static void playerVsPlayerText(Scanner scan, String[][] boardGame, Connect4 board, Connect4TextConsole text) throws IOException {
        //Print out game
        System.out.print(Connect4TextConsole.printGame(boardGame));
        System.out.println("\n Begin Game.");

        while (!isBoardFull(board)) {
            //PLAYER X TURN
            System.out.println("Player X - your turn. Choose a column number from 1-7.");
            int colInput = text.inputMove(scan, board);

            board.playMove(colInput, "X");
            System.out.print(Connect4TextConsole.printGame(boardGame));
            if (board.checkWinningMove(board.getRowPosition(), colInput, "X")) {
                System.out.println("Player X won the game.");
                break;
            }
            //PLAYER O TURN
            System.out.println("Player O - your turn. Choose a column number from 1-7.");
            colInput = text.inputMove(scan, board);
            board.playMove(colInput, "O");
            System.out.print(Connect4TextConsole.printGame(boardGame));
            if (board.checkWinningMove(board.getRowPosition(), colInput, "O")) {
                System.out.println("Player O won the game.");
                break;
            }
        }
    }

    /**
     * Functionality for a player to vs a computer with Text Console
     * @param scan Scanner object
     * @param boardGame 2D String Array board
     * @param board Connect4 object
     * @param text Connect4TextConsole object
     * @param computerPlayer Connect4ComputerPlayer object
     */
    public static void computerVsPlayerText(Scanner scan, String[][] boardGame, Connect4 board,
                                        Connect4TextConsole text, Connect4ComputerPlayer computerPlayer) throws IOException {
        //Print out game
        System.out.print(Connect4TextConsole.printGame(boardGame));
        System.out.println("\n Begin Game.");

        while (!isBoardFull(board)){
            //Player Turn
            System.out.println("It is your turn. Choose a column number from 1-7.");
            int colInput = text.inputMove(scan, board);
            int computerCol = computerPlayer.randColGenerator(board);

            //plays move
            board.playMove(colInput, "X");


            System.out.print(Connect4TextConsole.printGame(boardGame));

            //checks winning condition
            if (board.checkWinningMove(board.getRowPosition(), colInput, "X")) {
                System.out.println("You won the game.");
                break;
            }

            //Computer turn
            System.out.println("Computers turn.");

            //plays move
            board.playMove(computerCol, "O");
            System.out.print(Connect4TextConsole.printGame(boardGame));

            //checks winning condition
            if (board.checkWinningMove(board.getRowPosition(), computerCol, "O")) {
                System.out.println("The computer won the game.");
                break;
            }
        }

    }

    public static boolean isTextConsole(Scanner scan){
        //Checks to see if Text Console or GUI
        System.out.println("Type G for GUI or T for Text-Based-UI.");
        String decideInput = scan.nextLine();
        while (!decideInput.equals("G") && !decideInput.equals("T")){
            System.out.println("Please enter a valid input. Type G for GUI or T for Text-Based-UI.");
            decideInput = scan.nextLine();
        }
        if (decideInput.equals("T")){
            return true;
        }
        if (decideInput.equals("G")){
            return false;
        }

        System.out.println("Nothing has been chosen");
        return false;

    }

    public static void main(String[] args) throws IOException {
       boolean gameOver = false;
       boolean isConsole = true;
        Scanner scan = new Scanner(System.in);
        String[][] boardGame = new String[6][7];
        Connect4 board = new Connect4(boardGame);
        Connect4TextConsole text = new Connect4TextConsole();
        Connect4ComputerPlayer computerPlayer = new Connect4ComputerPlayer();
        //Connect4GUI gui = new Connect4GUI();
//        Connect4Client client = new Connect4Client();
//
//        Connect4Server server = new Connect4Server();
//        client.connectToServer();

        //Populate the array with spaces first in order to print
        for (int row = 0; row < boardGame.length; row++) {
            for (int col = 0; col < boardGame[row].length; col++) {
                boardGame[row][col] = " ";
            }
        }


        if (isTextConsole(scan)) {

            //Checks to see if player wants to play with Computer or Player
            if (computerPlayer.isPlayingComputer(scan)) {
                System.out.println("Start game with Computer \n");
                computerVsPlayerText(scan, boardGame, board, text, computerPlayer);
                gameOver = true;


            }
            if (!gameOver) {
                System.out.println("Start game with Player \n");
                playerVsPlayerText(scan, boardGame, board, text);
            }


        }
        else{
            Application.launch(Connect4GUI.class, args);
               // client.connectToServer(); // runs GUI
        }
    }


}
