package MemoryGame;

import java.util.Random;
import javafx.scene.image.Image;

public class RandImage
{
	protected int num;
	Random rand = new Random();
	protected Image img;
	
	public RandImage()
	{
		num = rand.nextInt(4)+1;
		if(num == 1)
		{
			img = new Image(getClass().getResourceAsStream("Club.jpg"), 50, 50, false, false);
		}
		else if(num == 2)
		{
			img = new Image(getClass().getResourceAsStream("Diamond.jpg"), 50, 50, false, false);
		}
		else if(num == 3)
		{
			img = new Image(getClass().getResourceAsStream("Heart.jpg"), 50, 50, false, false);
		}
		else
		{
			img = new Image(getClass().getResourceAsStream("Spade.jpg"), 50, 50, false, false);
		}
	}
	
	public void reRandomize()
	{
		num = rand.nextInt(4)+1;
		if(num == 1)
		{
			img = new Image(getClass().getResourceAsStream("Club.jpg"), 50, 50, false, false);
		}
		else if(num == 2)
		{
			img = new Image(getClass().getResourceAsStream("Diamond.jpg"), 50, 50, false, false);
		}
		else if(num == 3)
		{
			img = new Image(getClass().getResourceAsStream("Heart.jpg"), 50, 50, false, false);
		}
		else
		{
			img = new Image(getClass().getResourceAsStream("Spade.jpg"), 50, 50, false, false);
		}
	}
	
	public int getNum()
	{
		return num;
	}
	
	public Image getImage()
	{
		return img;
	}
	
	//8 minutes total
}
