package com.s4n.delivery.service;

import java.util.List;

import com.s4n.delivery.exception.ServiceException;

public interface DeliveryService {
	
	public String executeDelivery(List<String> allTheRoutes) throws ServiceException;

}
