package MemoryGame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import MemoryGame.EasyGame;
import MemoryGame.MediumGame;
import MemoryGame.HardGame;
import MemoryGame.MultiEasyGame;
import MemoryGame.MultiMediumGame;
import MemoryGame.MultiHardGame;

public class MemoryGameClient extends Application {

    private DataOutputStream toServer;
    private DataInputStream fromServer;
    private Label selectionLabel = new Label("Please select a difficulty");
    private Label singlePlayer = new Label("Single Player");
    private Label multiPlayer = new Label("Multi-Player");
    private ToggleGroup difficultyRadios = new ToggleGroup();
    private RadioButton singleEasy = new RadioButton("Easy");
    private RadioButton singleMedium = new RadioButton("Medium");
    private RadioButton singleHard = new RadioButton("Hard");
    private RadioButton multEasy = new RadioButton("Easy");
    private RadioButton multMedium = new RadioButton("Medium");
    private RadioButton multHard = new RadioButton("Hard");
    private Button startGame = new Button("Start Game");

    public void start(Stage t) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        singleEasy.setId("singleEasy");
        singleMedium.setId("singleMedium");
        singleHard.setId("singleHard");
        multEasy.setId("multEasy");
        multMedium.setId("multMedium");
        multHard.setId("multHard");

        singleEasy.setToggleGroup(difficultyRadios);
        singleMedium.setToggleGroup(difficultyRadios);
        singleHard.setToggleGroup(difficultyRadios);
        multEasy.setToggleGroup(difficultyRadios);
        multMedium.setToggleGroup(difficultyRadios);
        multHard.setToggleGroup(difficultyRadios);
        singleEasy.setSelected(true);

        gridPane.add(selectionLabel, 0, 0);
        GridPane.setColumnSpan(selectionLabel, GridPane.REMAINING);
        gridPane.add(singlePlayer, 0, 1);
        GridPane.setColumnSpan(singlePlayer, GridPane.REMAINING);
        gridPane.add(singleEasy, 0, 2);
        gridPane.add(singleMedium, 1, 2);
        gridPane.add(singleHard, 2, 2);
        gridPane.add(multiPlayer, 0, 3);
        GridPane.setColumnSpan(multiPlayer, GridPane.REMAINING);
        gridPane.add(multEasy, 0, 4);
        gridPane.add(multMedium, 1, 4);
        gridPane.add(multHard, 2, 4);
        gridPane.add(startGame, 1, 5);

        gridPane.setAlignment(Pos.CENTER);
        startGame.setOnAction(e -> submit(e));

        Scene difficultyScene = new Scene(gridPane, 300, 200);
        t.setTitle("Difficulty Selection");
        t.setScene(difficultyScene);
        t.show();
    }

    private void submit(ActionEvent e) {

        Toggle selected = difficultyRadios.getSelectedToggle();
        String difficulty = ((RadioButton) selected).getId();
        Stage gameStage = new Stage();

        //Run correct game version

        if(difficulty.equals("singleEasy"))
        {
        	EasyGame easy = new EasyGame();
            try
			{
				easy.start(gameStage);
			}
			catch (InterruptedException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        else if(difficulty.equals("singleMedium"))
        {
            	MediumGame medium = new MediumGame();
            	try
				{
					medium.start(gameStage);
				}
				catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        }
        else if(difficulty.equals("singleHard"))
        {
            	HardGame hard = new HardGame();
            	try
				{
					hard.start(gameStage);
				}
				catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        }
        else if(difficulty.equals("multEasy"))
        {
        	MultiEasyGame easy = new MultiEasyGame();
            try
			{
				easy.start(gameStage);
				easy.submit.setOnAction(ev -> getEasyScore(ev, easy));
			}
			catch (InterruptedException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        else if(difficulty.equals("multMedium"))
        {
        	MultiMediumGame medium = new MultiMediumGame();
        	try
			{
				medium.start(gameStage);
				medium.submit.setOnAction(ev -> getMediumScore(ev, medium));
			}
			catch (InterruptedException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        else if(difficulty.equals("multHard"))
        {
        	MultiHardGame hard = new MultiHardGame();
        	try
			{
				hard.start(gameStage);
				hard.submit.setOnAction(ev -> getHardScore(ev, hard));
			}
			catch (InterruptedException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }

    }
    
    private void getEasyScore(ActionEvent e,MultiEasyGame easy)
    {
    	try {
            Socket socket = new Socket("localhost", 8000);

            toServer = new DataOutputStream(socket.getOutputStream());
            fromServer = new DataInputStream(socket.getInputStream());
            
            toServer.writeDouble(easy.score);
            double opponScore = fromServer.readDouble();
        }
        catch (IOException exc) {
            System.err.println(exc.toString());
        }
    }
    
    private void getMediumScore(ActionEvent e,MultiMediumGame medium)
    {
    	try {
            Socket socket = new Socket("localhost", 8000);

            toServer = new DataOutputStream(socket.getOutputStream());
            fromServer = new DataInputStream(socket.getInputStream());

            toServer.writeDouble(medium.score);
            double opponScore = fromServer.readDouble();
        }
        catch (IOException exc) {
            System.err.println(exc.toString());
        }
    }
    
    private void getHardScore(ActionEvent e,MultiHardGame hard)
    {
    	try {
            Socket socket = new Socket("localhost", 8000);

            toServer = new DataOutputStream(socket.getOutputStream());
            fromServer = new DataInputStream(socket.getInputStream());

            toServer.writeDouble(hard.score);
            double opponScore = fromServer.readDouble();
        }
        catch (IOException exc) {
            System.err.println(exc.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}


