package com.s4n.delivery.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.s4n.delivery.config.DeliveryConfiguration;
import com.s4n.delivery.dto.DroneDTO;
import com.s4n.delivery.exception.ServiceException;
import com.s4n.delivery.service.Movements;

@TestInstance(Lifecycle.PER_CLASS)
class DroneMovementsImplTest {

	private Movements movement;
	private int maxDistanceTosendADelivery;

	@BeforeAll
	public void init() throws ServiceException {
		movement = new DroneMovementsImpl();
		maxDistanceTosendADelivery = Integer.parseInt(DeliveryConfiguration.getProperty("maxDistanceTosendADelivery"));
	}

	@Test
	void movementConstrainTest() throws ServiceException {

		DroneDTO droneDTO = new DroneDTO(0, maxDistanceTosendADelivery, "N");
		boolean isAnAvailableMovement = movement.moveForward(droneDTO);
		assertTrue(!isAnAvailableMovement);
		droneDTO = new DroneDTO(maxDistanceTosendADelivery, 0, "E");
		isAnAvailableMovement = movement.moveForward(droneDTO);
		assertTrue(!isAnAvailableMovement);

	}

	@Test
	void eastMovementTest() throws ServiceException {
		DroneDTO droneDTO = new DroneDTO(0, 0, "N");
		movement.moveRight(droneDTO);
		movement.moveForward(droneDTO);
		assertEquals(0, droneDTO.getY());
		assertEquals(1, droneDTO.getX());
		assertEquals("E", droneDTO.getDirection());

	}

	@Test
	void WestMovementTest() throws ServiceException {
		DroneDTO droneDTO = new DroneDTO(0, 0, "N");
		movement.moveLeft(droneDTO);
		movement.moveForward(droneDTO);
		assertEquals(0, droneDTO.getY());
		assertEquals(-1, droneDTO.getX());
		assertEquals("W", droneDTO.getDirection());

	}

	@Test
	void northMovementTest() throws ServiceException {
		DroneDTO droneDTO = new DroneDTO(0, 0, "N");
		movement.moveForward(droneDTO);
		assertEquals(1, droneDTO.getY());
		assertEquals(0, droneDTO.getX());
		assertEquals("N", droneDTO.getDirection());

	}

	@Test
	void southMovementTest() throws ServiceException {
		DroneDTO droneDTO = new DroneDTO(0, 0, "N");
		movement.moveLeft(droneDTO);
		movement.moveLeft(droneDTO);
		movement.moveForward(droneDTO);
		assertEquals(-1, droneDTO.getY());
		assertEquals(0, droneDTO.getX());
		assertEquals("S", droneDTO.getDirection());

	}
}
