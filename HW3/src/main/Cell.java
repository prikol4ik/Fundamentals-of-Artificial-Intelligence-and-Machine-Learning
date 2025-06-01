package main;

public enum Cell
{
	P1,
	P2,
	EMPTY,
	;

	public static String cellString(Cell c)
	{
		switch(c)
		{
			case P1:
				return "1";
			case P2:
				return "2";
			case EMPTY:
				return ".";
			default:
				throw new RuntimeException("Not mapped");
		}
	}

	public static String playerCellString(Cell c)
	{
		switch(c)
		{
			case P1:
				return "Player 1";
			case P2:
				return "Player 2";
			default:
				throw new RuntimeException("No such player");
		}
	}

};
