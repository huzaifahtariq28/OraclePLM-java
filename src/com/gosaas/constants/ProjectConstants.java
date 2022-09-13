package com.gosaas.constants;

import java.util.Enumeration;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ProjectConstants {
	public static final String CLOUD_URL;
	public static final String CLOUD_QUERY;
	public static final String CLOUD_USERNAME;
	public static final String CLOUD_PASSWORD;
	
	public static final String DB_URL;
	public static final String DB_USERNAME;
	public static final String DB_PASSWORD;
	
	public static final String LOGS_PATH;
	
	static {
		ResourceBundle bundle = null;
		try {
			bundle = PropertyResourceBundle.getBundle("com.gosaas.constants.config");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		CLOUD_URL = bundle.getString("cloud.url");
		CLOUD_QUERY = bundle.getString("cloud.query");
		CLOUD_USERNAME = bundle.getString("cloud.username");
		CLOUD_PASSWORD = bundle.getString("cloud.password");
		
		DB_URL = bundle.getString("database.url");
		DB_USERNAME = bundle.getString("database.username");
		DB_PASSWORD = bundle.getString("database.password");
		
		LOGS_PATH = bundle.getString("logs.path");
		
	}

	public static Properties convertResourceBundleToProperties() {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("com.gosaas.cloud.apps.roche.constants.ChangeAudit");
		Properties properties = new Properties();
		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			properties.put(key, bundle.getString(key));
		}
		return properties;
	}
}
