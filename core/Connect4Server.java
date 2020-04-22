//package core;
//
//import java.io.*;
//import java.net.*;
//import java.util.Date;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextArea;
//import javafx.stage.Stage;
//import jdk.internal.org.objectweb.asm.Handle;
//import ui.Connect4GUI;
//import ui.Connect4TextConsole;
//
//public class Connect4Server extends Application{
//    private int sessionNo = 1; // Number a session
//    @Override // Override the start method in the Application class
//    public void start(Stage primaryStage) {
//        TextArea taLog = new TextArea();
//        // Create a scene and place it in the stage
//        Scene scene = new Scene(new ScrollPane(taLog), 450, 200);
//        primaryStage.setTitle("Connect4Server"); // Set the stage title
//        primaryStage.setScene(scene); // Place the scene in the stage
//        primaryStage.show(); // Display the stage
//
//        new Thread( () -> {
//            try {
//                // Create a server socket
//                ServerSocket serverSocket = new ServerSocket(8000);
//                // Ready to create a session for every two players
//                while (true) {
//                    // Connect to player 1
//                    Socket player1 = serverSocket.accept();
//                    player1.getInetAddress().getHostAddress();
//
//                    // Connect to player 2
//                    Socket player2 = serverSocket.accept();
//                    player2.getInetAddress().getHostAddress();
//
//                    // Display this session and increment session number
//                    sessionNo++;
//                    // Launch a new thread for this session of two players
//                    new Thread(new HandleASession(player1, player2)).start();
//                }
//            }
//            catch(IOException ex) {
//                ex.printStackTrace();
//            }
//        }).start();
//    }
//
//    // Define the thread class for handling a new session for two players
//    class HandleASession implements Runnable{
//        private Socket player1;
//        private Socket player2;
//        Connect4TextConsole boardGameText = new Connect4TextConsole();
//        // Create and initialize cells
//        protected String[][] board = new String[6][7];
//        Connect4 connect4 = new Connect4(board);
//        private DataInputStream fromPlayer1;
//        private DataOutputStream toPlayer1;
//        private DataInputStream fromPlayer2;
//        private DataOutputStream toPlayer2;
//        // Continue to play
//        private boolean continueToPlay = true;
//        /** Construct a thread */
//        public HandleASession(Socket player1, Socket player2) {
//            this.player1 = player1;
//            this.player2 = player2;
//            // Initialize array
//            for (int row = 0; row < board.length; row++) {
//                for (int col = 0; col < board[row].length; col++) {
//                    board[row][col] = " ";
//                }
//            }
//        }
//
//        /** Implement the run() method for the thread */
//        public void run() {
//            try {
//                // Create data input and output streams
//                DataInputStream fromPlayer1 = new DataInputStream(
//                        player1.getInputStream());
//                DataOutputStream toPlayer1 = new DataOutputStream(
//                        player1.getOutputStream());
//                DataInputStream fromPlayer2 = new DataInputStream(
//                        player2.getInputStream());
//                DataOutputStream toPlayer2 = new DataOutputStream(
//                        player2.getOutputStream());
//                // Continuously serve the players and determine and report
//                // the game status to the players
//                while (true) {
//                    //player1 turn
//                   int column = fromPlayer1.readInt();
//                   board[connect4.nextOpenSpace(column)][column] = "X";
//                    sendMove(toPlayer2, connect4.nextOpenSpace(column), column, "X");
//                    if (connect4.isGameWon()) {
//                        break;
//                    }
//                   //now read player 2 turn
//                    column = fromPlayer2.readInt();
//                    board[connect4.nextOpenSpace(column)][column] = "O";
//                    sendMove(toPlayer1, connect4.nextOpenSpace(column), column, "O");
//                    if (connect4.isGameWon()) {
//                        break;
//                    }
//                    //continue reading / taking input
//                }
//            }
//            catch(IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//
//        /** Send the move to other player */
//        public void sendMove(DataOutputStream out, int row, int column, String piece)
//                throws IOException {
//            out.writeInt(row); // Send row index
//            out.writeInt(column); // Send column index
//            out.writeChars(piece); //send piece value
//        }
//
//
//        }
//
////    public static void main(String[] args) {
////        Connect4Client client = new Connect4Client();
////        launch(args);
////        client.connectToServer();
////
////        Connect4Client client2 = new Connect4Client();
////        client2.connectToServer();
////    }
//    }
