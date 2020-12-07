package com.s4n.delivery.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

import com.s4n.delivery.exception.ServiceException;
import com.s4n.delivery.service.DeliveryService;
import com.s4n.delivery.service.ReportGeneration;

@TestInstance(Lifecycle.PER_CLASS)
class DroneDeliveryServiceImplTest {

	private DeliveryService deliveryService;

	@BeforeAll
	public void init() throws ServiceException, IOException {
		ReportGeneration reportGeneration = mock(DroneDeliveryReportGenerationImpl.class);
		deliveryService = new DroneDeliveryServiceImpl(new DroneMovementsImpl(), reportGeneration);

		doNothing().when(reportGeneration).generateReport(Mockito.any(), Mockito.anyList());

	}

	@Test
	void test() throws ServiceException {
		List<String> allTheRoutes = new ArrayList<String>();
		allTheRoutes.add("AAAAIAAD");
		String response = deliveryService.executeDelivery(allTheRoutes);
		assertEquals("All lunches were delivered, successful operation", response);
	}

}
