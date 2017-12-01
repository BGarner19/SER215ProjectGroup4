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
                    Socket player = serverSocket.accept();

                    new DataOutputStream(player.getOutputStream()).writeInt(1);

                    HandleGameTask task = new HandleGameTask(player);

                    new Thread(task).start();
                }
                catch (IOException ex) {
                    //Do something to socket so it doesn't freeze program?
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

    private Socket player;

    public HandleGameTask(Socket player) {
        this.player = player;
    }

    public void run() {
    	int eCode = 1;
    	int mCode = 2;
    	int hCode = 3;
    	
        try {
            DataInputStream fromPlayer = new DataInputStream(player.getInputStream());
            DataOutputStream toPlayer = new DataOutputStream(player.getOutputStream());
            
        }
        catch (IOException exc) {
            System.err.println(exc.toString());
        }
    }
}
