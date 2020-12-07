package com.s4n.delivery.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.s4n.delivery.dto.DroneDTO;
import com.s4n.delivery.exception.ServiceException;
import com.s4n.delivery.service.ReportGeneration;

@TestInstance(Lifecycle.PER_CLASS)
class DroneDeliveryReportGenerationImplTest {

	private ReportGeneration reports;
	private String droneName;

	@BeforeAll
	public void init() throws ServiceException, IOException {
		reports = new DroneDeliveryReportGenerationImpl();
		droneName = "droneForTesting.txt";

	}

	@Test
	void test() throws IOException {
		DroneDTO droneDTO = new DroneDTO();
		droneDTO.setName(droneName);
		List<String> finalDeliveryPositions = new LinkedList<>();
		finalDeliveryPositions.add("(-2,4,N)");
		finalDeliveryPositions.add("(-1,3,S)");
		finalDeliveryPositions.add("(0,0,W)");
		reports.generateReport(droneDTO, finalDeliveryPositions);
		List<String> allTheRoutes = Files.readAllLines(Paths.get("src", "main", "resources", droneDTO.getName()));
		assertEquals("(-2,4,N)", allTheRoutes.get(1));
	}

	@AfterAll
	public void cleanUp() throws IOException {
		Files.deleteIfExists(Paths.get("src", "main", "resources", droneName));
	}

}
