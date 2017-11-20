package MemoryGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.*;

public class EasyGame extends Application
{	
	private RadioButton[] button= new RadioButton[9];
	private RandImage[] image = new RandImage[9];
	private RandImage target = new RandImage();
	private ImageView itarget = new ImageView(target.getImage());
	private Image cover = new Image(getClass().getResourceAsStream("Question.jpg"), 50, 50, false, false);
	private Button submit = new Button("Submit");
	private Button gameSet = new Button("Set Game");
	private Button replay = new Button("Replay");
	private Button exit = new Button("Exit");
	private Label tally = new Label("");
	private double score = 0;
	private Label lscore = new Label("Score: " + score);
	private TextField gameNum = new TextField();
	private Button set = new Button("Set");
	private Label lnum = new Label("Number of Games: ");
	private int numOfGames = 10;
	private int totalGames = numOfGames;
	
	public void start(Stage primaryStage) throws InterruptedException
	{
		GridPane gridPane = new GridPane();
		gridPane.setHgap(5);
		gridPane.setVgap(5);
		gridPane.add(lscore, 0, 0);
		gridPane.add(new Label("Image: "), 2, 0);
		gridPane.add(itarget, 2, 1);
		gridPane.add(submit, 2, 5);
		gridPane.add(gameSet, 0, 5);
		gridPane.addRow(6,tally);
		GridPane.setColumnSpan(tally, GridPane.REMAINING);
		
		for(int i = 0; i < 9; i++)
		{
			image[i] = new RandImage();
		}
		for(int i = 0; i < 9; i++)
		{
			button[i] = new RadioButton();
			button[i].setGraphic(new ImageView(image[i].getImage()));
			button[i].setDisable(true);
		}
		int count = 0;
		for(int i = 2; i < 5; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				gridPane.add(button[count], j, i);
				count++;
			}
		}

		gridPane.setAlignment(Pos.CENTER);
		submit.setAlignment(Pos.CENTER);
		gameSet.setAlignment(Pos.CENTER);
		tally.setAlignment(Pos.CENTER);
		for(int i = 0; i < 9; i++)
		{
			button[i].setAlignment(Pos.BOTTOM_RIGHT);
		}
		GridPane.setHalignment(submit, HPos.RIGHT);
		GridPane.setHalignment(gameSet, HPos.LEFT);
		GridPane.setHalignment(tally, HPos.CENTER);
		
		submit.setOnAction(e -> submit(e));
		gameSet.setOnAction(e -> gameSet());
		
