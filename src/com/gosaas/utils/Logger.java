package com.gosaas.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class Logger {

	private org.apache.log4j.Logger logger = null;
	
	public Logger(String loggerName, String path) {
		Map<String, String> map = new HashMap<>();
		map.put("log4j.category." + loggerName, "DEBUG, " + loggerName);
		map.put("log4j.additivity." + loggerName, "false");
		
		File file = new File(path);
		if(file != null && file.exists() && file.isDirectory()) {
			map.put("log4j.appender." + loggerName, "org.apache.log4j.RollingFileAppender");
			map.put("log4j.appender." + loggerName + ".File", "" + path+"/logs/" + loggerName + ".log");
			//map.put("log4j.appender." + loggerName + ".File", "C:\\logs\\" + loggerName + ".log");
			map.put("log4j.appender." + loggerName + ".MaxFileSize", "25MB");
			map.put("log4j.appender." + loggerName + ".MaxBackupIndex", "3");
			map.put("log4j.appender." + loggerName + ".layout", "org.apache.log4j.PatternLayout");
			map.put("log4j.appender." + loggerName + ".layout.ConversionPattern", "%d %-5p - %m%n");
		} else {
			map.put("log4j.appender." + loggerName, "org.apache.log4j.ConsoleAppender");
			map.put("log4j.appender." + loggerName + ".Target", "System.out");
			map.put("log4j.appender." + loggerName + ".layout", "org.apache.log4j.PatternLayout");
			map.put("log4j.appender." + loggerName + ".layout.ConversionPattern", "%d %-5p - %m%n");
		}
		
		Properties properties = new Properties();
		properties.putAll(map);
		
		PropertyConfigurator.configure(properties);
		logger = org.apache.log4j.Logger.getLogger(loggerName);
	}
	
	protected Logger(org.apache.log4j.Logger logger) {
		this.logger = logger;
	}
	
	public void info(String msg) {
		logger.info(msg);
	}
	
	public void debug(String msg) {
		logger.debug(msg);
	}
	
	public void error(String msg) {
		logger.error(msg);
	}
	
	public void error(String msg, Throwable th) {
		logger.error(msg, th);
	}
}
