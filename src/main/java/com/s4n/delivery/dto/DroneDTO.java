package com.s4n.delivery.dto;

import java.io.Serializable;

public class DroneDTO implements Serializable {

	private static final long serialVersionUID = 5790816956897041146L;
	private String name;
	private int x;
	private int y;
	private String direction;

	public DroneDTO() {

	}

	public DroneDTO(int x, int y, String direction) {

		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DroneDTO [name=" + name + ", x=" + x + ", y=" + y + ", direction=" + direction + "]";
	}
	
	
	
	

}