		Scene scene = new Scene(gridPane, 800, 600);
		primaryStage.setTitle("Memory Game");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(3000),
		        ae -> cover()));
		timeline.play();
	}
	
	private void submit(ActionEvent e)
	{
		numOfGames--;
		double addit = 1000;
		int selected = 0;
		int valid = 0;
		int correct = 0;
		
		for(int i = 0; i < 9; i++)
		{
			if(target.getNum() == image[i].getNum())
			{
				valid++;
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			if(button[i].isSelected())
			{
				selected++;
			}
		}
		
		if(valid != 0)
		{
			for(int i = 0; i < 9; i++)
			{
				if(button[i].isSelected() && target.getNum() == image[i].getNum())
				{
					correct++;
				}
				else if(button[i].isSelected() && target.getNum() != image[i].getNum())
				{
					addit = addit-(1000/valid);
				}
				else if(!button[i].isSelected() && target.getNum() == image[i].getNum())
				{
					addit = addit-(1000/valid);
				}
			}
		}
		else
		{
			if(selected != 0)
			{
				addit = 0;
			}
		}
		if(addit < 0)
		{
			addit = 0;
		}
		
		if(valid != 0)
		{
			tally.setText("Last Game: " + correct + " of " + valid + " Matches| Points Earned: " + addit);
		}
		else
		{
			tally.setText("Last Game: No Match| Points Earned: " + addit);
		}
		score = score + addit;
		lscore.setText("Score: " + score);
		
		if(numOfGames != 0)
		{
			target.reRandomize();
			itarget.setImage(target.getImage());

			for(int i = 0; i < 9; i++)
			{
				button[i].setSelected(false);
			}

			for(int i = 0; i < 9; i++)
			{
				image[i].reRandomize();
			}

			for(int i = 0; i < 9; i++)
			{
				button[i].setGraphic(new ImageView(image[i].getImage()));
				button[i].setDisable(true);
			}

			Timeline timeline = new Timeline(new KeyFrame(
					Duration.millis(3000),
					ae -> cover()));
			timeline.play();
		}
		else
		{
			final Stage endCard = new Stage();
			GridPane endPane = new GridPane();
			endPane.setHgap(5);
			endPane.setVgap(5);
			Label endLabel = new Label("You earned " + score + " out of " + (totalGames*1000) + " possible points.");
			endPane.add(replay, 0, 1);
			endPane.add(exit, 1, 1);
			endPane.add(endLabel, 0, 0);
			
			endPane.setAlignment(Pos.CENTER);
			endLabel.setAlignment(Pos.CENTER);
			replay.setAlignment(Pos.CENTER);
			exit.setAlignment(Pos.CENTER);
			
			replay.setOnAction(event -> replay(event));
			exit.setOnAction(event -> exit(event));
			
			((Node)(e.getSource())).getScene().getWindow().hide();
			
	        Scene endScene = new Scene(endPane, 400, 300);
			endCard.setTitle("Memory Game");
			endCard.setScene(endScene);
			endCard.show();
		}
	}
	
	private void replay(ActionEvent e)
	{
		final Stage newPrim = new Stage();
		numOfGames = 10;
		totalGames = numOfGames;
		score = 0;
		lscore.setText("Score: " + score);
		target.reRandomize();
		itarget.setImage(target.getImage());
		
		((Node)(e.getSource())).getScene().getWindow().hide();
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(5);
		gridPane.setVgap(5);
		gridPane.add(lscore, 0, 0);
		gridPane.add(new Label("Image: "), 2, 0);
		gridPane.add(itarget, 2, 1);
		gridPane.add(submit, 2, 5);
		gridPane.add(gameSet, 0, 5);
		gridPane.addRow(6,tally);
		GridPane.setColumnSpan(tally, GridPane.REMAINING);
		
		for(int i = 0; i < 9; i++)
		{
			image[i] = new RandImage();
		}
		for(int i = 0; i < 9; i++)
		{
			button[i] = new RadioButton();
			button[i].setGraphic(new ImageView(image[i].getImage()));
			button[i].setDisable(true);
		}
		int count = 0;
		for(int i = 2; i < 5; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				gridPane.add(button[count], j, i);
				count++;
			}
		}

		gridPane.setAlignment(Pos.CENTER);
		submit.setAlignment(Pos.CENTER);
		gameSet.setAlignment(Pos.CENTER);
		tally.setAlignment(Pos.CENTER);
		for(int i = 0; i < 9; i++)
		{
			button[i].setAlignment(Pos.BOTTOM_RIGHT);
		}
		GridPane.setHalignment(submit, HPos.RIGHT);
		GridPane.setHalignment(gameSet, HPos.LEFT);
		GridPane.setHalignment(tally, HPos.CENTER);
		
		submit.setOnAction(event -> submit(event));
		gameSet.setOnAction(event -> gameSet());
		
		Scene scene = new Scene(gridPane, 800, 600);
		newPrim.setTitle("Memory Game");
		newPrim.setScene(scene);
		newPrim.show();
		
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(3000),
		        ae -> cover()));
		timeline.play();
	}
	
	private void exit(ActionEvent e)
	{
		((Node)(e.getSource())).getScene().getWindow().hide();
	}
	
	private void gameSet()
	{
		final Stage setter = new Stage();
		GridPane setPane = new GridPane();
		setPane.setHgap(5);
		setPane.setVgap(5);
		setPane.add(lnum, 0, 0);
		setPane.add(gameNum, 1, 0);
		setPane.add(set, 1, 1);
		
		setPane.setAlignment(Pos.CENTER);
		set.setAlignment(Pos.CENTER);
		gameNum.setAlignment(Pos.CENTER);
		lnum.setAlignment(Pos.CENTER);
		GridPane.setHalignment(set, HPos.RIGHT);
		
		set.setOnAction(e -> set(e));
		
        Scene setScene = new Scene(setPane, 400, 300);
		setter.setTitle("Memory Game");
		setter.setScene(setScene);
		setter.show();
	}
	
	private void set(ActionEvent e)
	{
		if(gameNum.getText().isEmpty())
		{
			gameNum.setText("No Valid Value.");
		}
		else if(Integer.parseInt(gameNum.getText()) > 0)
		{
			numOfGames = Integer.parseInt(gameNum.getText());
			totalGames = numOfGames;
			((Node)(e.getSource())).getScene().getWindow().hide();
		}
		else
		{
			gameNum.setText("No Valid Value.");
		}
	}
	
	private void cover()
	{
		for(int i = 0; i < 9; i++)
		{
			button[i].setGraphic(new ImageView(cover));
			button[i].setDisable(false);
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
	
	//130 Minutes
}
