package main.core;

import java.io.Serializable;
public class Score implements Comparable<Score>, Serializable {
	
	private static final long serialVersionUID = -6220082138832090333L;
	private double ratio;
	private int time, rows, columns, mines;
	private String name;
	
	/** Constructor for Score
	 * @param rows The number of rows of tiles
	 * @param columns The number of columns of tiles
	 * @param mines The number of mines
	 * @param time The amount of time it took to win
	 * @param name The name of the player who won
	 */
	public Score(int rows, int columns, int mines, int time, String name) {
		
		ratio = ((double) mines) / (rows * columns);
		this.time = time;
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		this.name = name;
	}

	@Override
	public int compareTo(Score other) {
		
		if (ratio < other.getRatio())
			return -1;
		else if (ratio > other.getRatio())
			return 1;
		else if (ratio == other.getRatio())
			if (time > other.getTime())
				return 1;
			else if (time < other.getTime())
				return -1;
		return 0;
	}
	

	@Override
	public String toString() {
		
		if (time > 60) {
			return String.format("%-12s%d x %d with %d mines in %d minutes and %d seconds", name, rows, columns, mines, time / 60, time % 60);
		}
		return String.format("%-12s%d x %d with %d mines in %d seconds", name, rows, columns, mines, time);
	}


	public double getRatio() {
		return ratio;
	}


	public int getTime() {
		return time;
	}

}
