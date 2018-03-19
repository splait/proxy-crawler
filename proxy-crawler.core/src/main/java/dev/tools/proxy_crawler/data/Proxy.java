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

package dev.tools.proxy_crawler.data;

/**
 * Proxy representation
 * 
 * @author splait
 *
 */

public class Proxy {

	/**
	 * Proxy host address
	 */
	private String host;

	/**
	 * Proxy port
	 */
	private int port;

	private HypertextProtocol scheme;

	/**
	 * 
	 */
	public Proxy(final String host, final int port, final HypertextProtocol scheme) {
		setHost(host);
		setPort(port);
		setScheme(scheme);
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(final String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(final int port) {
		this.port = port;
	}

	/**
	 * @return the scheme
	 */
	public HypertextProtocol getScheme() {
		return scheme;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s:%d [%s]", this.getHost(), this.getPort(), this.getScheme());
	}
	/**
	 * @param scheme the scheme to set
	 */
	public void setScheme(HypertextProtocol scheme) {
		this.scheme = scheme;
	}

	public static enum HypertextProtocol {

		HTTP("http"), HTTPS("https");

		private String scheme;
		
		private HypertextProtocol(String scheme) {
			this.scheme = scheme;
		}
		
		public String getScheme() {
			return this.scheme;
		}
	}
}
