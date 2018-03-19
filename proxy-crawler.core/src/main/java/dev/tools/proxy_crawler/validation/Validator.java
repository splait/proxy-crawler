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
package dev.tools.proxy_crawler.validation;

import java.io.IOException;
import java.net.ConnectException;

import javax.xml.bind.PropertyException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import dev.tools.proxy_crawler.data.Proxy;
import dev.tools.proxy_crawler.properties.CrawlerProperties;

/**
 * Verification process of proxy server availability
 * 
 * @author splait
 *
 */
@SuppressWarnings("deprecation")
@Component
public class Validator {

	private static Logger LOG = Logger.getLogger(Validator.class);

	boolean validate(final Proxy proxy) throws IOException, PropertyException {
		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();
		String hostValidator = CrawlerProperties.getProperty("validator.http.host");
		HttpGet get = new HttpGet(hostValidator);
		HttpHost proxyHost = new HttpHost(proxy.getHost(), proxy.getPort(), proxy.getScheme().getScheme());
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHost);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Integer.parseInt(CrawlerProperties.getProperty("validator.http.host.timeout")));
		try {
			HttpResponse response = client.execute(get);
			if (response != null) {
				StatusLine status = response.getStatusLine();
				if (response.getStatusLine().getStatusCode() == 200) {
					LOG.info(String.format("proxy server [%s:%d] could reach (%s) [%s]", proxy.getHost(), proxy.getPort(), hostValidator, status));
					return true;
				} else {
					LOG.info(String.format("proxy server [%s:%d] could'not reach (%s) [%s]", proxy.getHost(), proxy.getPort(), hostValidator, status));
				}
			}
		} catch (ConnectException e) {
			LOG.fatal(e.getMessage());
		}
		return false;
	}
}
