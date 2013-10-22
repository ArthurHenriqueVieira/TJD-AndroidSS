package com.example.tyriangame;

public class Coordinates {
	private float _x;
	private float _y;

	public float getX() {
		return _x;
	}

	public void setX(float f) {
		_x = f;
	}

	public float getY() {
		return _y;
	}

	public void setY(float value) {
		_y = value;
	}

	public String toString() {
		return "Coordinates: (" + _x + "/" + _y + ")";
	}
}
