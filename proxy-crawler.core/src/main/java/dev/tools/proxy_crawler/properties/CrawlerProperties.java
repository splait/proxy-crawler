/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * 
 */
package dev.tools.proxy_crawler.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.log4j.Logger;

/**
 * Crawler properties
 * @author splait
 *
 */
public class CrawlerProperties {

	private static Logger LOG = Logger.getLogger(CrawlerProperties.class);
	private String PROPERTY_FILE = "crawler.properties";
	private static Properties propertiesLoaded;
	private static CrawlerProperties instance;
	
	/**
	 * Load properties file
	 * 
	 * @throws 
	 */
	private void loadPropertiesFile(){
		LOG.info(String.format("loading %s",this.PROPERTY_FILE)); 
		this.propertiesLoaded = new Properties();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(PROPERTY_FILE );
			this.propertiesLoaded.load(fileInputStream);
		} catch (FileNotFoundException e) {
			LOG.fatal(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (IOException ex) {
			LOG.fatal(ex.getMessage(), ex);
			throw new RuntimeException(ex);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					LOG.fatal(e.getMessage(), e);
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	/*
	 * @param propertyKey
	 * @return String
	 * 
	 * @throws Exception 
	 */
	public static String getProperty(final String propertyKey) throws PropertyException {
		if (instance == null) {
			instance = new CrawlerProperties();
		}
		try {
			if (propertiesLoaded == null) {
				instance.loadPropertiesFile();
			}
			if(propertyKey == null || "".equals(propertyKey)){
				return null;
			}
			return propertiesLoaded.getProperty(propertyKey);
		} catch (Exception e) {
			LOG.fatal(e.getMessage(), e);
			throw new PropertyException(e);
		}
	}
}
