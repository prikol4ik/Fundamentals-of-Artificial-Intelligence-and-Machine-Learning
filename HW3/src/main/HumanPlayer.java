package main;

import java.io.*;

/**
 * Implementation of a human player (input via standard input).
 *
 * (Note: You probably want to leave this class intact to play against your code. Create another class that extends Player and put your solution there)
 */
public class HumanPlayer extends Player
{
	@Override
	public int decideTurn(Board b)
	{
		int choice = -1;
		while (!b.getAvailableColumns().contains(choice))
		{
			System.out.println("Please select one of: " + b.getAvailableColumns());
			choice = this.getUserInput();
		}
		return choice;
	}

	private int getUserInput()
	{
		while (true)
		{
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				return Integer.parseInt(br.readLine());
			}
			catch (NumberFormatException e)
			{
				System.out.println("Please enter a number!");
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
};
