package main;

public class GameMain
{
	public static void main(String[] args)
	{
		// You can edit the players here to play against your code
		// or you can set two variations to play against each other (e.g one generates more games than the other)
		Game g = new Game(new MonteCarlo(), new HumanPlayer());

		Cell winner = g.play();
		System.out.println(Cell.playerCellString(winner) + " won");
		System.out.println(g.getBoard());
	}
}
