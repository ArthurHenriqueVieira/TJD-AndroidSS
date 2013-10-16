package com.example.tyriangame;

public class Coordinates {
	private int _x;
	private int _y;

	public int getX() {
		return _x;
	}

	public void setX(int value) {
		_x = value;
	}

	public int getY() {
		return _y;
	}

	public void setY(int value) {
		_y = value;
	}

	public String toString() {
		return "Coordinates: (" + _x + "/" + _y + ")";
	}
}
