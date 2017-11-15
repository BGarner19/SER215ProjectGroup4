import javax.swing.*;
import java.awt.*;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MemoryGameServer extends JFrame {

    public static void main(String[] args) {
        MemoryGameServer serverFrame = new MemoryGameServer();
    }

    public MemoryGameServer() {

        JTextArea logArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600,300);
        setTitle("Memory Game Server");
        setVisible(true);

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            logArea.append(new Date() + ": Server started at socket 8000\n");

            int sessionNum = 1;

            while (true) {
                logArea.append(new Date() + ": Waiting for player to join game #" + sessionNum + "\n");

                Socket player = serverSocket.accept();

                logArea.append(new Date() + ": Player One has joined game #" + sessionNum + "\n");
                logArea.append("Player's IP Address is: " + player.getInetAddress().getHostAddress() + "\n");

                new DataOutputStream(player.getOutputStream()).writeInt(1);

                logArea.append(new Date() + ": Starting a thread for game #" + sessionNum++ + "\n");

                HandleGameTask task = new HandleGameTask(player);

                new Thread(task).start();
            }
        }
        catch (IOException exc) {
            System.err.println(exc.toString());
        }
    }
}

class HandleGameTask implements Runnable {

    private Socket player;

    public HandleGameTask(Socket player) {
        this.player = player;
    }

    public void run() {
        try {
            DataInputStream fromPlayer = new DataInputStream(player.getInputStream());
            DataOutputStream toPlayer = new DataOutputStream(player.getOutputStream());

        }
        catch (IOException exc) {
            System.err.println(exc.toString());
        }
    }
}
