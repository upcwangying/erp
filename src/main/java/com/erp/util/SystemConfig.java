package com.erp.util;

import com.erp.entity.Project;
import com.erp.exception.ServiceException;
import com.erp.service.ProjectService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SystemConfig {
	private static Logger logger = Logger.getLogger(SystemConfig.class);
	private static Map<String, String> configs = new HashMap<String, String>();
	private static Map<Integer, Project> projectMaps = new HashMap<Integer, Project>();

	public static void init() {
		InputStream in = null;
		try {
			logger.info("--------The system config begin init---------");
			Properties properties = new Properties();
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream("system-config.properties");
			properties.load(in);
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				logger.info(entry.getKey() + "=" + entry.getValue());
				configs.put((String) entry.getKey(), (String) entry.getValue());
			}
			properties.clear();

			logger.info("--------The system config init success---------");
		} catch (IOException e) {
			logger.error("--------The system config init failure:" + e.getMessage() + "---------");
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			logger.info("########The Project begin init########");
			List<Project> projects = ProjectService.initProjects(null);
			if (projects != null) {
				for (Project project : projects) {
					projectMaps.put(project.getProjectId(), project);
				}
			}
			logger.info("########The Project init success########");
		} catch (ServiceException e) {
			logger.error("########The Project init failure:" + e.getMessage() + "########");
			e.printStackTrace();
		}

	}

	public static String getValue(String key , String def) {
		String v = configs.get(key);
		try {
			if (v == null) {
				logger.warn("no value in system-config.properties for key '" + key + "', use default:" + def);
				return def;
			}
			return v;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return def;
		}
	}

	public static String getValue(String key) {
		return getValue(key, "");
	}

	public static boolean getBooleanValue(String key, boolean def) {
		String v = configs.get(key);
		try {
			if (v == null) {
				logger.warn("no value in system-config.properties for key '" + key + "', use default:" + def);
				return def;
			}
			return Boolean.valueOf(v);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return def;
		}
	}

	public static boolean getBooleanValue(String key) {
		return getBooleanValue(key, false);
	}

	public static int getIntegerValue(String key, int def) {
		String v = configs.get(key);
		try {
			if (v == null) {
				logger.warn("no value in system-config.properties for key '" + key + "', use default:" + def);
				return def;
			}
			return Integer.parseInt(v);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return def;
		}
	}

	public static int getIntegerValue(String key) {
		return getIntegerValue(key, 0);
	}

	public static float getFloatValue(String key, float def) {
		String v = configs.get(key);
		try {
			if (v == null) {
				logger.warn("no value in system-config.properties for key '" + key + "',use default:" + def);
				return def;
			}
			return Float.parseFloat(v);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return def;
		}
	}

	public static float getFloatValue(String key) {
		return getFloatValue(key, 0);
	}

	public static long getLongValue(String key, long def) {
		String v = configs.get(key);
		try {
			if (v == null) {
				logger.warn("no value in system-config.properties for key '" + key + "',use default:" + def);
				return def;
			}
			return Long.parseLong(v);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return def;
		}
	}


	public static long getLongValue(String key) {
		return getLongValue(key, 0);
	}

	public static Project getProjectById (int id) {
		Project project = projectMaps.get(id);
		if (project == null) {
			project = new Project();
			projectMaps.put(id, project);
		}
		return project;
	}

}
