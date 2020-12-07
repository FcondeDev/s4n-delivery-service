package com.s4n.delivery.service.impl;

import static com.s4n.delivery.utils.Constants.REPORT_TITLE;

import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import com.s4n.delivery.config.DeliveryConfiguration;
import com.s4n.delivery.dto.DroneDTO;
import com.s4n.delivery.service.ReportGeneration;

public class DroneDeliveryReportGenerationImpl implements ReportGeneration {

	Logger logger = Logger.getLogger(DroneDeliveryReportGenerationImpl.class.getName());

	@Override
	public void generateReport(DroneDTO droneDTO, List<String> finalDeliveryPositions) {

		try (PrintWriter writer = new PrintWriter(
				DeliveryConfiguration.getProperty("reportsRoute") + droneDTO.getName(), "UTF-8")) {
			writer.println(REPORT_TITLE);
			for (String finalPosition : finalDeliveryPositions) {
				writer.println(finalPosition);
			}

		} catch (Exception e) {
			logger.info(String.format("There was an error creating the report for the drone: %s , error : %s",
					droneDTO.getName(), e));
		}

	}

}
