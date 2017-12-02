package MemoryGame;

import java.io.*;
import java.net.*;


public class MemoryGameServer {

    public MemoryGameServer()
    {
    	try {
            ServerSocket serverSocket = new ServerSocket(8000);
            int sessionNum = 1;
            int ePlay = 0;
            int mPlay = 0;
            int hPlay = 0;

            while (true) {

                try
                {
                    Socket player1 = serverSocket.accept();
                    Socket player2 = serverSocket.accept();
                    
                    new DataOutputStream(player1.getOutputStream()).writeInt(1);
                    new DataOutputStream(player2.getOutputStream()).writeInt(2);
                    HandleGameTask task = new HandleGameTask(player1, player2);

                    new Thread(task).start();
                }
                catch (IOException ex) {
                }
            }
        }
        catch (IOException exc) {
            System.err.println(exc.toString());
        }
    }

    public static void main(String[] args) {
        new MemoryGameServer();
    }
}

class HandleGameTask implements Runnable {

    private Socket player1;
    private Socket player2;

    public HandleGameTask(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void run() {
    	int eCode = 1;
    	int mCode = 2;
    	int hCode = 3;
    	
        try {
        	
            DataInputStream fromPlayer1 = new DataInputStream(player1.getInputStream());
            DataOutputStream toPlayer1 = new DataOutputStream(player1.getOutputStream());
            DataInputStream fromPlayer2 = new DataInputStream(player2.getInputStream());
            DataOutputStream toPlayer2 = new DataOutputStream(player2.getOutputStream());
            
        
        	 double p1score = fromPlayer1.readDouble();
             double p2score = fromPlayer2.readDouble();
             
             if (p1score > p2score) {
            	 toPlayer1.writeInt(1);
            	 toPlayer2.writeInt(0);
             } 
             else if (p1score < p2score) {
            	 toPlayer1.writeInt(0);
            	 toPlayer2.writeInt(1);
             }
             else {
            	 toPlayer1.writeInt(2);
            	 toPlayer1.writeInt(2);
             }
             
            
           
        }
        catch (IOException exc) {
            System.err.println(exc.toString());
        }
    }
}
