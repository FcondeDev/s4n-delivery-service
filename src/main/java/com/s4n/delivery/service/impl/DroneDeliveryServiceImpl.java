package com.s4n.delivery.service.impl;

import static com.s4n.delivery.utils.Constants.MOVING_FORWARD;
import static com.s4n.delivery.utils.Constants.MOVING_LEFT;
import static com.s4n.delivery.utils.Constants.MOVING_RIGHT;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.s4n.delivery.config.DeliveryConfiguration;
import com.s4n.delivery.dto.DroneDTO;
import com.s4n.delivery.exception.ServiceException;
import com.s4n.delivery.service.DeliveryService;
import com.s4n.delivery.service.Movements;
import com.s4n.delivery.service.ReportGeneration;

public class DroneDeliveryServiceImpl implements DeliveryService {

	Logger logger = Logger.getLogger(DroneDeliveryServiceImpl.class.getName());

	private Movements movements;
	private ReportGeneration reportGeneration;

	public DroneDeliveryServiceImpl(Movements movements, ReportGeneration reportGeneration) {
		super();
		this.movements = movements;
		this.reportGeneration = reportGeneration;
	}

	public String executeDelivery(List<String> allTheRoutes) throws ServiceException {
		int maxNumberOfDeliveriesPerDrone = DeliveryConfiguration.getProperty("maxNumberOfDeliveriesPerDrone");
		int maxNumberOfDronesAvailable = DeliveryConfiguration.getProperty("maxNumberOfDronesAvailable");

		int numberOfDronesDeployed = 1;
		int deliveryPerDroneCount = 0;
		int numberofDeliveriesDone = 0;
		List<String> finalDeliveryPositions = new LinkedList<>();
		int numberOfDeliveries = allTheRoutes.size();
		int numberOfDeliveriesLeft = numberOfDeliveries % maxNumberOfDeliveriesPerDrone;

		logger.info(String.format("The number of deliveries is : %s", numberOfDeliveries));
		DroneDTO droneDTO = new DroneDTO(0, 0, "N");
		droneDTO.setName("in01.txt");

		for (String route : allTheRoutes) {
			logger.info(String.format("The Executing route : %s", route));
			executeMovements(droneDTO, route);
			deliveryPerDroneCount = deliveryPerDroneCount + 1;
			positionBuilder(droneDTO, finalDeliveryPositions);
			logger.info(String.format("Drone position after this route : %s", droneDTO.toString()));
			// if the max of deliveries per drone is reached, we have to initialize the
			// variables again, we have to check if there are some deliveries left too
			if (deliveryPerDroneCount == maxNumberOfDeliveriesPerDrone
					|| numberOfDeliveries - numberofDeliveriesDone == numberOfDeliveriesLeft) {
				logger.info(String.format("Generating report for drone : %s", droneDTO.getName()));
				reportGeneration.generateReport(droneDTO, finalDeliveryPositions);
				finalDeliveryPositions = new LinkedList<>();
				numberOfDronesDeployed = numberOfDronesDeployed + 1;
				droneDTO = new DroneDTO(0, 0, "N");
				String numberOfDrone = numberOfDronesDeployed < 10 ? "0" + numberOfDronesDeployed
						: String.valueOf(numberOfDronesDeployed);
				droneDTO.setName("in" + numberOfDrone + ".txt");
				deliveryPerDroneCount = 0;
			}

			if (numberOfDronesDeployed == maxNumberOfDronesAvailable) {
				logger.info(
						"There is not more drones available for delivering, the operation is closed. Please see the reports for more information");
				return "Exit code, No drones available, Please check the reports to track each drone";
			}
			numberofDeliveriesDone = numberofDeliveriesDone + 1;
		}

		return "All lunches were delivered, successful operation";

	}

	private void positionBuilder(DroneDTO droneDTO, List<String> finalDeliveryPositions) {
		finalDeliveryPositions.add("(" + droneDTO.getX() + "," + droneDTO.getY() + "," + droneDTO.getDirection() + ")");
	}

	private void executeMovements(DroneDTO droneDTO, String route) throws ServiceException {
		for (int i = 0; i < route.length(); i++) {
			if (MOVING_FORWARD == route.charAt(i)) {
				boolean nextMovementeAllowed = movements.moveForward(droneDTO);

				if (!nextMovementeAllowed) {
					droneDTO.setDirection("N");
					droneDTO.setX(0);
					droneDTO.setY(0);
					break;
				}

			} else if (MOVING_LEFT == route.charAt(i)) {
				movements.moveLeft(droneDTO);
			} else if (MOVING_RIGHT == route.charAt(i)) {
				movements.moveRight(droneDTO);
			} else {
				logger.log(Level.WARNING,
						"The movement is not allowed,the drone will ignore it and the delivery will continue normally {}",
						droneDTO.getDirection());
			}
		}
	}

}
