package MemoryGame;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;


public class MemoryGameServer extends JFrame {

    public MemoryGameServer()
    {
    	JTextArea logArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600,300);
        setTitle("Memory Game Server");
        
        setVisible(true);
    	try {
            ServerSocket serverSocket = new ServerSocket(9000);
            logArea.append(new Date() + ": Server started at socket 9000\n");
            
            int sessionNum = 1;
            int ePlay = 0;
            int mPlay = 0;
            int hPlay = 0;

            while (true) {

                try
                {
                	logArea.append(new Date() + ": Waiting for player to join game #" + sessionNum + "\n");
                    Socket player1 = serverSocket.accept();
                    
                    
                    new DataOutputStream(player1.getOutputStream()).writeInt(1);
                    
                    HandleGameTask task = new HandleGameTask(player1);

                    new Thread(task).start();
                    sessionNum++;
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

    public HandleGameTask(Socket player1) {
        this.player1 = player1;
    }

    public void run() {
    	
        try {
        	
            DataInputStream fromPlayer1 = new DataInputStream(player1.getInputStream());
            DataOutputStream toPlayer1 = new DataOutputStream(player1.getOutputStream());

        }
        catch (IOException exc) {
            System.err.println(exc.toString());
        }
    }
}
