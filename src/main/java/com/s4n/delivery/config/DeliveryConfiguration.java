package com.s4n.delivery.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.s4n.delivery.exception.ServiceException;

public class DeliveryConfiguration {

	private DeliveryConfiguration() {
	}

	public static int getProperty(String property) throws ServiceException {

		try (InputStream input = new FileInputStream("src/main/resources/application-default.properties")) {

			Properties properties = new Properties();
			properties.load(input);
			return Integer.parseInt((String) properties.get(property));
		} catch (IOException e) {
			throw new ServiceException(String.format("There was an error loading default properties %s", e));

		}

	}

}
