//package core;
//
//import java.io.*;
//import java.net.*;
//import javafx.application.Application;
//import ui.Connect4GUI;
//
//public class Connect4Client{
//
//    // Input and output streams from/to server
//    private DataInputStream fromServer;
//    private DataOutputStream toServer;
//    // Continue to play?
//    private boolean continueToPlay = true;
//    // Wait for the player to mark a cell
//    private boolean waiting = true;
//    // Host name or ip
//    private String host = "localhost";
//        // Connect to the server
//
//    public void connectToServer() {
//        try {
//            // Create a socket to connect to the server
//            Socket socket = new Socket(host, 8000);
//            // Create an input stream to receive data from the server
//            fromServer = new DataInputStream(socket.getInputStream());
//            // Create an output stream to send data to the server
//            toServer = new DataOutputStream(socket.getOutputStream());
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        // Control the game on a separate thread
//        new Thread(() -> {
//            try {
//                //launch game
//
//                Application.launch(Connect4GUI.class, "", "", "");
//            }
//            catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//        }).start();
//    }
//    /** Wait for the player to mark a cell */
//    public void waitForPlayerAction() throws InterruptedException {
//        while (waiting) {
//            Thread.sleep(100);
//        }
//        waiting = true;
//    }
//    /** Send this player's move to the server */
//    public void sendMove(int columnSelected) throws IOException {
//        toServer.writeInt(columnSelected); // Send the selected column
//    }
//    /** Receive info from the server */
//    public void receiveInfoFromServer(Connect4 board) throws IOException {
//        // Get the other player's move
//        int row = fromServer.readInt();
//        int column = fromServer.readInt();
//        String piece = fromServer.readLine();
//
//        board.playMove(column, piece);
//
//    }
//
//}