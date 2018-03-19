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
package dev.tools.proxy_crawler.driver;

import org.openqa.selenium.WebDriver;

import dev.tools.proxy_crawler.exception.DriverInitializationException;

/**
 *	Abstract Driver class that must be extended by a specific driver class 
 * @author splait
 */
public abstract class Driver {
	
	private WebDriver driver;

	/**
	 * Default Constructor
	 */
	public Driver() throws DriverInitializationException{
		setDriver(createDriver());
	}
	
	abstract WebDriver createDriver() throws DriverInitializationException;
	
	public WebDriver getDriver() {
		return this.driver;
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
