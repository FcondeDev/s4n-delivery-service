package com.s4n.delivery.service;

import com.s4n.delivery.dto.DroneDTO;
import com.s4n.delivery.exception.ServiceException;

public interface Movements {
	
	public boolean moveForward(DroneDTO droneDTO) throws ServiceException;
	
	public void moveLeft(DroneDTO droneDTO) throws ServiceException;
	
	public void moveRight(DroneDTO droneDTO) throws ServiceException;

}
