package com.s4n.delivery.service.impl;

import static com.s4n.delivery.utils.Constants.EAST_DIRECTION;
import static com.s4n.delivery.utils.Constants.NORTH_DIRECTION;
import static com.s4n.delivery.utils.Constants.SOUTH_DIRECTION;
import static com.s4n.delivery.utils.Constants.WEST_DIRECTION;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.s4n.delivery.config.DeliveryConfiguration;
import com.s4n.delivery.dto.DroneDTO;
import com.s4n.delivery.exception.ServiceException;
import com.s4n.delivery.service.Movements;

public class DroneMovementsImpl implements Movements {

	Logger logger = Logger.getLogger(DroneMovementsImpl.class.getName());

	public boolean moveForward(DroneDTO droneDTO) throws ServiceException {
		try {

			boolean isAnAvailableMovement = checkDistanceConstrain(droneDTO);

			if (isAnAvailableMovement) {
				if (droneDTO.getDirection().equals(NORTH_DIRECTION)) {
					droneDTO.setY(droneDTO.getY() + 1);
				} else if (droneDTO.getDirection().equals(SOUTH_DIRECTION)) {
					droneDTO.setY(droneDTO.getY() - 1);
				} else if (droneDTO.getDirection().equals(WEST_DIRECTION)) {
					droneDTO.setX(droneDTO.getX() - 1);
				} else if (droneDTO.getDirection().equals(EAST_DIRECTION)) {
					droneDTO.setX(droneDTO.getX() + 1);
				} else {
					logger.log(Level.WARNING, "The provided direction is not allowed {}", droneDTO.getDirection());
				}
			} else {
				logger.log(Level.WARNING,
						"The drone is out of the delivery area, the delivery failed, the drone is not going to do this delivery, please see the report for more information");
			}
			return isAnAvailableMovement;
		} catch (Exception e) {
			throw new ServiceException(String.format("There is an error moving forward %s", e));
		}

	}

	public void moveLeft(DroneDTO droneDTO) throws ServiceException {

		try {
			Map<String, String> leftMovements = new HashMap<>();
			leftMovements.put("N", "W");
			leftMovements.put("W", "S");
			leftMovements.put("S", "E");
			leftMovements.put("E", "N");
			if (leftMovements.containsKey(droneDTO.getDirection())) {
				droneDTO.setDirection(leftMovements.get(droneDTO.getDirection()));
			} else {
				logger.log(Level.WARNING, "The provided direction is not allowed {}", droneDTO.getDirection());
			}

		} catch (Exception e) {
			throw new ServiceException(String.format("There is an error moving left  %s", e));
		}

	}

	public void moveRight(DroneDTO droneDTO) throws ServiceException {
		try {
			Map<String, String> rightMovements = new HashMap<>();
			rightMovements.put("N", "E");
			rightMovements.put("E", "S");
			rightMovements.put("S", "W");
			rightMovements.put("W", "N");
			if (rightMovements.containsKey(droneDTO.getDirection())) {
				droneDTO.setDirection(rightMovements.get(droneDTO.getDirection()));
			} else {
				logger.log(Level.WARNING, "Direction: {} is not allowed", droneDTO.getDirection());
			}

		} catch (Exception e) {
			throw new ServiceException(String.format("There is an error moving right  %s", e));
		}

	}

	private boolean checkDistanceConstrain(DroneDTO droneDTO) throws ServiceException {
		int maxDistanceTosendADelivery = DeliveryConfiguration.getProperty("maxDistanceTosendADelivery");
		return Math.sqrt(Math.pow(droneDTO.getX(), 2) + Math.pow(droneDTO.getY(), 2)) <= maxDistanceTosendADelivery;
	}

}
