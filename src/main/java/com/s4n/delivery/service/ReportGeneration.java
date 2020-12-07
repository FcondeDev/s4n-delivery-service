package com.s4n.delivery.service;

import java.util.List;

import com.s4n.delivery.dto.DroneDTO;

public interface ReportGeneration {

	public void generateReport(DroneDTO droneDTO, List<String> finalDeliveryPositions);

}
