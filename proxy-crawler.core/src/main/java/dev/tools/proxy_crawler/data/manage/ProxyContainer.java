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
package dev.tools.proxy_crawler.data.manage;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import dev.tools.proxy_crawler.data.Proxy;
import dev.tools.proxy_crawler.exception.NoProxyAvailableException;
import dev.tools.proxy_crawler.exception.ProxyServerAlreadyAddedException;

/**
 * @author splait
 *
 */
@Component
public class ProxyContainer {

	private List<Proxy> proxyList;
	private List<Proxy> useadProxyServersList;

	private static Logger LOG = Logger.getLogger(ProxyContainer.class);

	public ProxyContainer() {
		this.proxyList = new ArrayList<>();
		this.useadProxyServersList = new ArrayList<>();
	}

	public void addProxy(Proxy proxy) throws ProxyServerAlreadyAddedException {
		synchronized (proxyList) {
			LOG.info(String.format("Trying to add %s:%d [%s] to list", proxy.getHost(), proxy.getPort(),
					proxy.getScheme()));
			verifyIfProxyWasAdded(proxyList, proxy);
			synchronized (useadProxyServersList) {
				verifyIfProxyWasAdded(useadProxyServersList, proxy);
			}
			this.proxyList.add(proxy);
		}
	}

	private void verifyIfProxyWasAdded(List<Proxy> proxyList, Proxy proxy) throws ProxyServerAlreadyAddedException {
		proxyList.forEach((proxyListItem) -> {
			if (proxy.getHost().equals(proxyListItem.getHost()))
				throw new ProxyServerAlreadyAddedException(
						String.format("Proxy server [%s:%d] already added", proxy.getHost(), proxy.getPort()));
		});
	}

	public Proxy getNextProxy() throws NoProxyAvailableException{
		Proxy proxy = null;
		synchronized (proxyList) {
			LOG.info("Trying to get the next proxy available");
			if (proxyList.size() == 0)
				throw new NoProxyAvailableException("No proxy available");
			proxy = proxyList.get(0);
			proxyList.remove(0);
			useadProxyServersList.add(proxy);
		}
		LOG.info(String.format("Proxy available %s:%d [%s]", proxy.getHost(), proxy.getPort(), proxy.getScheme()));
		return proxy;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.proxyList.toString();
	}
}
