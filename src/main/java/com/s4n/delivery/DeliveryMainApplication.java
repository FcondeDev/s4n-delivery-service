package com.s4n.delivery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import com.s4n.delivery.exception.ServiceException;
import com.s4n.delivery.service.DeliveryService;
import com.s4n.delivery.service.impl.DeliveryServiceImpl;
import com.s4n.delivery.service.impl.DroneMovementsImpl;
import com.s4n.delivery.service.impl.ReportGenerationImpl;

public class DeliveryMainApplication {
	static Logger logger = Logger.getLogger(DeliveryMainApplication.class.getName());

	public static void main(String[] args) throws ServiceException {

		DeliveryService deliveryService = new DeliveryServiceImpl(new DroneMovementsImpl(), new ReportGenerationImpl());
		try {
			
			List<String> allTheRoutes = Files.readAllLines(Paths.get("src","main","resources","routes.txt"));
			String responseCode = deliveryService.executeDelivery(allTheRoutes);
			logger.info(String.format("The delivery has been executed with code: %s", responseCode));
		} catch (IOException e) {
			throw new ServiceException(String.format("There was an error carrying out the delivery process %s", e));
		}

	}

}
