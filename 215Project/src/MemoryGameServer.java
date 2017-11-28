import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class MemoryGameServer extends Application {

    public void start(Stage t) {
        TextArea logArea = new TextArea();
        ScrollPane scrollPane = new ScrollPane(logArea);

        Scene serverScene = new Scene(scrollPane, 600, 300);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        t.setTitle("Memory Server");
        t.setScene(serverScene);
        t.show();

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            logArea.appendText(new Date() + ": Server started at socket 8000\n");

            int sessionNum = 1;

            while (true) {

                try {
                    logArea.appendText(new Date() + ": Waiting for player to join game #" + sessionNum + "\n");
                    serverSocket.setSoTimeout(1);
                    Socket player = serverSocket.accept();

                    logArea.appendText(new Date() + ": Player One has joined game #" + sessionNum + "\n");
                    logArea.appendText("Player's IP Address is: " + player.getInetAddress().getHostAddress() + "\n");

                    new DataOutputStream(player.getOutputStream()).writeInt(1);

                    logArea.appendText(new Date() + ": Starting a thread for game #" + sessionNum++ + "\n");

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
        launch(args);
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
