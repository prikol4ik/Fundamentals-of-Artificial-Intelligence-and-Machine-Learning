package main;

/**
 * Base class for players.
 *
 * You want to extend this class with your own and implement the only abstract method.
 * You can use getCellType() to figure out which player you are supposed to play as (P1 or P2).
 */
public abstract class Player
{
	private Cell cellType;
	public abstract int decideTurn(Board b);

	public void setCellType(final Cell c)
	{
		this.cellType = c;
	}

	public Cell getCellType()
	{
		return this.cellType;
	}
}
